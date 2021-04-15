package kr.or.rlog.visit;

import kr.or.rlog.common.CreateTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TBL_VISITOR")
public class Visitor extends CreateTimeEntity {

    public Visitor (String ip, String refer, String agent){
        this.ip = ip;
        this.refer = refer;
        this.agent = agent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="visitor_seq")
    @SequenceGenerator(name = "visitor_seq", sequenceName = "visitor_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "refer", nullable = true)
    private String refer;

    @Column(name = "agent", nullable = true)
    private String agent;

}

