package kr.or.rlog.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TBL_CATEGORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @GeneratedValue @Id
    private Long id;
    private String categoryName;
    private Long parentId;

    public Category(String categoryName, Long parentId){
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

}
