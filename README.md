# Microservices Project

Project to demonstrate concepts and practices in a microservice architecture.

<!-- TOC -->

* [Microservices Project](#microservices-project)
    * [Intended Structure](#intended-structure)
    * [Common classes and DTOs](#common-classes-and-dtos)
    * [Creating a new Service](#creating-a-new-service)
    * [Decent Defaults](#decent-defaults)
    * [Authentication](#authentication)

<!-- TOC -->

## Intended Structure

The project is a POC for a microservices architecture that will explore and demonstrate concepts.

## Common classes and DTOs

The library ecommerce-commons contains several utilities to be imported within each component service. Make sure to
create DTO classes within each service respective package within the architecture.

Commons project structure:

```
.
├── commons
│   ├── auth
│   ├── config
│   ├── exception
│   └── web
├── orders
│   └── dto
└── payment
    ├── client ## Contains Feign Client definitions for the service
    └── dto
```

## Creating a new Service

The following dependencies must be imported in the pom:

```xml

<dependencies>
    <dependency>
        <groupId>br.com.jlcorradi</groupId>
        <artifactId>ecommerce-commons</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>


    <!--    Test Dependencies-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>dev.jlcorradi</groupId>
        <artifactId>ecommerce-commons-test</artifactId>
        <version>${project.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

In case the Service receives/sends messages Include the ampq dependencies:

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
    <scope>provided</scope>
</dependency>
```

The following settings must be in the application.yml:

```yml
server:
  port: ${SERVICE_PORT}

## This is important if you'll use FeignClient
client-services-urls:
  gateway-service: ${GATEWAY_SERVICE_URL}


authService:
  jwtTokenSecret: ${JWT_TOKEN_SECRET}

management:
  observations:
    key-values:
      application: ${spring.application.name}
  endpoints:
    enabled-by-default: true
    jmx:
      exposure:
        include: '*'
    web:
      exposure:
        include: "health,info,prometheus"

spring:
  application:
    name: Application Name
  datasource:
    url: ${JDBC_URL}
    username: ${JDBC_USERNAME}
    password: ${JDBC_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

In case the Service receives/sends messages Include the rabbit MQ configuration:

````yml
spring:
  rabbitmq:
    addresses: localhost:5672
    username: rabbit
    password: rabbit
````

* Important: The ``Exchange`` and ```RabbitTemplate``` objects will be added to spring context only if at least
* ```spring.rabbitmq.addresses``` property is set.

You can also include nice system information logs on startup by adding the ```SystemInfoApplicationListener``` to
the application on startup:

```java

@SpringBootApplication
@WithCommons
@WithStaticJwtSecurity
public class ServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication springApplication = new SpringApplication(ServiceApplication.class);
    springApplication.addListeners(new SystemInfoApplicationListener());
    springApplication.run(args);
  }
}
```

## Decent Defaults

Once you annotate your application with ```@WithCommons``` you bring in common features from the commons project:

- Exception Handler at the API Level
- Model Mapper ready to use
- A JwtValidator object that can be autowired to validate JWTs. You can use it in case you need to customize the
  Spring security with a bean of type SecurityFilterChain (Refer to Spring Security 6 documentation)
- Support for Feign Clients.
    - You should define feign client interfaces within the commons project under the owner service folder.

The commons project also provides static JwtAuhentication that verifies the signature of the token and creates an
Authenticated user (BasicJwtAuthenticationToken) in the SecurityContextHolder with the username, id and authorities
allowing the application to use the @PreAuthorize annotation with roles validation. It is brought in
via the ```@WithStaticJwtSecurity``` annotation.

## Authentication

When using Feign Clients the Authentication Header will be propagated by default. You can choose if you want the client
requests to go through the Gateway service and have a full authentication check or go direct to the intended service
with the jwt token which will statically verify the token and resolve the username and userId from it.

You can inject ```BasicJwtAuthenticationToken``` in your request method to have access to it:

```java

@Getter
@EqualsAndHashCode(callSuper = false)
public class BasicJwtAuthenticationToken extends AbstractAuthenticationToken
{
   ...
}
```

You can configure Basic JWT authentication
adding ```.with(new BasicJwtSecurityHttpConfigurer(jwtValidator), Customizer.withDefaults())```
to your security filter chain like so:

```java

@Bean
public SecurityFilterChain config(HttpSecurity http) throws Exception
{
  log.info("Using Basic Static Jwt based security");

  return http
      .httpBasic(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize ->
      {
        authorize.requestMatchers("/actuator", "/actuator/**").permitAll();
        authorize.anyRequest().authenticated();
      })
      .with(new BasicJwtSecurityHttpConfigurer(), jwt -> jwt.withJwtValidator(jwtValidator))
      .build();
}
```

## Conventions - Constants

The constants that represents routes in the microservices must be defined as constants in a class
named ```{Service}RoutingConstants``` in the ecommerce-commons project. That way the same value will be used in the 
Feign Clients and Api Controllers across the ecosystem. What should be declared:
- Api URLS
- Rabbit MQ Routing Keys.

Ex:
```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentRoutingConstants
{
  public static final String PAYMENTS_API_URL = "/api/v1/payments";
  public static final String PAYMENT_STATUS_CHANGE_ROUTING_KEY = "payment.onPaymentStatusChange";
}

```

### Services and ports

The services are set up to run on the following ports:

- OrdersServiceApplication :8080/
- GatewayServiceApplication :8090/
- NotificationServiceApplication :8085/
- CatalogServiceApplication :8082/
- PaymentServiceApplication :8084/
- InventoryServiceApplication :8083/
- AuthServiceApplication :8081/