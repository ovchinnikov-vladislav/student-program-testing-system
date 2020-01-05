package rsoi.lab5.seservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "rsoi.lab5.seservice.*")
@EnableJpaRepositories
@EnableJpaAuditing
@PropertySource("classpath:application.properties")
public class SessionServiceApp {
    public static void main(String... args) {
        SpringApplication.run(SessionServiceApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return str -> System.out.println("SessionService");
    }
}