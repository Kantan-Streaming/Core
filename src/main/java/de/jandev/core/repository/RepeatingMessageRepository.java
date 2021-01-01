package de.jandev.core.repository;

import de.jandev.core.model.timer.RepeatingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepeatingMessageRepository extends JpaRepository<RepeatingMessage, Integer> {

    List<RepeatingMessage> findAllByActiveIsTrueAndUserActiveIsTrue();
}
