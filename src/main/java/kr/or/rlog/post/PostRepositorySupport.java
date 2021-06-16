package kr.or.rlog.post;

import kr.or.rlog.category.Category;
import kr.or.rlog.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositorySupport{
        public Page<Post> findAllByStatusNotAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category);
        public Page<Post> findAllByStatusAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category);
}
