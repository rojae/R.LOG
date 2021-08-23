package kr.or.rlog.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDto createRoot(){
        Map<Long, List<CategoryDto>> groupingByParent = categoryRepository.findAll()
                .stream()
                .map(ce -> new CategoryDto(ce.getId(), ce.getCategoryName(), ce.getParentId()))
                .collect(groupingBy(CategoryDto::getParentId));
        CategoryDto rootCategoryDto = new CategoryDto(0L, "ROOT", null);
        addSubCategories(rootCategoryDto, groupingByParent);

        return rootCategoryDto;
    }

    private void addSubCategories(CategoryDto parent, Map<Long, List<CategoryDto>> groupingByParentId){
        List<CategoryDto> subCategories = groupingByParentId.get(parent.getCategoryId());

        if(subCategories == null)
            return;

        parent.setSubCategories(subCategories);

        subCategories
                .forEach(s -> {
                    addSubCategories(s, groupingByParentId);
                });
    }

    public Category getParent(Category category){
        if(category.getParentId() == null) return null;
        Optional<Category> parent = categoryRepository.findById(category.getParentId());
        return parent.orElse(null);
    }


    public List<Category> getParentsAndMe(Category category){
        ArrayList<Category> list = new ArrayList<>();
        list.add(category);

        while(true) {
            Category parent = this.getParent(category);
            if(parent == null)
                break;
            list.add(parent);
            category = parent;
        }
        Collections.reverse(list);
        return list;
    }


    public List<CategoryOne> getByParentId(Long parentId) {
        List<Category> list = categoryRepository.findByParentId(parentId);
        return list.stream().map(CategoryOne::of).collect(Collectors.toList());
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public Optional<CategoryOne> getById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryOne::of);
    }

    @Transactional
    public boolean editCategory(Long id, String categoryName){
        Optional<Category> category =  categoryRepository.findById(id);
        if(category.isPresent()){
            category.get().setCategoryName(categoryName);
            return true;
        }
        else return false;
    }

}
