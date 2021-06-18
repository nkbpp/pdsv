package ru.pfr.service.pdsvvrr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pdsvvrr.Adminparam;
import ru.pfr.repo.pdsvvrr.AdminparamRepository;

@Service
public class AdminparamService {
    @Autowired
    AdminparamRepository adminparamRepository;

    @Transactional
    public void save(Adminparam adminparam) {
        adminparamRepository.save(adminparam);
    }

    public Adminparam findByAdminparam() {
        return adminparamRepository.findById(1l).get();
    }
}
