package kr.or.rlog.comment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TBL_COMMENT")
public class Comment {

    @Id @GeneratedValue
    private Long Id;


}
