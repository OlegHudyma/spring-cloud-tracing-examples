Cloned from https://github.com/in28minutes/spring-boot-examples/tree/master/spring-boot-basic-microservice

Steps to set up locally:

1. Start docker.
2. Run docker container: 
```
docker run -d -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:1.8
```
3. Run discovery.
4. Run forex-service.
5. Run currency-conversion.
6. Go to http://localhost:8100/currency-converter-feign/from/EUR/to/INR/quantity/10000 to generate some traces.
7. Check traces in Jaeger: http://localhost:16686/search