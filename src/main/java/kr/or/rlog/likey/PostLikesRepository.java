package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, PostLikesRepositorySupport {

    @Query("select count(t) from TBL_POST_LIKES t where t.post = :post and t.status = 'ENABLE' ")
    Long findCountByPostAndStatus(@Param("post") Post post);

    /**
     * 인기 글 POST ID 반환 쿼리 (좋아요 갯수)
     * @param popularSize : 조회하는 인기 글 갯수
     * @return Post.id
     */
    @Query(value = "select t.post_id " +
            "from tbl_post_likes t " +
            "where t.status = 'ENABLE' " +
                "AND ( SELECT IF(p.status = 'ENABLE', true, false) " +
                "FROM tbl_post p " +
                "WHERE t.post_id = p.id ) " +
            "group by t.post_id " +
            "order by count(*) " +
            "desc limit ?1", nativeQuery = true)
    List<Long> findByLikeDesc(int popularSize);
}
