package kr.or.rlog.category;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CategoryOne {
    private Long id;
    private String categoryName;
    private Long parentId;

    public CategoryOne(){

    }

    public CategoryOne(Long id, String categoryName, Long parentId){
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

    // Entity -> CategoryOne
    public static CategoryOne of(Category category) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Category.class, CategoryOne.class)
                .addMapping(Category::getId, CategoryOne::setId)
                .addMapping(Category::getCategoryName, CategoryOne::setCategoryName)
                .addMapping(Category::getParentId, CategoryOne::setParentId);
                //.addMappings(mapper -> mapper.map(src -> src.getPosts().size(), CategoryOne::setPostCount));

        return modelMapper.map(category, CategoryOne.class);
    }
}
