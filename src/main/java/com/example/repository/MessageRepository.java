package com.example.repository;

import com.example.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface MessageRepository  extends JpaRepository<Message, Integer>{
    //See if message Id exists
    boolean existsByMessageId(Integer messageId);
}
