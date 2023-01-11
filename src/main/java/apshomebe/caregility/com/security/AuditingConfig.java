//package apshomebe.caregility.com.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//
//import apshomebe.caregility.com.audit.AuditorAwareImpl;
//
//@Configuration
//@EnableMongoAuditing
//public class AuditingConfig {
//
//    @Bean
//    public AuditorAware<String> myAuditorProvider() {
//        return new AuditorAwareImpl();
//    }
//}