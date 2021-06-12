package kr.or.rlog.account;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.platform.PlatformType;
import org.springframework.beans.factory.annotation.Autowired;
import static kr.or.rlog.account.QAccount.account;

public class AccountRepositorySupportImpl implements AccountRepositorySupport {

    @Autowired
    JPAQueryFactory query;

    @Override
    public Account findByEmailAndIsAuthIsTrueAndPlatformType(String email, PlatformType platformType) {
        return query.selectFrom(account)
                .where(
                        account.email.eq(email)
                                .and(account.platformType.eq(platformType))
                                .and(account.isAuth.eq(true))
                ).fetchOne();
    }

    @Override
    public Account findByEmailAndIsAuthIsTrue(String email) {
        return query.select(account)
                .where(
                        account.email.eq(email)
                        .and(account.isAuth.eq(true))
                ).fetchOne();
    }

    @Override
    public boolean existsAccountByEmailAndIsAuthIsTrue(String email) {
        Integer result = query.selectOne()
                .from(account)
                .where(
                        account.email.eq(email)
                        .and(account.isAuth.eq(true))
                ).fetchOne();

        return result != null;
    }


}
