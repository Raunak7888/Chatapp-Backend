package com.chatapp.auth.chatapp.service;

import com.chatapp.auth.chatapp.DTO.UserDataDto;
import com.chatapp.auth.model.User;
import com.chatapp.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserDataServiceImpl implements GetUserDataService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDataDto getUserData(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserDataDto(user.getId(), user.getUsername());
        }
        return null; // Or handle the user not found case accordingly
    }

    @Override
    public List<UserDataDto> searchUsers(String query) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(query);
        return users.stream()
                .map(user -> new UserDataDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }
}
