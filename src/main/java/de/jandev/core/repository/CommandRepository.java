package de.jandev.core.repository;

import de.jandev.core.model.command.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Integer> {

    List<Command> findAllByName(String name);

    List<Command> findAllByUserId(String id);

    boolean existsByPrefixAndNameAndUserId(String prefix, String name, String userId);
}
