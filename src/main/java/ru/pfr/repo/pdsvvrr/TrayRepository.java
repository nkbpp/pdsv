package ru.pfr.repo.pdsvvrr;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pdsvvrr.Tray;

import java.util.List;

public interface TrayRepository extends JpaRepository<Tray, Long> {
    public List<Tray> findAll();
}
