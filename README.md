### Transactional Database Requester
This application demonstrates capabilities of declarative transaction management with Spring Framework.
Such popular use-case of transactional data transfer between two containers, more likely two bank accounts in the real world, is considered.  
A bit of 'cross-cutting' AOP (based on AspectJ) is present here as well.
Database - Oracle XE, although any other RDBMS should match.

### Technology Stack
* JDK 8
* Apache Maven v.3.2
* Spring Framework v.4.1.6.RELEASE (various libs)
* AspectJ v.1.8.5
* JDBC (Oracle Driver 11g) v.11.2.0.4
* Oracle Database XE 11g

### Build Instructions
Invoke the following maven command from the app root dir:

`mvn clean package`

Examine build log, make sure build was successful:

`[INFO] BUILD SUCCESS`

### Launch Instructions
Once the app is assembled, take a look at AtWork class - this is the entry point.
Database connection settings are inside jdbc.properties file.