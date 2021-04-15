package kr.or.rlog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@ServletComponentScan
@SpringBootApplication
@EnableJpaAuditing
@PropertySource({
        "classpath:application-oauth.properties"
        ,"classpath:application-path.properties"
})
public class Application {

    @Value("${rlog.image.upload.path}")
    private String uploadPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(name = "uploadPath")
    public String uploadPath() {
        return uploadPath;
    }

}
