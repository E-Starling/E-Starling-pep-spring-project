package com.example.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.Optional;
import com.example.entity.Message;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // Create Message
    public Message addMessage(Message message) {
        // check blank
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new IllegalArgumentException("This message is empty");
        }
        // check over 255 char
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("This message is over 255 characters");
        }
        // check posted_by is real
        if (message.getPostedBy() == null || !accountRepository.existsByAccountId(message.getPostedBy())) {
            throw new IllegalArgumentException("This user doesn't exist");
        }
        return messageRepository.save(message);
    }

    // Retrieve all Messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Retrive Message By Id
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message with ID " + messageId + " not found"));
    }

    // Delete Message By Id
    public String deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return "1";
        }
        return "";
    }

    // Update Message By Id
    public String updateMessageById(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty()) {
            throw new IllegalArgumentException("This message is empty");
        }
        if (newMessageText.length() > 255) {
            throw new IllegalArgumentException("This message is over 255 characters");
        }
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return "1";
        } else {
            throw new IllegalArgumentException("This message doesn't exist");
        }
    }

    // Retrieve All Messages By Account
    public List<Message> getMessagesByAccountId(Integer accountId) {
        List<Message> messages = messageRepository.findByPostedBy(accountId);

        // if no messages are found return an empty list
        return messages != null ? messages : new ArrayList<>();
    }
}
