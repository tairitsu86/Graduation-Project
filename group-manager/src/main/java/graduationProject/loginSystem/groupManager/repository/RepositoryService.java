package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.dto.CreateGroupDto;
import graduationProject.loginSystem.groupManager.entity.Group;
import graduationProject.loginSystem.groupManager.entity.GroupRole;
import graduationProject.loginSystem.groupManager.dto.GroupDetailDto;
import graduationProject.loginSystem.groupManager.dto.GroupDto;

import java.util.List;

public interface RepositoryService {
    List<String> getGroups(String username);
    List<String> getMembers(String groupId);
    GroupDetailDto getGroupDetail(String groupId);
    List<GroupDto> searchGroup(String keyword);
    Group createGroup(CreateGroupDto createGroupDto);
    void deleteGroup(String groupId);
    void addMember(String groupId, String username, GroupRole groupRole);
    void removeMember(String groupId, String username);
}
