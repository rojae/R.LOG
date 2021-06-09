package kr.or.rlog.post;


import kr.or.rlog.category.Category;
import kr.or.rlog.common.Status;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAllByStatusNotOrderByCreatedDateDesc(Pageable pageable, Status status);
    public Page<Post> findAllByTitleContainsIgnoreCaseAndStatusNotOrderByCreatedDateDesc(Pageable pageable, String keyword, Status status);

    public Page<Post> findAllByStatusOrderByCreatedDateDesc(Pageable pageable, Status status);
    public Page<Post> findAllByTitleContainsIgnoreCaseAndStatusOrderByCreatedDateDesc(Pageable pageable, String keyword, Status status);


    public Page<Post> findAllByStatusNotAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category);
    public Page<Post> findAllByTitleContainsIgnoreCaseAndStatusNotAndCategoryOrderByCreatedDateDesc(Pageable pageable, String keyword, Status status, Category category);

    public Page<Post> findAllByStatusAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category);
    public Page<Post> findAllByTitleContainsIgnoreCaseAndStatusAndCategoryOrderByCreatedDateDesc(Pageable pageable, String keyword, Status status, Category category);


    public Optional<Post> findByIdAndStatus(Long id, Status status);
    public Optional<Post> findByIdAndStatusNot(Long id, Status status);

}
