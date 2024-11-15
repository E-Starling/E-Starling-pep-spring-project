package com.example.repository;

import com.example.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    // See if message Id exists
    boolean existsByMessageId(Integer messageId);

    // Find all messages by specific account
    List<Message> findByPostedBy(Integer accountId);
}
