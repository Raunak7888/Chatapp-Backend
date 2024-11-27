package com.chatapp.auth.chatapp.service;

import com.chatapp.auth.chatapp.DTO.GroupDTO;
import com.chatapp.auth.model.Group;
import com.chatapp.auth.repository.GroupChatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupChatRepository groupChatRepository;

    public GroupService(GroupChatRepository groupChatRepository) {
        this.groupChatRepository = groupChatRepository;
    }

    // Save a new message to the database
    public GroupDTO saveMessage(GroupDTO groupDTO) {
        Group group = new Group(
                groupDTO.getContent(),
                groupDTO.getGroupId(),
                groupDTO.getSenderId()
        );
        group.setTimestamp(LocalDateTime.now()); // Set timestamp

        Group savedGroup = groupChatRepository.save(group);
        return mapToDTO(savedGroup);
    }

    // Fetch all messages for a specific group
    public List<GroupDTO> getMessagesByGroupId(Long groupId) {
        return groupChatRepository.findAll()
                .stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Map the Group entity to GroupDTO
    private GroupDTO mapToDTO(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getContent(),
                group.getTimestamp(),
                group.getSenderId(),
                group.getGroupId()
        );
    }
}
