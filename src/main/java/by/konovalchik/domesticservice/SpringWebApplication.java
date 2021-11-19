package by.konovalchik.domesticservice;

import by.konovalchik.domesticservice.configuration.NoAuthSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;





@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringWebApplication {

    public static void main(String[] args) {

        SpringApplication. run(SpringWebApplication.class, args);

    }

}
