package com.tarus.server.yearlyrejectionstat;

import org.springframework.stereotype.Service;

@Service
public class YearlyRejectionStatService {
    private YearlyRejectionStatRepository repository;
    public YearlyRejectionStat saveRejectionStat(YearlyRejectionStat stat){

        return repository.save(stat);
    }
}
