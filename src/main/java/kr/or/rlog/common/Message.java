package kr.or.rlog.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private String response;
    private String code;

    @Builder
    public Message(String response, String code){
        this.response = response;
        this.code = code;
    }

}
