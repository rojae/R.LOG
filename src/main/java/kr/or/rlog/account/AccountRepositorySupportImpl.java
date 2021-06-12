package kr.or.rlog.account;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.platform.PlatformType;
import org.springframework.security.access.annotation.Secured;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AccountRepositorySupportImpl implements AccountRepositorySupport {

    @PersistenceContext
    EntityManager em;

    @Override
    public Account findByEmailAndIsAuthIsTrueAndPlatformType(String email, PlatformType platformType) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QAccount qAccount = new QAccount("account");
        return query.selectFrom(qAccount)
                .where(
                        qAccount.email.eq(email)
                                .and(qAccount.platformType.eq(platformType))
                                .and(qAccount.isAuth.eq(true))
                ).fetchOne();
    }
}
