package ru.pfr.service.asv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.asv.LegalEntity;
import ru.pfr.model.asv.LegalEntityGroup;
import ru.pfr.repo.asv.LegalEntityRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LegalEntityService {
    @Autowired
    private LegalEntityRepository legalEntityRepository;

    public List<LegalEntity> findAll(Date d1, Date d2) {

        //return legalEntityRepository.findByPAYMENTSTATEMENTDATEBetween(d1,d2);
        return legalEntityRepository.findAll(d1,d2);
    }

    public List<LegalEntity> findAll() {

        //return legalEntityRepository.findByPAYMENTSTATEMENTDATEBetween(d1,d2);
        return legalEntityRepository.findAll();
    }
}

