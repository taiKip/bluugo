package com.tarus.server.yearlyrejectionstat;

import org.springframework.stereotype.Service;

@Service
public class YearlyRejectionStatService {
    private YearlyRejectionStatRepository repository;
    public YearlyRejectionStat saveRejectionStat(YearlyRejectionStat stat){
        YearlyRejectionStat statDb = repository.findByRejectionPercentage(stat.getRejectionPercentage());
        if(statDb!=null){
            return statDb;
        }
        return repository.save(stat);
    }
}
