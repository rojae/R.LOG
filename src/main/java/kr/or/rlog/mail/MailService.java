package kr.or.rlog.mail;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountRepository;
import kr.or.rlog.utils.RandomUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service("mailService")
@AllArgsConstructor
public class MailService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailRepository mailRepository;

    private static final String signupUrlTest = "http://localhost:8080/api/v1/signup/mail";
    private static final String updateUrlTest = "http://localhost:8080/api/v1/update/mail";

    private static final String signupUrl = "https://rlog.or.kr/api/v1/signup/mail";
    private static final String updateUrl = "https://rlog.or.kr/api/v1/update/mail";

    private static final String fromAddress = "rojae@rlog.or.kr";

    private JavaMailSender mailSender;

    public void signupSend(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:595px;width:100%;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;background-color:#fff;-webkit-text-size-adjust:100%;text-align:left\" class=\"__web-inspector-hide-shortcut__\">" +
                "<!-- Header -->" +
                "<tbody>" +
                "<tr><td height=\"30\"></td></tr>" +
                "                    <tr><td style=\"padding-right:27px; padding-left:21px\">" +
                "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "                                <tbody><tr><td style=\"\" width=\"61\">" +
                "                                        <img src=\"https://rojae2.cafe24.com/rlog/images/logo.png\" alt=\"IALAB\" width=\"61\" loading=\"lazy\">" +
                "                                    </td><td style=\"padding-left:5px\">" +
                "                                        <img src=\"https://ssl.pstatic.net/static/common/ems/nid_dm/nid_201412.gif\" alt=\"회원정보\" width=\"42\" loading=\"lazy\">" +
                "                                    </td></tr>                            " +
                "                           </tbody></table>" +
                "                        </td></tr>" +
                "                    <tr><td height=\"13\"></td></tr>" +
                "                   <tr><td style=\"padding-right:27px; padding-left:18px;line-height:34px;font-size:29px;color:#424240;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                            R.LOG<br/><span style=\"color:#7802FD\">가입 인증메일입니다.</span>" +
                "                   </td></tr>                    " +
                "                  <tr><td height=\"22\"></td></tr>                    " +
                "<tr><td height=\"1\" style=\"background-color: #e5e5e5;\"></td></tr>                    " +
                "<!-- //Header -->                    " +
                "<tr><td style=\"padding-top:24px; padding-right:27px; padding-bottom:32px; padding-left:20px\">" +
                "<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                <tbody><tr><td height=\"6\"></td></tr>                                " +
                "                               <tr style=\"display:none;\"><td style=\"padding:9px 15px 10px;background-color:#f4f4f4;font-size:14px;color:#000;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">                                        " +
                "                               이 메일은 <span style=\"color:#03c75a\">" + mail.getUserName() + "의 등록된 메일 주소로 발송되었습니다. </td></tr><tr style=\"display:none;\"><td height=\"24\"></td></tr>                                " +
                "                               <tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">                                        " +
                "                               회원<span style=\"color:#009E25\">" + mail.getUserName() + "</span>이(가) <strong>회원가입 메일인증을 시도합니다</strong><br><br>회원님의 활동이 아니라면, 다른 사람이 메일계정을 알고 있는 것 입니다. </td></tr>" +
                "                                <tr><td height=\"24\"></td></tr>                                " +
                "                               <tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;margin:0;padding:0\">" +
                "                                            <tbody><tr><td height=\"23\" style=\"font-weight:bold;color:#000;vertical-align:top;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                                    인증 요구정보" +
                "                                               </td></tr>" +
                "                                            <tr><td height=\"2\" style=\"background:#424240\"></td></tr>" +
                "                                            <tr><td height=\"20\"></td></tr>" +
                "                                            <tr><td>" +
                "                                                    <table cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;margin:0;padding:0\">" +
                "                                                        <tbody><tr><td width=\"110\" style=\"padding-bottom:5px;color:#696969;line-height:23px;vertical-align:top;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                                                만료시간" +
                "                                                            </td><td style=\"padding-bottom:5px;color:#000;line-height:23px;vertical-align:top;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "" + mail.getExpireDate().getYear() + "년 " + mail.getExpireDate().getMonthValue() + "월 " + mail.getExpireDate().getDayOfMonth() + "일 " + mail.getExpireDate().getHour() + "시 " + mail.getExpireDate().getMinute() + "분 " + mail.getExpireDate().getSecond() + "초</td></tr>" +
                "<tr><td height=\"1\" style=\"background:#d5d5d5\"></td></tr>" +
                "   </tbody></table>" +
                "</td></tr><tr><tr><td height=\"2\" style=\"background:#424240\"></td></tr> </tr>" +
                "<tr><td height=\"24\"></td></tr>" +
                "<tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "<strong>인증을 완료하시겠습니까?<br>" +
                "지금 바로 “인증하기”를 눌러주세요.</strong>" +
                "</td></tr>" +
                "<tr><td style=\"height:34px;font-size:14px;color:#696969;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "<a href=\"" + signupUrl + "?secretKey=" + mail.getSecretKey() + "&email=" + mail.getEmail() + "\"style=\"display:inline-block;padding:10px 10px 10px; margin-top:10px; background-color:#08a600; color:#fff;text-align: center; text-decoration: none;\" target=\"_blank\" rel=\"noreferrer noopener\">인증하기</a>" +
                "</td></tr>" +
                "<tr><td height=\"24\"></td></tr>" +
                "<!-- footer -->                    <tr><td style=\"padding-top:26px;padding-left:21px;padding-right:21px;padding-bottom:13px;background:#f9f9f9;font-size:12px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#696969;line-height:17px\">                            본 메일은 발신전용 입니다. 서비스에 대한 문의사항은 R.LOG <a href=\"http://rlog.or.kr\" style=\"color:#696969;font-weight:bold;text-decoration:underline\" rel=\"noreferrer noopener\" target=\"_blank\">홈페이지</a>에서 확인해주세요.                        </td></tr>                    <tr><td style=\"padding-left:21px;padding-right:21px;padding-bottom:57px;background:#f9f9f9;font-size:12px;font-family:Helvetica;color:#696969;line-height:17px\">                            Copyright ⓒ <strong>R.LOG</strong> Corp. All Rights Reserved.                        </td></tr>                    <!-- //footer -->                </tbody></table>"
                + "<br/><br/>";
        //"<img src=\"\">";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail.getEmail());
        helper.setSubject("[R.LOG] 회원가입 인증메일입니다");
        helper.setFrom(fromAddress);
        mailSender.send(mimeMessage);
    }


    public void mailSend(Mail mail, String title) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:595px;width:100%;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;background-color:#fff;-webkit-text-size-adjust:100%;text-align:left\" class=\"__web-inspector-hide-shortcut__\">" +
                "<!-- Header -->" +
                "<tbody>" +
                "<tr><td height=\"30\"></td></tr>" +
                "                    <tr><td style=\"padding-right:27px; padding-left:21px\">" +
                "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "                                <tbody><tr><td style=\"\" width=\"61\">" +
                "                                        <img src=\"https://rojae2.cafe24.com/rlog/images/logo.png\" alt=\"IALAB\" width=\"61\" loading=\"lazy\">" +
                "                                    </td><td style=\"padding-left:5px\">" +
                "                                        <img src=\"https://ssl.pstatic.net/static/common/ems/nid_dm/nid_201412.gif\" alt=\"회원정보\" width=\"42\" loading=\"lazy\">" +
                "                                    </td></tr>                            " +
                "                           </tbody></table>" +
                "                        </td></tr>" +
                "                    <tr><td height=\"13\"></td></tr>" +
                "                   <tr><td style=\"padding-right:27px; padding-left:18px;line-height:34px;font-size:29px;color:#424240;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                            R.LOG<br/><span style=\"color:#7802FD\">신규 이메일 등록 인증메일입니다.</span>" +
                "                   </td></tr>                    " +
                "                  <tr><td height=\"22\"></td></tr>                    " +
                "<tr><td height=\"1\" style=\"background-color: #e5e5e5;\"></td></tr>                    " +
                "<!-- //Header -->                    " +
                "<tr><td style=\"padding-top:24px; padding-right:27px; padding-bottom:32px; padding-left:20px\">" +
                "<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                <tbody><tr><td height=\"6\"></td></tr>                                " +
                "                               <tr style=\"display:none;\"><td style=\"padding:9px 15px 10px;background-color:#f4f4f4;font-size:14px;color:#000;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">                                        " +
                "                               이 메일은 <span style=\"color:#03c75a\">" + mail.getUserName() + "의 등록된 메일 주소로 발송되었습니다. </td></tr><tr style=\"display:none;\"><td height=\"24\"></td></tr>                                " +
                "                               <tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">                                        " +
                "                               회원<span style=\"color:#009E25\">" + mail.getUserName() + "</span>이(가) <strong>회원가입 메일인증을 시도합니다</strong><br><br>회원님의 활동이 아니라면, 다른 사람이 메일계정을 알고 있는 것 입니다. </td></tr>" +
                "                                <tr><td height=\"24\"></td></tr>                                " +
                "                               <tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                        <table cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;margin:0;padding:0\">" +
                "                                            <tbody><tr><td height=\"23\" style=\"font-weight:bold;color:#000;vertical-align:top;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                                    인증 요구정보" +
                "                                               </td></tr>" +
                "                                            <tr><td height=\"2\" style=\"background:#424240\"></td></tr>" +
                "                                            <tr><td height=\"20\"></td></tr>" +
                "                                            <tr><td>" +
                "                                                    <table cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;margin:0;padding:0\">" +
                "                                                        <tbody><tr><td width=\"110\" style=\"padding-bottom:5px;color:#696969;line-height:23px;vertical-align:top;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                                                만료시간" +
                "                                                            </td><td style=\"padding-bottom:5px;color:#000;line-height:23px;vertical-align:top;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "" + mail.getExpireDate().getYear() + "년 " + mail.getExpireDate().getMonthValue() + "월 " + mail.getExpireDate().getDayOfMonth() + "일 " + mail.getExpireDate().getHour() + "시 " + mail.getExpireDate().getMinute() + "분 " + mail.getExpireDate().getSecond() + "초</td></tr>" +
                "<tr><td height=\"1\" style=\"background:#d5d5d5\"></td></tr>" +
                "   </tbody></table>" +
                "</td></tr><tr><tr><td height=\"2\" style=\"background:#424240\"></td></tr> </tr>" +
                "<tr><td height=\"24\"></td></tr>" +
                "<tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "<strong>인증을 완료하시겠습니까?<br>" +
                "지금 바로 “인증하기”를 눌러주세요.</strong>" +
                "</td></tr>" +
                "<tr><td style=\"height:34px;font-size:14px;color:#696969;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "<a href=\"" + updateUrl + "?secretKey=" + mail.getSecretKey() + "&email=" + mail.getEmail() + "\"style=\"display:inline-block;padding:10px 10px 10px; margin-top:10px; background-color:#08a600; color:#fff;text-align: center; text-decoration: none;\" target=\"_blank\" rel=\"noreferrer noopener\">인증하기</a>" +
                "</td></tr>" +
                "<tr><td height=\"24\"></td></tr>" +
                "<!-- footer -->                    <tr><td style=\"padding-top:26px;padding-left:21px;padding-right:21px;padding-bottom:13px;background:#f9f9f9;font-size:12px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#696969;line-height:17px\">                            본 메일은 발신전용 입니다. 서비스에 대한 문의사항은 R.LOG <a href=\"http://rlog.or.kr\" style=\"color:#696969;font-weight:bold;text-decoration:underline\" rel=\"noreferrer noopener\" target=\"_blank\">홈페이지</a>에서 확인해주세요.                        </td></tr>                    <tr><td style=\"padding-left:21px;padding-right:21px;padding-bottom:57px;background:#f9f9f9;font-size:12px;font-family:Helvetica;color:#696969;line-height:17px\">                            Copyright ⓒ <strong>R.LOG</strong> Corp. All Rights Reserved.                        </td></tr>                    <!-- //footer -->                </tbody></table>"
                + "<br/><br/>";
        //"<img src=\"\">";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail.getEmail());
        helper.setSubject(title);
        helper.setFrom(fromAddress);
        mailSender.send(mimeMessage);
    }

    public void expireMail(Mail mail, String title) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:595px;width:100%;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;background-color:#fff;-webkit-text-size-adjust:100%;text-align:left\" class=\"__web-inspector-hide-shortcut__\">" +
                "<!-- Header -->" +
                "<tbody>" +
                "<tr><td height=\"30\"></td></tr>" +
                "                    <tr><td style=\"padding-right:27px; padding-left:21px\">" +
                "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "                                <tbody><tr><td style=\"\" width=\"61\">" +
                "                                        <img src=\"https://rojae2.cafe24.com/rlog/images/logo.png\" alt=\"IALAB\" width=\"61\" loading=\"lazy\">" +
                "                                    </td><td style=\"padding-left:5px\">" +
                "                                        <img src=\"https://ssl.pstatic.net/static/common/ems/nid_dm/nid_201412.gif\" alt=\"회원정보\" width=\"42\" loading=\"lazy\">" +
                "                                    </td></tr>                            " +
                "                           </tbody></table>" +
                "                        </td></tr>" +
                "                    <tr><td height=\"13\"></td></tr>" +
                "                   <tr><td style=\"padding-right:27px; padding-left:18px;line-height:34px;font-size:29px;color:#424240;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                            R.LOG<br/><span style=\"color:#7802FD\">해당 메일은 만료되었습니다.</span>" +
                "                   </td></tr>                    " +
                "                  <tr><td height=\"22\"></td></tr>                    " +
                "<tr><td height=\"1\" style=\"background-color: #e5e5e5;\"></td></tr>                    " +
                "<!-- //Header -->                    " +
                "<tr><td style=\"padding-top:24px; padding-right:27px; padding-bottom:32px; padding-left:20px\">" +
                "<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "                                <tbody><tr><td height=\"6\"></td></tr>                                " +
                "                               <tr style=\"display:none;\"><td style=\"padding:9px 15px 10px;background-color:#f4f4f4;font-size:14px;color:#000;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">                                        " +
                "                               이 메일은 <span style=\"color:#03c75a\">" + mail.getUserName() + "의 등록된 메일 주소로 발송되었습니다. </td></tr><tr style=\"display:none;\"><td height=\"24\"></td></tr>                                " +
                "                               <tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">                                        " +
                "                               회원<span style=\"color:#009E25\">" + mail.getUserName() + "</span>이(가) <strong>다른 메일로 변경하였습니다.</strong><br><br>회원님의 활동이 아니라면, 다른 사람이 메일계정을 알고 있는 것 입니다. </td></tr>" +
                "                                <tr><td height=\"24\"></td></tr>                                " +
                "<tr><td height=\"1\" style=\"background:#d5d5d5\"></td></tr>" +
                "   </tbody></table>" +
                "</td></tr><tr><tr><td height=\"2\" style=\"background:#424240\"></td></tr> </tr>" +
                "<tr><td height=\"24\"></td></tr>" +
                "<tr><td style=\"font-size:14px;color:#696969;line-height:24px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;\">" +
                "</td></tr>" +
                "<tr><td height=\"24\"></td></tr>" +
                "<!-- footer -->                    <tr><td style=\"padding-top:26px;padding-left:21px;padding-right:21px;padding-bottom:13px;background:#f9f9f9;font-size:12px;font-family:'나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#696969;line-height:17px\">                            본 메일은 발신전용 입니다. 서비스에 대한 문의사항은 R.LOG <a href=\"http://rlog.or.kr\" style=\"color:#696969;font-weight:bold;text-decoration:underline\" rel=\"noreferrer noopener\" target=\"_blank\">홈페이지</a>에서 확인해주세요.                        </td></tr>                    <tr><td style=\"padding-left:21px;padding-right:21px;padding-bottom:57px;background:#f9f9f9;font-size:12px;font-family:Helvetica;color:#696969;line-height:17px\">                            Copyright ⓒ <strong>R.LOG</strong> Corp. All Rights Reserved.                        </td></tr>                    <!-- //footer -->                </tbody></table>"
                + "<br/><br/>";
        //"<img src=\"\">";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail.getEmail());
        helper.setSubject(title);
        helper.setFrom(fromAddress);
        mailSender.send(mimeMessage);
    }


    public Mail createMail(Account account) {
        Optional<Account> savedAccount = accountRepository.findById(account.getId());
        Mail mail = new Mail.MailBuilder()
                .account(account)
                .email(account.getEmail())
                .auth(false)
                .secretKey(RandomUtils.getAlpha(64))
                .expireDate(LocalDateTime.now().plusMinutes(5))
                //.sentDate(LocalDateTime.now())
                .userName(account.getUserName())
                .build();
        Mail savedMail = this.mailRepository.save(mail);
        savedAccount.ifPresent(s -> {
            s.addMail(savedMail);
        });
        return savedMail;
    }

    @Transactional
    public Mail updateMail(Account account, String newMail) {
        Optional<Account> savedAccount = accountRepository.findById(account.getId());
        if (savedAccount.isPresent()) {
            Mail mail = new Mail.MailBuilder()
                    .account(savedAccount.get())
                    .email(newMail)
                    .auth(false)
                    .secretKey(RandomUtils.getAlpha(64))
                    .expireDate(LocalDateTime.now().plusMinutes(5))
                    //.sentDate(LocalDateTime.now())
                    .userName(savedAccount.get().getUserName())
                    .build();
            Mail savedMail = this.mailRepository.save(mail);
            savedAccount.get().addMail(savedMail);
            return savedMail;
        } else {
            return null;
        }
    }

}
