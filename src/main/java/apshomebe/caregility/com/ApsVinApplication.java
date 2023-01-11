package apshomebe.caregility.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import apshomebe.caregility.com.audit.AuditorAwareImpl;

@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
public class ApsVinApplication {
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApsVinApplication.class, args);
	}

}

