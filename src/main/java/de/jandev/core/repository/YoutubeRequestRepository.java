package de.jandev.core.repository;

import de.jandev.core.model.chat.YoutubeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeRequestRepository extends JpaRepository<YoutubeRequest, Integer> {

    List<YoutubeRequest> findAllByUserId(String id);
}
