package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    public Page<Guestbook> findAllByStatusNotOrderByCreatedDateDesc(Pageable pageable, Status unable);

    public Page<Guestbook> findAllByWriterAndStatusNotOrStatusOrderByCreatedDateDesc(Pageable pageable, Account writer, Status unable, Status enable);

}
