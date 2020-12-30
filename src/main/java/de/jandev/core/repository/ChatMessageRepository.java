package de.jandev.core.repository;

import de.jandev.core.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    List<ChatMessage> findAllByUserId(String user);
}
