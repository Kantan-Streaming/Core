package de.jandev.core.repository;

import de.jandev.core.model.command.SimpleTextCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleTextCommandRepository extends JpaRepository<SimpleTextCommand, Integer> {

    List<SimpleTextCommand> findAllByName(String name);

    List<SimpleTextCommand> findAllByUserUsername(String user);
}
