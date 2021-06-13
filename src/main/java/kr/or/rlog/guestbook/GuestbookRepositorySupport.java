package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestbookRepositorySupport {
    public Page<Guestbook> findAllByStatusNotOrderByCreatedDateDesc(Pageable pageable, Status unable);
    public Page<Guestbook> findAllByWriterAndStatusNotOrStatusOrderByCreatedDateDesc(Pageable pageable, Account writer, Status unable, Status enable);

}
