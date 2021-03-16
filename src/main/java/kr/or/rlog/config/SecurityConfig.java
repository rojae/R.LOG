package kr.or.rlog.config;


import kr.or.rlog.account.AccountService;
import kr.or.rlog.common.LoggerFilter;
import kr.or.rlog.handler.LoginFailHandler;
import kr.or.rlog.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    // Voter's expression handler custom
    public AccessDecisionManager accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voters = Collections.singletonList(webExpressionVoter);
        return new AffirmativeBased(voters);
    }

    @Override
    public void configure(WebSecurity web) {
        String[] staticResources  =  {
                "/assets/**",
                "/css/**",
                "/ico/**",
                "/images/**",
                "/js/**"
        };

        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers( "/", "/signup", "/api/v1/signup/mail")
                .mvcMatchers(staticResources);
        //web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new LoggerFilter(), WebAsyncManagerIntegrationFilter.class);

        http.authorizeRequests()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .accessDecisionManager(accessDecisionManager());

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailHandler())
                .permitAll();

        http.csrf().disable();

        http.logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/index");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    // method security에서 authenticationManager를 bean으로 접근 가능하도록
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}