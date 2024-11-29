package com.chatapp.auth.chatapp.controller;

import com.chatapp.auth.chatapp.DTO.MessageDTO;
import com.chatapp.auth.chatapp.service.GetUserDataService;
import com.chatapp.auth.chatapp.service.MessageService;
import com.chatapp.auth.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageService messageService, GetUserDataService getUserDataService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Handles sending of a private message.
     * @param messageDTO DTO containing message content and sender information.
     */
    @MessageMapping("/send/message")
    public void sendMessage(MessageDTO messageDTO) {
        // Log incoming message content and receiver ID for tracking
        logger.info("Received message: '{}' from sender ID: {} to receiver ID: {}", messageDTO.getContent(), messageDTO.getSenderId(), messageDTO.getReceiverId());

        try {
            // Save the message to the database
            Message savedMessage = messageService.savePrivateMessage(messageDTO);

            // Log the saved message content
            logger.info("Message successfully saved: {}", savedMessage.getContent());

            String destination = "/topic/user/" + messageDTO.getReceiverId() + "/queue/private";
            System.out.println(destination);
            messagingTemplate.convertAndSend(destination, convertToDTO(savedMessage));
        } catch (Exception e) {
            logger.error("Error saving message: {}", e.getMessage());
            throw new RuntimeException("Message could not be saved due to an error.");
        }
    }


    /**
     * Converts a Message entity to a MessageDTO.
     * @param message The message entity to convert.
     * @return MessageDTO representing the message.
     */
    private MessageDTO convertToDTO(Message message) {
        // Map entity fields to DTO fields
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        dto.setSenderId(message.getSenderId());
        dto.setReceiverId(message.getReceiverId());

        // Log the conversion for debugging
        logger.debug("Converted message ID {} to DTO", message.getId());

        return dto;
    }


}
