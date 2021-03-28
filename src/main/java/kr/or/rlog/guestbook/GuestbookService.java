package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestbookService {

    @Autowired
    private GuestbookRepository guestbookRepository;

    public boolean createNew(Account account, Guestbook guestbook){
        Guestbook newGuestbook = new Guestbook(account, guestbook.getContent());
        guestbookRepository.save(newGuestbook);
        return true;
    }

    public Page<GuestbookDto> getPage(Pageable pageable){
        Page<Guestbook> pages = guestbookRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<GuestbookDto> list = new ArrayList<>();
        for (Guestbook origin : pages) {
            GuestbookDto target = new GuestbookDto();
            BeanUtils.copyProperties(origin, target);
            target.setWriter(
                    new AccountDto(origin.getWriter().getEmail()
                            ,origin.getWriter().getUserName()
                            ,origin.getWriter().getProfileImage()
                    )
            );
            list.add(target);
        }
        return new PageImpl<GuestbookDto>(list, pages.getPageable(), pages.getTotalElements());
    }
}
