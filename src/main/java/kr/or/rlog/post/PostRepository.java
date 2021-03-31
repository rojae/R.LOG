package kr.or.rlog.post;


import kr.or.rlog.common.Status;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);
    public Page<Post> findAllByTitleContainsIgnoreCaseOrderByCreatedDateDesc(Pageable pageable, String keyword);

    public Page<Post> findAllByStatusOrderByCreatedDateDesc(Pageable pageable, Status status);
    public Page<Post> findAllByTitleContainsIgnoreCaseAndStatusOrderByCreatedDateDesc(Pageable pageable, String keyword, Status status);

    public Optional<Post> findByIdAndStatus(Long id, Status status);
}
