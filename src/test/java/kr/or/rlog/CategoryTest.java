package kr.or.rlog;

import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.category.CategoryRepository;
import kr.or.rlog.category.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void top() throws Exception {
        // GIVEN
        List<Category> categoryList = createCategories();
        given(categoryRepository.findAll())
                .willReturn(categoryList);

        // WHEN
        CategoryDto categoryRoot = categoryService.createRoot();

        // THEN
        verify(categoryRepository, atLeastOnce()).findAll();

        // ROOT
        assertThat(categoryRoot.getSubCategories().size()).isEqualTo(2);
        System.out.println(categoryRoot);

        // SUB1
        assertThat(categoryRoot.getSubCategories()
                .get(0).getSubCategories().size()).isEqualTo(2);
        System.out.println(categoryRoot.getSubCategories().get(0));

        // SUB2
        assertThat(categoryRoot.getSubCategories()
                .get(1).getSubCategories().size()).isEqualTo(2);
        System.out.println(categoryRoot.getSubCategories().get(1));

    }

    private List<Category> createCategories() {
        Category sub1 = new Category("SUB1", 0L);
        Category sub2 = new Category("SUB2", 0L);
        Category sub11 = new Category("SUB1-1", 1L);
        Category sub12 = new Category("SUB1-2", 1L);
        Category sub21 = new Category("SUB2-1", 2L);
        Category sub22 = new Category("SUB2-2", 2L);

        ReflectionTestUtils.setField(sub1, "id", 1L);
        ReflectionTestUtils.setField(sub2, "id", 2L);
        ReflectionTestUtils.setField(sub11, "id", 3L);
        ReflectionTestUtils.setField(sub12, "id", 4L);
        ReflectionTestUtils.setField(sub21, "id", 5L);
        ReflectionTestUtils.setField(sub22, "id", 6L);

        return Arrays.asList(
            sub1, sub2, sub11, sub12, sub21, sub22
        );
    }

}
