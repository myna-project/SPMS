# SPMS-API
Simple Production Management System API (SPMS-API) allows to record and query the data relating to the production cycles that are carried out on a working station. The data that can be entered concern the information on the production order, the production phases carried out and the produced quantities.
The data in JSON format is sent to and received by SPMS-API via the REST services, protected through basic authentication, that the software exposes.
A graphical interface [SPMS](https://github.com/myna-project/SPMS-UI) has been developed to facilitate the use of the REST services exposed by SPMS-API.
### Installation requirements
To install SPMS-API you need the following:
* [Apache Maven](https://maven.apache.org/)
* a web server (we recommend [Apache Tomcat](https://tomcat.apache.org/))
* an object-relational DataBase Management System (we recommend [PostgreSQL](https://www.postgresql.org/))

### Installation example using Apache Tomcat and PostgreSQL
In PostgreSQL create an user 'spms' and a database 'spms', then execute all sql commands in file ddl/creation/spms\_x.x.x.sql where x.x.x correspond to the latest version of the software. Executing sql commands in file ddl/test/data_x.x.x.sql you can create roles and example users that can access to the REST services exposed by SPMS.

In Apache Tomcat folder installation edit file context.xml adding:

```
<Resource auth="Container" driverClassName="org.postgresql.Driver" maxIdle="30" maxTotal="100" maxWaitMillis="10000" name="jdbc/spms" password="secret" type="javax.sql.DataSource" url="jdbc:postgresql://{psql_ip}:{psql_port}/spms" username="spms"/>
```

inside the tag Context.

In the project folder, execute

```
mvn clean package
```

to create file spms-api.war in the /target folder.

Copy spms-api.war file in Apache Tomcat webapps folder and start Apache Tomcat to run SPMS-API.

### REST services
To get list of REST services exposed by SPMS-API go to: http://{tomcat_ip}:{tomcat_port}/spms-api/swagger/api-docs