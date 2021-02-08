package kr.or.rlog.post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TBL_POST")
public class Post {

    @Id @GeneratedValue
    private Long Id;

}
