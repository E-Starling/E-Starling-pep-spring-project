package com.example.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.entity.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController

public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Register Account
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.registerAccount(account);
            return new ResponseEntity<>(savedAccount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // If the username for account creation already exists 409 error.
            if ("Username already exists".equals(e.getMessage())) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            // Else for 400 error
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Login Account
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.loginAccount(account);
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
            // Invalid Login 401 error
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // Add Message
    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.addMessage(message);
            return new ResponseEntity<>(savedMessage, HttpStatus.OK);
            // 400 error
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve All Messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Retrive Message By Id
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        try {
            // retrieve message by Id
            Message message = messageService.getMessageById(messageId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Return 200 regardless
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    // Delete message by ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId) {
        String deleted = messageService.deleteMessageById(messageId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    // Update message by ID
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessageById(@PathVariable Integer messageId,
            @RequestBody Map<String, String> request) {
        String newMessageText = request.get("messageText");
        try {
            String updatedCount = messageService.updateMessageById(messageId, newMessageText);
            return new ResponseEntity<>(updatedCount, HttpStatus.OK);
        }
        // Return 400 for exceptions
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Retrive all messages by account
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
