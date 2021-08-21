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

To verify
```shell
curl -X GET http://localhost:8080/actuator/health
{"status":"UP"}%
```
```shell
curl -X GET http://localhost:8080/actuator/prometheus | grep internet
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  9066  100  9066    0     0   818k      0 --:--:-- --:--:-- --:--:--  885k
# HELP internet_report_time_gauge
# TYPE internet_report_time_gauge gauge
internet_report_time_gauge 59.0
# HELP internet_packet_counter_total
# TYPE internet_packet_counter_total counter
internet_packet_counter_total 24.0
```

Reference :
* https://blog.frantic.im/all/debugging-home-internet-connection/
* https://www.mokkapps.de/blog/monitoring-spring-boot-application-with-micrometer-prometheus-and-grafana-using-custom-metrics/
* https://github.com/bertrandmartel/speed-test-lib