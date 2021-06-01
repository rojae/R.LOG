package kr.or.rlog.category;

import kr.or.rlog.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public CategoryDto getAll(Model model) {
        return categoryService.createRoot();
    }

    @GetMapping("/categories/{parentId}")
    @ResponseBody
    public List<CategoryOne> getCategories(@PathVariable Long parentId){
        return categoryService.getByParentId(parentId);
    }

    @DeleteMapping("/categories/{id}")
    @Secured("ADMIN")
    @ResponseBody
    public ResponseEntity<Message> deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
        Message message = Message.builder().code("200").response("카테고리가 제거되었습니다.").build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/category")
    @Secured("ADMIN")
    @ResponseBody
    public ResponseEntity<Message> createCategory(@RequestBody CategoryOne category){
        categoryRepository.save(new Category(category));
        Message message = Message.builder().code("200").response("카테고리가 생성되었습니다.").build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    @Secured("ADMIN")
    @ResponseBody
    public CategoryOne getCategory(@PathVariable Long id){
        return categoryService.getById(id).get();
    }

    @PutMapping("/category/{id}")
    @Secured("ADMIN")
    @ResponseBody
    public ResponseEntity<Message> editCategory(@PathVariable Long id, @RequestBody String categoryName){
        if(categoryService.editCategory(id, categoryName)){
            Message message = Message.builder().code("200").response("카테고리가 수정되었습니다.").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }else{
            Message message = Message.builder().code("200").response("카테고리 수정을 실패했습니다.").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

}
