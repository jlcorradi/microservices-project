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
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>
```

The following settings must be in the applicaiton.yml:
```yml
server:
  port: ${SERVICE_PORT}

authService:
  jwtTokenSecret: ${JWT_TOKEN_SECRET}

management:
  endpoints:
    web:
      exposure:
        include: "*"
spring:
  datasource:
    url: ${JDBC_URL}
    username: ${JDBC_USERNAME}
    password: ${JDBC_PASSWORD~~~~}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

You can also include nice system information logs on startup by adding the ```SystemInfoApplicationListener``` to 
the application on startup:
```java
@SpringBootApplication
@WithCommons
@WithStaticJwtSecurity
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ServiceApplication.class);
        springApplication.addListeners(new SystemInfoApplicationListener());
        springApplication.run(args);
    }
}
```

### Decent Defaults
Once you annotate your application with ```@WithCommons``` you bring in common features from the commons project:
 - Exception Handler at the API Level
 - Model Mapper ready to use
 - A JwtValidator object that can be autowired to validate JWTs. You can use it in case you need to customize the 
   Spring security with a bean of type SecurityFilterChain (Refer to Spring Security 6 documentation)

The commons project also provides static JwtAuhentication that verifies the signature of the token and creates an 
Authenticated user (BasicJwtAuthenticationToken) in the SecurityContextHolder with the username, id and authorities 
allowing the application to use the @PreAuthorize annotation with roles validation. It is brought in 
via the ```@WithStaticJwtSecurity``` annotation.