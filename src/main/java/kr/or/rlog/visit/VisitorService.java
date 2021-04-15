package kr.or.rlog.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    public long todayCount(){
        return visitorRepository.getTodayCount();
    }

    public long monthCount(){
        return visitorRepository.getMonthCount();
    }

    public long allCount(){
        return visitorRepository.getAllCount();
    }
}
