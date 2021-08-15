package kr.or.rlog.query;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * QueryDSL Utils
 */
public class QueryUtils {

    public static StringExpression postUrl(NumberPath postId) {
        if(postId == null)
            return null;
        else{
            StringExpression prefix = Expressions.asString("/post/");
            return prefix.concat(postId.stringValue());
        }
    }

    public static StringExpression dateTimeToYYYYMMDD(DateTimePath time){
        if(time == null)
            return null;
        else {
            return Expressions.stringTemplate("DATE_FORMAT({0}, {1})", time, ConstantImpl.create("%y.%m.%d %H:%i:%S"));
        }
    }
}
