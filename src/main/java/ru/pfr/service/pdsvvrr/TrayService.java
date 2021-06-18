package ru.pfr.service.pdsvvrr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.pdsvvrr.Tray;
import ru.pfr.repo.pdsvvrr.TrayRepository;

import java.util.List;

@Service
public class TrayService {

    @Autowired
    private TrayRepository trayRepository;

    public List<Tray> findAll() {
        return trayRepository.findAll();
    }
}
