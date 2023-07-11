# FGDAC-AOP-DEMO
AOP Interceptor fro Spring Data Implementations to filter and redact data based on security control atributes associated with the data type

<UL>The demo consists of eight Spring Boot projects:
<LI>The first of note (BenchmarkGeode) contains the AOP Around Interceptor class (HijackedAroundMethod.java)) that proxies access to any public method on a Spring Data Repository (including custom interface methods added to a repository interface), or any public method on MongoDbTemplate </LI>
<LI>The second is a set of common data objects and utlitty classes (obsolete but not yet cleaned up)</LI>
  <LI>The third is a benchmark application for mongodb that includes the AOP Interceptor library</LI>
<LI>The fourth is a benchmark application for Postgres that includes the AOP Interceptor library</LI>
  <LI>The fifth is a Kafka Consumer that consumes data access log events from the AOP Inteceptor and writes them to a MongoDB Collection</LI>
  <LI>The sixth is a UI for Object Registry component used by the AOP Registry</LI>
  <LI>The seventh contains DDL for the tables governing the behavior of the Interceptor</>
    <li>Test Data Generaton utility</li>
    </UL>
