package co.pd.datagen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.dataobjects.FGDACControlItem;

public class GenerateFGDACTableFile {
	private List<FGDACControlItem> controlItems = new ArrayList<FGDACControlItem>();
	
	
	public void doIt() {
		readBaseFGACControleFile(BenchmarkConstants.SECURITY_CONTROL_BASE_DATA);
		enrichAndWrite();
	}
	   private void readBaseFGACControleFile(String csvFile) {
		   //log
				
		   try {
	         File file = new File(BenchmarkConstants.COMPLETED_PERSONS_FOLDER+csvFile);
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         String[] tempArr;
	         while((line = br.readLine()) != null) {
	            tempArr = line.split(BenchmarkConstants.DELIMITER);
	            String coalition  = tempArr[0];
	            String client = tempArr[1];
	            String grp_security_cd = tempArr[2];
	            FGDACControlItem controlItem = new FGDACControlItem();
	            controlItem.setCoalitionId(coalition);
	            controlItem.setClientId(client);
	            controlItem.setSecurityScope(grp_security_cd);
	            controlItems.add(controlItem);
	         }
	            br.close();
	         } catch(IOException ioe) {
	            ioe.printStackTrace();
			}
	   	}	

	   public void enrichAndWrite() {
		   int carrierId=0;
		   int accountId=0;
		   int accountGrp=0;
		   int planId=0;
		   int numCarriersPerItem = 4;
		   int numAccountsPerCarrier = 10;
		   int numGroupsPerAccount = 46;
		   int numPlansPerGroup = 55;
		   int numLobPerPlan = 1;
		   String[] lobValues = {"Medicare","Medicaid","HIM","Fully Insured","Self Insured"};
		   String[] roleValues = {"AUD","WK1","WK2","WK3","WK4","WK5","WK6","WK7","WK8","WK9","WK10","WK11","WK12","WK13"};
		   BufferedWriter fgdacDetailWriter;
		   try {
			   fgdacDetailWriter = new BufferedWriter(new FileWriter(BenchmarkConstants.COMPLETED_PERSONS_FOLDER+"fgdacDetailItems.csv"));
			    		  
			   Iterator<FGDACControlItem> iterator = controlItems.iterator();
			   while(iterator.hasNext()) {
				   FGDACControlItem baseControlItem = iterator.next();
			   	   int carrierCnt=0;
		           while(carrierCnt<numCarriersPerItem) {
		           	carrierCnt++;
		           	carrierId++;
		           	int accountCnt=0;
		           	while(accountCnt<numAccountsPerCarrier) {
		           		accountCnt++;
		           		accountId++;
		           		int accountGrpCnt=0;
		           		while(accountGrpCnt<numGroupsPerAccount) {
		           			accountGrpCnt++;
		           			accountGrp++;
		           			int planCnt=0;
		           			while(planCnt<numPlansPerGroup) {
		           				planCnt++;
		           				planId++;
		           				int lobCnt=0;
		           				while(lobCnt<numLobPerPlan) {
		           					lobCnt++;
		           					FGDACControlItem controlItem = new FGDACControlItem();
		           					controlItem.setCoalitionId(baseControlItem.getCoalitionId());
		           					controlItem.setClientId(baseControlItem.getClientId());
		           					controlItem.setCarrierId(Integer.toString(carrierId));
		           					controlItem.setSecurityScope(baseControlItem.getSecurityScope());
		           					controlItem.setAccountId(Integer.toString(accountId));
		           					controlItem.setGroupId(Integer.toString(accountGrp));
		           					controlItem.setPlanCode(Integer.toString(planId));
		           					int lobSel = (int)(Math.random()*lobValues.length);
//		           					System.out.println("lobValues.length: "+lobValues.length+" --lobSel:"+lobSel);
		           					controlItem.setLineOfBusiness(lobValues[lobSel]);
		           					int roleSel =(int)(Math.random()*roleValues.length);
		           					controlItem.setSecurityRole(roleValues[roleSel]);
		           					String cag=controlItem.getCarrierId()+"."+controlItem.getAccountId()+"."+controlItem.getGroupId();
		           					controlItem.setCag(cag);
		           					fgdacDetailWriter.write(convertControlItemToString(controlItem)); 
		           				}
		           			}
		           		}
		           	}
		           }
				   
			   }
		    	  fgdacDetailWriter.close();
			} catch(IOException ioe) {
	            ioe.printStackTrace();
			}
	   }
	   
	private String convertControlItemToString(FGDACControlItem controlItem) {
		String controlItemStr = controlItem.getClientId()+","
			   + "\""+controlItem.getCarrierId()+"\","
			   + "\""+controlItem.getAccountId()+"\","
			   + "\""+controlItem.getGroupId()+"\","
			   + "\""+controlItem.getPlanCode()+"\","
			   + "\""+controlItem.getLineOfBusiness()+"\","
			   + "\""+controlItem.getCoalitionId()+"\","
			   + "\""+controlItem.getSecurityScope()+"\","
			   + "\""+controlItem.getSecurityRole()+"\","
			   + "\""+controlItem.getCag()+"\"\n";
//		System.out.println(controlItemStr+"length: "+controlItemStr.length());
		return controlItemStr;
	}
	   

}
