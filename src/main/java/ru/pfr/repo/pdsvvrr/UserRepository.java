package ru.pfr.repo.pdsvvrr;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pdsvvrr.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByLogin(String login);
}
