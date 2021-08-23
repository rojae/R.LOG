package kr.or.rlog.likey;

import com.querydsl.core.QueryResults;
import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostDto;

import java.util.List;

public interface PostLikesRepositorySupport {

    /**
     * ==================================================================
     * @methodName : getLikeCount
     * @description : 현재 글의 좋아요 수.
     * @author: rojae
     * @date : 2021-08-21
     * ==================================================================
     **/
    Long getLikeCount(Long pId);

    /**
     * ==================================================================
     * @methodName : isLike
     * @description : 사용자가 게시글에 좋아요를 눌렀는가?
     * @author: rojae
     * @date : 2021-08-21
     * ==================================================================
     **/
    boolean isLike(Long pId);

    /**
     * ==================================================================
     * @methodName : getPostLikes
     * @description : 현재 글에서의, 사용자 좋아요 객체를 반환.
     * @author: rojae
     * @date : 2021-08-21
     * ==================================================================
     **/
    PostLikes getPostLikes(Long pId);

    /**
     * ==================================================================
     * @methodName : findByPostIds
     * @description : 게시글의 고유 값 ID를 통해, 게시글 내용 조회
     * @func1 : 게시글 내용 조회
     * @func2 : subQuery - 인기 글의 고유 값 ID를 가져옴
     * @author: rojae
     * @date :  2021-08-15
     * ==================================================================
     **/
    List<PostDto> findByPostIds(int popularSize);


}
