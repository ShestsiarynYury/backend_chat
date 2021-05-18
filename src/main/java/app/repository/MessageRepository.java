package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
}
