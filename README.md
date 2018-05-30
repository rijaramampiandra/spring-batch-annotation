# 1.	Description
*	Read a flat file
*	Convert the value to model object
*	Insert values on MySQL DB
*	The batch is executed every 10 seconds
*	It's an Spring boot application
*	You don't need to create the table, the application create it automatically
*	Java version : 1.8

# 2.	Config file (file path, DB connexion) :
*	DEV or Production profile : */spring-batch-annotation/src/main/resources/application.properties*
*	Test profile : */spring-batch-annotation/src/test/resources/application-test.properties*


# 3.	Integration testing
*	H2 DB memory is used on integration testing.
*	test.hibernate.hbm2ddl.auto=**create-drop** -> Table is create and drop on the execution
*	Execute : ProcessJobTestParent.java

# 4.	File to read
*	report.csv
*	Path is define on the application.properties

# 5.	Dependencies and framework
*	Spring scheduler
*	Spring job
*	Spring batch
*	Spring data JPA
*	Lombok
*	Jackson

# 6.	Databases for profile
*	DEV or Production : MySQL
*	Test : H2 memory with MODE=MySQL

# 7.	Project builder
*	Maven

# 8.	Batch cron
*	The batch is executed every 10 seconds
*	To chance the cron, edit application.properties and change **cron.expression**