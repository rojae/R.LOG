package kr.or.rlog.common;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.UserAccount;
import kr.or.rlog.account.platform.PlatformType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithAdmin {
}

class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithAdmin> {

    @Override
    public SecurityContext createSecurityContext(WithAdmin user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserAccount account = this.loadAdmin();
        Authentication auth = new UsernamePasswordAuthenticationToken(account, account.getAccount().getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" +account.getAccount().getRole())));
        context.setAuthentication(auth);
        return context;
    }

    public UserAccount loadAdmin() {
        Account account = new Account();
        account.setId(1L);
        account.setUserName("rojae");
        account.setPassword("rojae");
        account.setEmail("rojae@mail.com");
        account.setRole("ADMIN");
        account.setPlatformType(PlatformType.RLOG);
        return new UserAccount(account);
    }
}