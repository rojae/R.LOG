package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    boolean existsByAccount(Account account);
}
