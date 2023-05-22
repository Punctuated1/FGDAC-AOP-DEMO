package com.pd.benchmark.geode;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.fgacdataobjects.DataAccessContext;
import com.pd.benchmark.fgacdataobjects.ObjectRegistry;
import com.pd.benchmark.fgacdataobjects.ProtectedAttribute;

@Aspect
public class HijackedAroundMethod {
//	 private ClientCache cache = new ClientCacheFactory().addPoolLocator("127.0.0.1", 10334).create();
//	 private Region<String, ObjectRegistry> objectRegistryRegion =
//     cache.<String, ObjectRegistry>createClientRegionFactory(ClientRegionShortcut.PROXY).create("objectRegistry");
	 private Map<String, ObjectRegistry> objectResistryMap = new HashMap<String, ObjectRegistry>();
	 private ObjectMapper mapper = new ObjectMapper();
	 private Properties kafkaProducerProperties;
	 private KafkaProducer<String, String> kafkaLogProducer;
	 private String workerRole="AUD";
	 private String[] workerRoles = {"WK1","WK2","WK3","AUD"};
	 //	 private static final String WORKER_ROLE="AUD";
	 private String securityScopes = "'BCBSIL^STD','BCBSNM^STD','BCBSOK^STD','TRSTMARKIL^STD','COLHLTHIL^STD','BCBSTX^STD','BCBCTX^EMP','BCBSMT^STD','BCBSNM^EMP'";
	 private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
	 private String filterSQL = "SELECT cag FROM bench.security_control WHERE cag in(?) AND security_scope IN (?) ORDER BY cag";
	 private SecurityScopeDAOImpl scopeDAOImpl = new SecurityScopeDAOImpl();
	 
	 
	public HijackedAroundMethod() {
		System.out.println("new bean: HijackedAroundMethod");
		mapper.registerModule(new JavaTimeModule());
		kafkaProducerProperties = new Properties();
		kafkaProducerProperties.put("bootstrap.servers", "127.0.0.1:9092");
		kafkaProducerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProducerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProducerProperties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "3048588");
		kafkaLogProducer = new KafkaProducer<String, String>(kafkaProducerProperties);
		String hashMapPopulated = doPopulateHashMap();
		System.out.println(hashMapPopulated);
	}

	@Around("execution(public * org.springframework.data.repository.Repository+.*(..))")
	public Object myAdvice(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch elapsedTime = new StopWatch();
		elapsedTime.start();
		String methodName = pjp.getSignature().getName();
		DataAccessContext accessContext = new DataAccessContext();
		accessContext.setInputArgs(pjp.getArgs());
		accessContext.setMethodName(methodName);
		accessContext.setTargetRepository(pjp.toLongString());
		accessContext.setUserName("HCSCWorker");
		accessContext.setSecurityScopes(securityScopes);
		double rndm = Math.random()*workerRoles.length;
		int roleIndex = (int)rndm;
		workerRole=workerRoles[roleIndex];
		accessContext.setSecurityRole(workerRole);
		Object[] args = pjp.getArgs();
	      if(args.length>0){
	          for (int i = 0; i < args.length; i++) {
	             System.out.println("arg "+(i+1)+": "+args[i]);
	          }
	       }
		try {
			StopWatch myQueryTimer = new StopWatch();
			myQueryTimer.start();
			Object result = pjp.proceed(args);
			myQueryTimer.stop();
			long elapsedMyQueryTime = elapsedTime.getTotalTimeNanos();
			System.out.println("Query Elapsed Time: "+formatter.format(elapsedMyQueryTime)+" Nanos.");
			if( methodName.startsWith("find")) {
				if(result.getClass().isInstance(Optional.class)) {
					Optional<Object> optional = (Optional<Object>) result;
					
					if(optional.isPresent()) {
						List<Object> optionalObjects = new ArrayList<Object>();
						optionalObjects.add(optional.get());
						optionalObjects = processObjects(optionalObjects);
						if(optionalObjects.isEmpty()) {
							optional=Optional.empty();
						}
					}
				} else {
					//if(result.getClass().isInstance(ArrayList.class)) {
					List<Object> objects = (List) result;
					result = processObjects(objects);
				}
			} 
			elapsedTime.stop();
			long elapsedTimeNanos = elapsedTime.getLastTaskTimeNanos();
			String elapsedTineNanoStr = formatter.format(elapsedTimeNanos)+" Nanos, ";
			accessContext.setElapsedTime(elapsedTineNanoStr);
			accessContext.setOperationResult(result);
			accessContext.setOperationTimeStamp(LocalDateTime.now());
			StopWatch accessLogTimer = new StopWatch();
			accessLogTimer.start();
			String accessContextString = CompressionUtil.compressAndReturnB64(mapper.writeValueAsString(accessContext));
			
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(BenchmarkConstants.LOG_TOPIC_NAME, 
					accessContext.getOperationTimeStamp().toString(), 
					accessContextString);
			kafkaLogProducer.send(producerRecord);
			accessLogTimer.stop();
			System.out.println(".............................................. Access Log Time: "+formatter.format(accessLogTimer.getTotalTimeNanos())+" Nanos, ");
			return result;
			
		} catch(Throwable ex) {
			ex.printStackTrace();
			throw ex;
		}

	}
	
	private List<Object> processObjects(List<Object> objects) {
			StopWatch filterRedactTimer = new StopWatch();
			filterRedactTimer.start();
			System.out.println("start filtering: "+LocalDateTime.now());
			ObjectRegistry objectRegistry = checkFilteringRequired(objects);
			if(objectRegistry.getDataObjectFiltering()) {
				List<String> cagList= getDistinctCagList(objects, objectRegistry);
					System.out.println("Returned distinct cag list: "+LocalDateTime.now());
		
				Map<String,String> allowedCags = scopeDAOImpl.getAllowedCagsForSecurityScopes(cagList, securityScopes);
					System.out.println("Returned allowed cag list: "+LocalDateTime.now());
				
				objects = filterObjects(objects, allowedCags, objectRegistry);
					System.out.println("Returned filtering: "+LocalDateTime.now());
					filterRedactTimer.stop();
					long elapsedTimeNanos = filterRedactTimer.getLastTaskTimeNanos();
					System.out.println("Filter/ Redact Elapsed Time: "+formatter.format(elapsedTimeNanos)+" Nanos.");
			}
		return objects;
	}

	private ObjectRegistry checkFilteringRequired(List<Object> objects) {
		Iterator<Object> iterator = objects.iterator();
		ObjectRegistry objectRegistry = null;
		List<Object> nonDistinctList = new ArrayList();
		boolean firstObject = true;
		boolean isFilteringRequired = false;
		while(iterator.hasNext()&&firstObject) {
			Object object = iterator.next();
			objectRegistry = objectResistryMap.get(object.getClass().getCanonicalName());
			firstObject=false;
			isFilteringRequired=objectRegistry.getDataObjectFiltering();
		}
		return objectRegistry;
	}
	
	private List<String> getDistinctCagList(List<Object> objects, ObjectRegistry objectRegistry) {
		Iterator<Object> iterator = objects.iterator();
		List<Object> nonDistinctList = new ArrayList();
		while(iterator.hasNext()) {
			Object object = iterator.next();
			try {
				Method filterMethod = object.getClass().getDeclaredMethod(objectRegistry.getFilterAttributeName());
				nonDistinctList.add((String)filterMethod.invoke(object));
			} catch(Throwable ex) {
				ex.printStackTrace();
			}
		}
		return new ArrayList(new HashSet(nonDistinctList));
	}
	
	private List<Object> filterObjects(List<Object> objects, Map<String, String> allowedCags, ObjectRegistry objectRegistry ) {
		Iterator<Object> iterator = objects.iterator();
		System.out.println("result set from Query "+objects.size());
		StopWatch retainAllTimer = new StopWatch();
		retainAllTimer.start();
		List<Object> filteredObjectList = new ArrayList<Object>();
		//		objects.retainAll(allowedCags);
		while(iterator.hasNext()) {
			Object object = iterator.next();
			try {
				Method filterMethod = object.getClass().getDeclaredMethod(objectRegistry.getFilterAttributeName());
				
				if(allowedCags.containsKey(filterMethod.invoke(object))) {
					filteredObjectList.add(object);
					if (workerRole.equalsIgnoreCase("AUD") && objectRegistry.isHostsSensitiveData()) {
						maskSensitiveAttributes(object, objectRegistry);
					}
				}
			} catch(Throwable ex) {
				ex.printStackTrace();
			}
		}
		retainAllTimer.stop();
		System.out.println("Retain All Elapsed Time: "+formatter.format(retainAllTimer.getLastTaskTimeNanos())+" Nanos. filtered list size: "+filteredObjectList.size());
		return filteredObjectList;
	}
	
	private void maskSensitiveAttributes(Object object, ObjectRegistry objectRegistry) {
		if(objectRegistry != null && objectRegistry.isHostsSensitiveData()) {
			try {
				Iterator<ProtectedAttribute>  iterator = objectRegistry.getProtectedAttributes().iterator();
				while(iterator.hasNext()) {
					ProtectedAttribute protectedAttribute = iterator.next();
					Method method;
						if(protectedAttribute.getAttributeMaskValue() == null) {
							method = object.getClass().getDeclaredMethod(protectedAttribute.getAttributeSetterName(), LocalDate.class);
						} else {
							method =  object.getClass().getDeclaredMethod(protectedAttribute.getAttributeSetterName(), String.class);
						}
					method.invoke(object, protectedAttribute.getAttributeMaskValue());
					
				}
	
			} catch (Exception ex) {
					ex.printStackTrace();
			}

		}
	}
	

	
	private List<ProtectedAttribute> doPopulateProtectedAttibuteList() {
		List<ProtectedAttribute> protectedAttributes = new ArrayList<>();
		ProtectedAttribute dob = new ProtectedAttribute();
		dob.setAttributeSetterName("setDateOfBirth");
		dob.setAttributeMaskValue(null);
		
		ProtectedAttribute gender = new ProtectedAttribute();
		gender.setAttributeSetterName("setGender");
		gender.setAttributeMaskValue("...");

		ProtectedAttribute lastName = new ProtectedAttribute();
		lastName.setAttributeSetterName("setLastName");
		lastName.setAttributeMaskValue("...");

		ProtectedAttribute firstName = new ProtectedAttribute();
		firstName.setAttributeSetterName("setFirstName");
		firstName.setAttributeMaskValue("...");
		
		ProtectedAttribute nameUp = new ProtectedAttribute();
		nameUp.setAttributeSetterName("setNameUp");
		nameUp.setAttributeMaskValue("...");
		
		protectedAttributes.add(dob);
		protectedAttributes.add(gender);
		protectedAttributes.add(lastName);
		protectedAttributes.add(firstName);
		protectedAttributes.add(nameUp);
		
		return protectedAttributes;
	}
	
	private String doPopulateHashMap() {
		List<ProtectedAttribute> protectedAttributes = doPopulateProtectedAttibuteList();
		
		ObjectRegistry objectRegistry = new ObjectRegistry();
		objectRegistry.setDataObjectName("com.pd.benchmark.mongodb.Person");
		objectRegistry.setDataObjectFiltering(true);
		objectRegistry.setHostsSensitiveData(true);
		objectRegistry.setFilterAttributeName("getCag");
		objectRegistry.setProtectedAttributes(protectedAttributes);
		objectResistryMap.put(objectRegistry.getDataObjectName(), objectRegistry);

		ObjectRegistry objectRegistry2 = new ObjectRegistry();
		objectRegistry2.setDataObjectName(ObjectRegistry.class.getCanonicalName());
		objectRegistry2.setDataObjectFiltering(false);
		objectRegistry2.setHostsSensitiveData(false);
		objectRegistry2.setFilterAttributeName(null);
		objectResistryMap.put(objectRegistry2.getDataObjectName(), objectRegistry2);

		ObjectRegistry objectRegistry3 = new ObjectRegistry();
		objectRegistry3.setDataObjectName("com.pd.benchmark.jpa.PersonPg");
		objectRegistry3.setDataObjectFiltering(true);
		objectRegistry3.setHostsSensitiveData(true);
		objectRegistry3.setFilterAttributeName("getCag");
		objectRegistry3.setProtectedAttributes(protectedAttributes);
		objectResistryMap.put(objectRegistry3.getDataObjectName(), objectRegistry3);

		return "HashMap populated";
	}
	
//	private boolean isObjectRepositoryEmpty() {
//		ObjectRegistry objectRegistry = objectRegistryRegion.get(ObjectRegistry.class.getCanonicalName());
//		boolean isNull=(objectRegistry == null); 
//		return isNull;
//	}
//	
//	private ObjectRegistry getObjectRegistryById(String objectCanonicalName) {
//		return objectRegistryRegion.get(objectCanonicalName);
//		
//	}


	

}