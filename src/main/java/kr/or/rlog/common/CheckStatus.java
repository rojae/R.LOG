package kr.or.rlog.common;

import lombok.Getter;

/*
 *
 * UNCHECK : 미체크
 * READ : 읽음
 * PROCESS : 진행
 * DONE : 종료
 */
@Getter
public enum CheckStatus {
    UNCHECK,
    READ,
    PROCESS,
    DONE;
}
