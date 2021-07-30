<h1 align="center">
    internet-speed-monitor
</h1>
A small springboot app build to track the internet speed.

##### Initial Setup
Set the environment variables:
M2_HOME, JAVA_HOME etc

> Please note that this project uses java 16 by default. This may work with older java, use with your own risk.

##### Build command :
`./mvnw clean install`

##### To Run the application :
`./mvnw spring-boot:run`

##### If you have a ~/.m2/settings.xml that points to your organisations maven repo
`./mvnw -s settings.xml spring-boot:run`
