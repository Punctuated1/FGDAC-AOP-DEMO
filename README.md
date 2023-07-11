# FGDAC-AOP-DEMO
AOP Interceptor fro Spring Data Implementations to filter and redact data based on security control atributes associated with the data type

<UL>The demo consists of several Spring Boot projects:
<LI>The first of note (BenchmarkGeode) contains the AOP Around Interceptor class (HijackedAroundMethod.java)) that proxies access to any public method on a Spring Data Repository (including custom interface methods added to a repository interface), or any public method on MongoDbTemplate </LI>
<LI>The second project of note is BenchmarkMongo uses both Spring Boot Data Repository and MongoDbTmplate to access a mongoDb collection of person.When configured to include and configure the BenchmarkGedoe project with interceptor the finbylastname process demonstrates the filtering and redaction capability</LI>
<LI>Another project of note is BenchmarkPostgres demonstrates the interceptor applied to a postgres repository</LI>
</UL>
