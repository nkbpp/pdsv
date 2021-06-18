package ru.pfr.repo.pdsvvrr;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pdsvvrr.ListPhysicalPerson;


public interface ListPhysicalPersonRepozitory extends JpaRepository<ListPhysicalPerson, Long> {
}
