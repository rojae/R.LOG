package kr.or.rlog.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.or.rlog.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "TBL_CATEGORY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private Long parentId;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Post> posts = new HashSet<Post>();

    public void addPost(Post post){
        this.posts.add(post);
        post.setCategory(this);
    }

    public Category(Long id){
        this.id = id;
    }

    public Category(String categoryName, Long parentId){
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

    public Category(CategoryOne categoryOne){
        this.categoryName = categoryOne.getCategoryName();
        this.parentId = categoryOne.getParentId();
    }

}
