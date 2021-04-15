package kr.or.rlog.visit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

 /*   NOT yet
    @Query(value = "select count(t) from TBL_VISITOR t WHERE t.createdDate = :time", nativeQuery = true)
    public int getCount(@Param("time") LocalDateTime time);*/

}
