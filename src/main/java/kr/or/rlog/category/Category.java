package kr.or.rlog.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.or.rlog.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "TBL_CATEGORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private Long parentId;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Post> posts = new HashSet<Post>();

    public void addPost(Post post){
        this.posts.add(post);
        post.setCategory(this);
    }

    public Category(String categoryName, Long parentId){
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

}
