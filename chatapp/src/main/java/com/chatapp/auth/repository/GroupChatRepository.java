package com.chatapp.auth.repository;

import com.chatapp.auth.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupChatRepository extends JpaRepository<Group, Long> {
    List<Group> findByGroupId(Long groupId);
}
