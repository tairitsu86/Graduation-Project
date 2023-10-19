package gradutionProject.loginSystem.groupManager.repository;

import gradutionProject.loginSystem.groupManager.dto.GroupDetailDto;
import gradutionProject.loginSystem.groupManager.dto.GroupDto;
import gradutionProject.loginSystem.groupManager.entity.Group;
import gradutionProject.loginSystem.groupManager.entity.GroupRole;

import java.util.List;

public interface RepositoryService {
    List<GroupDto> getGroups(String username);
    List<String> getMembers(String groupId);
    GroupDetailDto getGroupDetail(String groupId);
    List<GroupDto> searchGroup(String keyword);
    void createGroup(Group group);
    void deleteGroup(String groupId);
    void addMember(String groupId, String username, GroupRole groupRole);
    void removeMember(String groupId, String username);
}
