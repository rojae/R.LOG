package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PostLikesService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

    /** ==================================================================
     * @methodName : createNew
     * @description : 현재 글에 대한 좋아요 객체 생성
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
    @Transactional
    public boolean createNew(Account user, Long postId) {
        Optional<Post> savedPost = postRepository.findById(postId);

        if (savedPost.isPresent()) {
            PostLikes savedPostLikes = postLikesRepository.save(new PostLikes(user, savedPost.get()));
            savedPost.get().addPostLike(savedPostLikes);
            return true;
        } else {
            return false;
        }
    }

    /** ==================================================================
     * @methodName : editPostLikes
     * @description : 좋아요 상태 토글 기능.
     * @func1 : 좋아요 -> 좋아요 취소
     * @func2 : 좋아요 취소 -> 좋아요
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
    @Transactional
    public boolean editPostLikes(Account user, Long postId) {
        Optional<PostLikes> savedLike = Optional.ofNullable(postLikesRepository.getPostLikes(postId));

        if (!savedLike.isPresent())
            return false;
        else if (LikesType.isEnable(savedLike.get().getStatus()))
            savedLike.get().setStatus(LikesType.UNABLE);
        else if (LikesType.isUnable(savedLike.get().getStatus()))
            savedLike.get().setStatus(LikesType.ENABLE);

        return true;
    }

}

