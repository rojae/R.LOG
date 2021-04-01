package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuestbookService {

    @Autowired
    private GuestbookRepository guestbookRepository;

    public boolean createNew(Account account, Guestbook guestbook){
        Guestbook newGuestbook = new Guestbook(account, guestbook.getContent(), guestbook.getStatus());
        guestbookRepository.save(newGuestbook);
        return true;
    }

    public Page<GuestbookDto> getPage(Pageable pageable, Account user){
        Page<Guestbook> pages;
        if(user != null && user.getRole().equals("ADMIN"))
            pages = guestbookRepository.findAllByOrderByCreatedDateDesc(pageable);
        else
            pages = guestbookRepository.findAllByWriterOrStatusOrderByCreatedDateDesc(pageable, user, Status.ENABLE);

        List<GuestbookDto> list = new ArrayList<>();
        for (Guestbook origin : pages) {
            GuestbookDto target = new GuestbookDto();
            BeanUtils.copyProperties(origin, target);
            target.setWriter(
                    new AccountDto(
                            origin.getWriter().getId()
                            ,origin.getWriter().getEmail()
                            ,origin.getWriter().getUserName()
                            ,origin.getWriter().getProfileImage()
                    )
            );
            target.setStatus(origin.getStatus());

            if(user != null && user.getId().equals(origin.getWriter().getId())) {
                target.setMine(true);
            }

            list.add(target);
        }
        return new PageImpl<GuestbookDto>(list, pages.getPageable(), pages.getTotalElements());
    }

    public Guestbook getOneIfMine(Long id, Account currentUser){
        Optional<Guestbook> guestbook = guestbookRepository.findById(id);
        if( currentUser != null && guestbook.isPresent() && guestbook.get().getWriter().getId().equals(currentUser.getId())){
            return guestbook.get();
        }
        return null;
    }

    public Optional<Guestbook> getOne(Long id){
        return guestbookRepository.findById(id);
    }

    @Transactional
    public boolean editProc(Long id, Guestbook guestbook) {
        Optional<Guestbook> savedGuestbook = guestbookRepository.findById(id);
        if(savedGuestbook.isPresent()) {
            savedGuestbook.get().setContent(guestbook.getContent());
            savedGuestbook.get().setStatus(guestbook.getStatus());
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public boolean deleteProc(Long id) {
        Optional<Guestbook> savedGuestbook = guestbookRepository.findById(id);
        if(savedGuestbook.isPresent()) {
            savedGuestbook.get().setStatus(Status.UNABLE);
            return true;
        }else{
            return false;
        }
    }
}
