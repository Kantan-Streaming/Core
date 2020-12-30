package de.jandev.core.repository;

import de.jandev.core.model.command.ActionCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionCommandRepository extends JpaRepository<ActionCommand, Integer> {

    List<ActionCommand> findAllByName(String name);

    List<ActionCommand> findAllByUserUsername(String user);
}
