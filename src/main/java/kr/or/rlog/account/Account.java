package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.BaseTimeEntity;
import kr.or.rlog.mail.Mail;
import kr.or.rlog.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "TBL_ACCOUNT")
@Getter
@Setter
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "platformType", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'RLOG' ")
    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "accessToken")
    private String accessToken;

    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'USER' ")
    private String role;

    @Column(name = "isAuth", columnDefinition = "TINYINT DEFAULT 0", length = 1)
    private boolean isAuth;

    @OneToMany(mappedBy = "account")
    private Set<Mail> mails = new HashSet<Mail>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<Post>();

    @OneToMany(mappedBy = "writer")
    private Set<Comment> comments = new HashSet<Comment>();

    public void addMail(Mail mail) {
        this.mails.add(mail);
        mail.setAccount(this);
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.setWriter(this);
    }

    public boolean postIsMine(Post post) {
        for(Post p : this.posts){
            if(p.getId().equals(post.getId()))
                return true;
        }
        return false;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setWriter(this);
    }

    public void deleteComment(Comment comment) {
        comments.removeIf(one -> one.getId().equals(comment.getId()));
    }

    // 패스워드 암호화 기법
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}