package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;

public interface PostLikesRepositorySupport {
    boolean existsByAccountAndPostAndStatus(Account account, Post post, LikesType status);
    PostLikes findByAccountAndPostAndStatus(Account user, Post post, LikesType likesType);

}
