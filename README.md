# spring-boot-docker-kubernetes-example

### Docker integration

To build docker image:
```
mvn clean package docker:build
```
To run docker container:
```
docker run -p 8080:8080 -t djhurley/spring-boot-docker-kubernetes-example
```
To build docker image and push to docker hub:
```
mvn clean package docker:build -DpushImage
```
To be able to push image to docker hub you need to add the following to your maven settins.xml:
```
<settings>
	<servers>
		<server>
			<id>docker-hub</id>
			<username>{docker hub account docker id}</username>
			<password>{docker hub account password}</password>
			<configuration>
				<email>{your email for docker hub account}</email>
			</configuration>
		</server>
	</servers>
</settings>
```
### Kubernetes integration

In Kubernetes add a ConfigMap using this yml file:
```
kind: ConfigMap
apiVersion: v1
metadata:
  name: spring-boot-docker-kubernetes-example
data:
  bean.message: Hello World 2!
```
Then deploy spring-boot-docker-kubernetes-example as a pod:
```
kubectl run spring-boot-docker-kubernetes-example --image=djhurley/spring-boot-docker-kubernetes-example
```
Then tail the logs to see the messages:
```
kubectl logs -f {spring-boot-docker-kubernetes-example pod name}
```
If you change the property bean.message in the Kubernetes ConfigMap you will see the application automatically update the message live.

Here is an example output from the application logs when a config refresh occurs:
```
The message is: Hello World 2!
The message is: Hello World 2!
The message is: Hello World 2!
2017-04-23 14:23:45.140  INFO 7 --- [default.svc/...] .r.EventBasedConfigurationChangeDetector : Detected change in config maps
2017-04-23 14:23:45.140  INFO 7 --- [default.svc/...] .r.EventBasedConfigurationChangeDetector : Reloading using strategy: REFRESH
2017-04-23 14:23:45.304  INFO 7 --- [default.svc/...] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@711c0af2: startup date [Sun Apr 23 14:23:45 GMT 2017]; root of context hierarchy
2017-04-23 14:23:45.377  INFO 7 --- [default.svc/...] trationDelegate$BeanPostProcessorChecker : Bean 'configurationPropertiesRebinderAutoConfiguration' of type [org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration$$EnhancerBySpringCGLIB$$522070ac] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2017-04-23 14:23:45.555  INFO 7 --- [default.svc/...] b.c.PropertySourceBootstrapConfiguration : Located property source: ConfigMapPropertySource [name='configmap.spring-boot-docker-kubernetes-example.default']
2017-04-23 14:23:45.556  INFO 7 --- [default.svc/...] b.c.PropertySourceBootstrapConfiguration : Located property source: SecretsPropertySource [name='secrets.spring-boot-docker-kubernetes-example.default']
2017-04-23 14:23:45.613  INFO 7 --- [default.svc/...] o.s.boot.SpringApplication               : The following profiles are active: kubernetes
2017-04-23 14:23:45.616  INFO 7 --- [default.svc/...] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@4a482e0b: startup date [Sun Apr 23 14:23:45 GMT 2017]; parent: org.springframework.context.annotation.AnnotationConfigApplicationContext@711c0af2
2017-04-23 14:23:45.638  INFO 7 --- [default.svc/...] o.s.boot.SpringApplication               : Started application in 0.486 seconds (JVM running for 338.559)
2017-04-23 14:23:45.638  INFO 7 --- [default.svc/...] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@4a482e0b: startup date [Sun Apr 23 14:23:45 GMT 2017]; parent: org.springframework.context.annotation.AnnotationConfigApplicationContext@711c0af2
The message is: Hello World 3!
The message is: Hello World 3!
The message is: Hello World 3!
```

### Jenkins Tips
Reset builds back to build number 1.
```
def job = Jenkins.instance.getItem("spring-boot-docker-kubernetes-example")
job.builds.findAll { it.number > 0 && it.number < 100 }.each { it.delete() }
job.nextBuildNumber = 1
job.saveNextBuildNumber()
```
