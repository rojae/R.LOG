package kr.or.rlog.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query(value = "select count(*) from tbl_visitor t WHERE t.created_date between CURRENT_DATE AND  CURRENT_DATE+1", nativeQuery = true)
    public int getTodayCount();

    @Query(value = "select count(*) from tbl_visitor t WHERE year(t.created_date) = year(CURRENT_DATE) and month(t.created_date) = month(CURRENT_DATE)", nativeQuery = true)
    public int getMonthCount();

    @Query(value = "select count(*) from tbl_visitor", nativeQuery = true)
    public int getAllCount();
}
