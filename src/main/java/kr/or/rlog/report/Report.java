package kr.or.rlog.report;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CheckStatus;
import kr.or.rlog.common.CreateTimeEntity;
import kr.or.rlog.common.ReadStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TBL_REPORT")
public class Report extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="report_seq")
    @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Column(name = "read_status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'UNREAD' ")
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    @Column(name = "check_status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'UNCHECK' ")
    @Enumerated(EnumType.STRING)
    private CheckStatus checkStatus;



}
