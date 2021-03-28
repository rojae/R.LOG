package kr.or.rlog.guestbook;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    public Page<Guestbook> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
