package com.chatapp.auth.chatapp.controller;

import com.chatapp.auth.chatapp.DTO.GroupDTO;
import com.chatapp.auth.chatapp.service.GroupService;
import com.chatapp.auth.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GroupService groupService;
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    public GroupController(SimpMessagingTemplate messagingTemplate, GroupService groupService) {
        this.messagingTemplate = messagingTemplate;
        this.groupService = groupService;
    }

    // Endpoint to save a message
    /**
     * Handle incoming WebSocket messages, save them to the database,
     * and broadcast to all subscribed clients.
     *
     * @param groupDTO the received group message DTO
     */
    @MessageMapping("/group/message") // Clients send to /app/group/message
    public void saveMessage(GroupDTO groupDTO) {
        logger.info("Received message: '{}' from sender ID: {} to group ID: {}", groupDTO.getContent(), groupDTO.getSenderId(), groupDTO.getGroupId());

        try {
            // Save the message to the database
            GroupDTO savedMessage = groupService.saveMessage(groupDTO);

            // Log the saved message
            logger.info("Message successfully saved: {}", savedMessage.getContent());

            // Broadcast the saved message to the group topic
            messagingTemplate.convertAndSend("/topic/group", savedMessage);

        } catch (Exception e) {
            logger.error("Error saving message: {}", e.getMessage());
            throw new RuntimeException("Message could not be saved due to an error.");
        }
    }

    // Endpoint to fetch all messages for a group
    @GetMapping("/auth/group/{groupId}/messages")
    public ResponseEntity<List<GroupDTO>> getMessages(@PathVariable Long groupId) {
        List<GroupDTO> messages = groupService.getMessagesByGroupId(groupId);
        return ResponseEntity.ok(messages);
    }
}
