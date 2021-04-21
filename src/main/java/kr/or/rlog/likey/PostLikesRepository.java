package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    boolean existsByAccountAndPostAndStatus(Account account, Post post, LikesType status);

    @Query("select count(t) from TBL_POST_LIKES t where t.post = :post and t.status = 'ENABLE' ")
    Long findCountByPostAndStatus(@Param("post") Post post);

    PostLikes findByAccountAndPostAndStatus(Account user, Post post, LikesType likesType);
}
