package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;

import java.util.List;

public interface AccountRepositorySupport {
    Account findByEmailAndIsAuthIsTrueAndPlatformType(String email, PlatformType platformType);
    Account findByEmailAndIsAuthIsTrue(String email);
    boolean existsAccountByEmailAndIsAuthIsTrue(String email);
}

