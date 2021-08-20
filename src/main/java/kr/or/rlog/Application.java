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

/**
 * @fileName: Application.java
 * @author: rojae
 * @date: 2021-08-21
 * @description: 웹 어플리케이션 메인 클래스
 *         "classpath:application-oauth.properties" 로딩.
 *         "classpath:application-path.properties"  로딩.
 * ===========================================================
 * DATE          AUTHOR      NOTE
 * -----------------------------------------------------------
 * 2021-08-21    rojae       최초생성
 */
@ServletComponentScan
@SpringBootApplication
@EnableJpaAuditing
@PropertySource({
        "classpath:application-oauth.properties"
        , "classpath:application-path.properties"
})
public class Application {

    @Value("${rlog.image.upload.path}")
    private String uploadPath;

    /**
     * ==================================================================
     * @methodName : main
     * @description : 웹 어플리케이션 메인 실행문.
     * @func1 : 웹 어플리케이션 기동
     * @author: rojae
     * @date : 2021-08-21
     * ==================================================================
     **/
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * ==================================================================
     * @methodName : passwordEncoder
     * @description : 패스워드 인코더 빈 등록.
     * @author: rojae
     * @date : 2021-08-21
     * ==================================================================
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * ==================================================================
     * @methodName : uploadPath
     * @description : 이미지 절대경로 빈 등록.
     * @author: rojae
     * @date : 2021-08-21
     * ==================================================================
     **/
    @Bean(name = "uploadPath")
    public String uploadPath() {
        return uploadPath;
    }

}
