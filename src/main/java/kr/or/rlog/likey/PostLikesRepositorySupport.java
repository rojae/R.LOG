package kr.or.rlog.likey;

import com.querydsl.core.QueryResults;
import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostDto;

import java.util.List;

public interface PostLikesRepositorySupport {
    boolean existsByAccountAndPostAndStatus(Account account, Post post, LikesType status);

    PostLikes findByAccountAndPostAndStatus(Account user, Post post, LikesType likesType);

    /**
     * @methodName: findByLikeDesc
     * @author: rojae
     * @date: 2021-08-15
     * @Description:
     * function : 게시글의 고유 값 ID를 통해, 게시글 내용 조회
     * subQuery : 인기 글의 고유 값 ID를 가져옴
     */
    List<PostDto> findByPostIds(int popularSize);

}
