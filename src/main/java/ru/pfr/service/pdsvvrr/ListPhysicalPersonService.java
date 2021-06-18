package ru.pfr.service.pdsvvrr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.pdsvvrr.ListPhysicalPerson;
import ru.pfr.repo.pdsvvrr.ListPhysicalPersonRepozitory;

import java.util.List;

@Service
public class ListPhysicalPersonService {
    @Autowired
    ListPhysicalPersonRepozitory listPhysicalPersonRepozitory;

    public List<ListPhysicalPerson> findAll() {
        return listPhysicalPersonRepozitory.findAll();
    }
}
