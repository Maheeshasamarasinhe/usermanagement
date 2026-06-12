package com.example.user_management_system.service;

import com.example.user_management_system.dto.GroupRequestDto;
import com.example.user_management_system.dto.GroupResponseDto;
import com.example.user_management_system.entity.Group;
import com.example.user_management_system.entity.User;
import com.example.user_management_system.repository.GroupRepository;
import com.example.user_management_system.repository.UserRepository;
import com.example.user_management_system.specification.GroupSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GroupResponseDto createGroup(GroupRequestDto dto) {
        Group group = modelMapper.map(dto, Group.class);
        Group savedGroup = groupRepository.save(group);
        return modelMapper.map(savedGroup, GroupResponseDto.class);
    }

    public List<GroupResponseDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> modelMapper.map(group, GroupResponseDto.class))
                .collect(Collectors.toList());
    }

    public GroupResponseDto getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return modelMapper.map(group, GroupResponseDto.class);
    }

    public GroupResponseDto updateGroup(Long id, GroupRequestDto dto) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        
        existingGroup.setName(dto.getName());
        existingGroup.setDescription(dto.getDescription());
        
        Group updatedGroup = groupRepository.save(existingGroup);
        return modelMapper.map(updatedGroup, GroupResponseDto.class);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public GroupResponseDto addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        group.getUsers().add(user);
        Group updatedGroup = groupRepository.save(group);
        return modelMapper.map(updatedGroup, GroupResponseDto.class);
    }

    public GroupResponseDto removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        group.getUsers().remove(user);
        Group updatedGroup = groupRepository.save(group);
        return modelMapper.map(updatedGroup, GroupResponseDto.class);
    }

    public List<GroupResponseDto> searchGroups(Map<String, String> searchCriteria) {
        GroupSpecification specification = new GroupSpecification(searchCriteria);
        return groupRepository.findAll(specification).stream()
                .map(group -> modelMapper.map(group, GroupResponseDto.class))
                .collect(Collectors.toList());
    }
}
