package ru.pfr.repo.pdsvvrr;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pdsvvrr.Adminparam;

import java.util.Optional;

public interface AdminparamRepository extends JpaRepository<Adminparam, Long> {
    public Optional<Adminparam> findById(Long l);
}
