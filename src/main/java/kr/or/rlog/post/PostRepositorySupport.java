package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositorySupport {
    public Page<Post> findAllByStatusNotAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category);

    public Page<Post> findAllByStatusAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category);

    /**
     * @methodName: findPostPage
     * @author: rojae
     * @date: 2021-08-16
     * @Description: post의 리스트를 전체적으로 뽑는 기능. (카테고리에 상관 없이 뽑는다)
     */
    public Page<PostDto> findPostPage(Pageable pageable, String keyword, Account user);
}
