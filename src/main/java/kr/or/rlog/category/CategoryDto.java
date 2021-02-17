package kr.or.rlog.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long parentId;
    private List<CategoryDto> subCategories;

    public CategoryDto(Long categoryId, String categoryName, Long parentId){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", parentId=" + parentId +
                ", subCategories=" + subCategories +
                '}';
    }
}
