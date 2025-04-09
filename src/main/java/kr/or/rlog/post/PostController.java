package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.category.CategoryService;
import kr.or.rlog.comment.CommentService;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PostController {

    private final static int pageSize = 7;
    private final static int blockSize = 5;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin/write")
    @Secured("ROLE_ADMIN")
    public String write() {
        return "blog/admin/page-blog-write";
    }

    /** ==================================================================
     * @methodName : savePost
     * @description : 글 쓰기 완료
     * @func1 : 글 쓰기로 인한 글 저장.
     * @func2 : 리다이렉트.
     * @author: rojae
     * @date : 2021-08-23
     ==================================================================**/
    @PostMapping("/admin/write")
    @Secured("ROLE_ADMIN")
    public String savePost(@CurrentUser Account user, @ModelAttribute Post post) {
        Post savedPost = postService.createNew(post, user);
        if (savedPost != null)
            return "redirect:/post/" + savedPost.getId();
        else
            return "blog/error";
    }

    /** ==================================================================
     * @methodName : edit
     * @description : 글 수정 화면.
     * @func1 : 해당 글을 1차 캐싱 이후, 데이터 전달.
     * @func2 : 글 수정 권한 검사.
     * @author: rojae
     * @date : 2021-08-23
     ==================================================================**/
    @GetMapping("/admin/write/{id}")
    @Secured("ROLE_ADMIN")
    public String edit(Model model, @PathVariable Long id, @CurrentUser Account account) {
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()) {
            if (post.get().getWriter().getId().equals(account.getId())) {
                model.addAttribute("post", post.get());
                model.addAttribute("category", post.get().getCategory());
            } else
                model.addAttribute("message", "권한이 없는 글입니다");
        } else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "blog/admin/page-blog-write";
    }

    /** ==================================================================
     * @methodName : editSave
     * @description : 글 수정 작업 완료.
     * @func1 : 글 수정 작업, 권한 검사.
     * @func2 : 해당 글을 1차 캐싱 이후, 수정 완료.
     * @author : rojae
     * @date : 2021-08-23
     ==================================================================**/
    @PostMapping("/admin/write/{id}")
    @Secured("ROLE_ADMIN")
    public String editSave(Model model, @ModelAttribute Post newPost, @PathVariable Long id, @CurrentUser Account account) {
        Optional<Post> oldPost = postService.getPost(id);
        if (oldPost.isPresent()) {
            if (oldPost.get().getWriter().getId().equals(account.getId())) {
                postService.editSave(newPost);
            } else
                model.addAttribute("message", "권한이 없는 글입니다");
        } else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "redirect:/post/" + newPost.getId();
    }

    /** ==================================================================
     * @methodName : getPost
     * @description : 단일 게시글 조회.
     * @func1 : 단일 게시글의 데이터를, 객체로 데이터 전송.
     * @author: rojae
     * @date : 2021-08-23
     ==================================================================**/
    @GetMapping("post/{id}")
    public ModelAndView getPost(@PathVariable Long id, @CurrentUser Account account) {
        ModelAndView mav = new ModelAndView(".blog.nav/page-blog-post");
        PostDetailDto postDetailDto = postService.getPostDetail(id);
        CategoryDto category = postDetailDto.getCategory();

        if (postDetailDto != null) {
            if (account != null)
                mav.addObject("isWriter", postDetailDto.getWriter().getId().equals(account.getId()));
            mav.addObject("post", postDetailDto);
            mav.addObject("categories", categoryService.getParentsAndMe(new Category(category.getCategoryId())));
        } else
            mav.addObject("message", "존재하지 않는 글입니다");
        return mav;
    }

    /** ==================================================================
     * @methodName : deletePost
     * @description : 해당 게시글을 삭제한다.
     * @func1 : 게시글 삭제.
     * @func2 : 페이지 이동.
     * @author: rojae
     * @date : 2021-08-23
     ==================================================================**/
    @DeleteMapping("post/{id}")
    @ResponseBody
    public ResponseEntity<Message> deletePost(Model model, @PathVariable Long id, @CurrentUser Account account) {
        if (postService.deletePost(id, account))
            return new ResponseEntity<Message>(Message.builder().response("삭제되었습니다. 자동으로 메인으로 이동합니다.").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("접근이 거부되었습니다.").code("403").build(), HttpStatus.FORBIDDEN);
    }

    /** ==================================================================
     * @methodName : myPosts
     * @description : 내 정보 > 내가 쓴 글 조회
     * @func1 : 내 글 페이징 조회.
     * @author: rojae
     * @date : 2021-08-23
     ==================================================================**/
    @GetMapping("posts")
    @Secured("ADMIN")
    @ResponseBody
    public ResponseEntity myPosts(@CurrentUser Account user, @PageableDefault(page = 0, size = pageSize, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> message = new HashMap<>();

        Page<PostDto> posts = postService.getPage(pageable, "", user);

        int pageNumber = (posts.getPageable().isPaged()) ? posts.getPageable().getPageNumber() : 0;    //  현재페이지
        int totalPages = posts.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
        int pageBlock = blockSize;  //블럭의 수 1, 2, 3, 4, 5
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1; //현재 페이지가 7이라면 1*5+1=6
        int endBlockPage = startBlockPage + pageBlock - 1; //6+5-1=10. 6,7,8,9,10해서 10.
        endBlockPage = Math.min(totalPages, endBlockPage);

        message.put("startBlockPage", startBlockPage);
        message.put("endBlockPage", endBlockPage);
        message.put("posts", posts);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** ==================================================================
     * @methodName : getPostByCategory
     * @description : 카테고리 선택을 통한, 글 조회
     * @func1 : 내 글 페이징 조회.
     * @author: rojae
     * @date : 2021-08-23
    ==================================================================**/
    @GetMapping("category/{categoryId}/posts")
    public String  getPostByCategory(Model model
            , @PathVariable Long categoryId
            , @CurrentUser Account user
            , @RequestParam(value = "keyword", defaultValue = "") String keyword
            , @PageableDefault(page = 0, size = pageSize, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PostDto> postPage = postService.getPageByCategory(pageable, keyword, user, categoryId);

        int pageNumber = (postPage.getPageable().isPaged()) ? postPage.getPageable().getPageNumber() : 0;    //  현재페이지
        int totalPages = postPage.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
        int pageBlock = blockSize;  //블럭의 수 1, 2, 3, 4, 5
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1; //현재 페이지가 7이라면 1*5+1=6
        int endBlockPage = startBlockPage + pageBlock - 1; //6+5-1=10. 6,7,8,9,10해서 10.
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("postPage", postPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryName", categoryService.getById(categoryId).get().getCategoryName());

        return "blog/page-blog-category";
    }


}
