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

    /*
     * proc1 : 좋아요 -> 좋아요 취소
     * proc2 : 좋아요 취소 -> 좋아요
     * done : 현재로써는 좋아요 > 좋아요 취소만 사용
     */
    @Transactional
    public boolean editProc(Account user, Long postId) {
        Optional<PostLikes> savedLike = Optional.ofNullable(postLikesRepository.findByAccountAndPostAndStatus(user, new Post(postId), LikesType.ENABLE));

        if (!savedLike.isPresent())
            return false;
        else if (LikesType.isEnable(savedLike.get().getStatus()))
            savedLike.get().setStatus(LikesType.UNABLE);
        else if (LikesType.isUnable(savedLike.get().getStatus()))
            savedLike.get().setStatus(LikesType.ENABLE);

        return true;
    }

}

