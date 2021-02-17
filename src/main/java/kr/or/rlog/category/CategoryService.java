package kr.or.rlog.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
        CategoryDto rootCategoryDto = new CategoryDto(0l, "ROOT", null);
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

}
