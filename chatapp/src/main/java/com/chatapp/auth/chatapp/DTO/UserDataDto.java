package com.chatapp.auth.chatapp.DTO;

import lombok.Data;

@Data
public class UserDataDto {
    private Long userId;
    private String username;

    // Constructors
    public UserDataDto() {}

    public UserDataDto(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}
