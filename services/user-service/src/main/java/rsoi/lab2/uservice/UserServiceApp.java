package rsoi.lab2.uservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "rsoi.lab2.uservice.*")
@EnableJpaRepositories
@PropertySource("classpath:application-h2.properties")
public class UserServiceApp {
    public static void main(String... args) {
        SpringApplication.run(UserServiceApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return str -> System.out.println("UserService");
    }
}