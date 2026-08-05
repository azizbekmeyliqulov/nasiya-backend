package xurshid_azizbek.com.example.nasiyabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NasiyaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NasiyaBackendApplication.class, args);
    }

}
