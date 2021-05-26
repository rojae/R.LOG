package kr.or.rlog.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public CategoryDto getAll(Model model) {
        return categoryService.createRoot();
    }

    @GetMapping("/categories/{parentId}")
    @ResponseBody
    public List<CategoryOne> getCategories(@PathVariable Long parentId){
        return categoryService.getByParentId(parentId);
    }

}
