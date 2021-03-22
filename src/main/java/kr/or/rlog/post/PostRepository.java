package kr.or.rlog.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByCreatedDateDesc();
    public Page<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);
    public Page<Post> findAllByTitleContainsIgnoreCaseOrderByCreatedDateDesc(Pageable pageable, String keyword);
}
