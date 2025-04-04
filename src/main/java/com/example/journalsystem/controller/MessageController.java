package com.example.journalsystem.controller;

import com.example.journalsystem.bo.Service.MessageService;
import com.example.journalsystem.bo.model.Message;
import com.example.journalsystem.config.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    private final MessageService messageService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public MessageController(MessageService messageService, JwtTokenUtil jwtTokenUtil) {
        this.messageService = messageService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long recipientId,
            @RequestBody String messageContent,
            HttpServletRequest request) {
        try {
            String token = messageService.extractTokenFromRequest(request);
            if(token!=null) messageService.sendMessage(senderId, recipientId, messageContent);
            return ResponseEntity.ok("Message sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Message>> getMessagesForUser(@PathVariable Long userId, HttpServletRequest request) {
        try {
            String token = messageService.extractTokenFromRequest(request);
            String username = jwtTokenUtil.extractUsernameFromToken(token);
            if (username==null || token==null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            List<Message> messages = messageService.getMessagesForUser(userId);
            if (messages == null || messages.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
