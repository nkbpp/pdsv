package ru.pfr.service.asv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.asv.PhisikalEntity;
import ru.pfr.repo.asv.PhisikalEntityRepository;

import java.util.Date;
import java.util.List;

@Service
public class PhisikalEntityService {
    @Autowired
    private PhisikalEntityRepository phisikalEntityRepository;

    public List<PhisikalEntity> findAll(Date d1, Date d2) {
        return phisikalEntityRepository.findAll(d1,d2);
    }

    public List<PhisikalEntity> findAll() {
        return phisikalEntityRepository.findAll();
    }
}
