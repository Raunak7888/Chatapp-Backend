package com.chatapp.auth.chatapp.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Content cannot be null")
    private String content;

    private LocalDateTime timestamp;

    @NotNull(message = "Sender ID cannot be null")
    private Long senderId;

    @NotNull(message = "Group ID cannot be null")
    private Long groupId;
}
