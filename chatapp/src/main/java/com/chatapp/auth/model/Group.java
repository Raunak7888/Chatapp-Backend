package com.chatapp.auth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Group_Chats")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "sender_id", nullable = false)
    private Long senderId; // Stores the sender's ID

    @Column(name = "Group_id", nullable = false)
    private Long GroupId;

    public Group(String content, Long groupId, Long senderId) {
        this.content = content;
        GroupId = groupId;
        this.senderId = senderId;
    }
}
