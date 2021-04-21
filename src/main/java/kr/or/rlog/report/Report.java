package kr.or.rlog.report;

import kr.or.rlog.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TBL_REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="report_seq")
    @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

}
