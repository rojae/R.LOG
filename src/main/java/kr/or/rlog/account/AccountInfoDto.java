package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.guestbook.Guestbook;
import kr.or.rlog.likey.PostLikes;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AccountInfoDto {
    private String email;
    private String userName;
    private String profileImage;
    private PlatformType platformType;
    private boolean recvMail;
    private String role;

    private Set<Comment> comments;
    private Set<Guestbook> guestbooks;
    private Set<PostLikes> postLikes;

    public AccountInfoDto(){

    }

    public AccountInfoDto(Account account){
        this.email = account.getEmail();
        this.userName = account.getUserName();
        this.profileImage = account.getProfileImage();
        this.platformType = account.getPlatformType();
        this.role = account.getRole();
        this.comments = account.getComments();
        this.guestbooks = account.getGuestbooks();
        this.postLikes = account.getPostLikes();
        this.recvMail = account.isRecvMail();
    }


}
