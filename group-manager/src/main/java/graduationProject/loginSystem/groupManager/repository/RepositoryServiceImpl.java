package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.controller.exception.GroupAlreadyExistException;
import graduationProject.loginSystem.groupManager.controller.exception.GroupNotExistException;
import graduationProject.loginSystem.groupManager.dto.GroupDetailDto;
import graduationProject.loginSystem.groupManager.dto.GroupDto;
import graduationProject.loginSystem.groupManager.entity.Group;
import graduationProject.loginSystem.groupManager.entity.GroupRole;
import graduationProject.loginSystem.groupManager.entity.Member;
import graduationProject.loginSystem.groupManager.entity.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<GroupDto> getGroups(String username) {
        return null;
    }

    @Override
    public List<String> getMembers(String groupId) {
        return memberRepository.getUsernameByGroupId(groupId);
    }

    @Override
    public GroupDetailDto getGroupDetail(String groupId) {
        return null;
    }

    @Override
    public List<GroupDto> searchGroup(String keyword) {
        return null;
    }

    @Override
    public void createGroup(Group group) {
        if(groupRepository.existsById(group.getGroupId())) throw new GroupAlreadyExistException(group.getGroupId());
        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(String groupId) {
        memberRepository.deleteByGroupId(groupId);
        groupRepository.deleteById(groupId);
    }

    @Override
    public void addMember(String groupId, String username, GroupRole groupRole) {
        if(!groupRepository.existsById(groupId)) throw new GroupNotExistException(groupId);
        memberRepository.save(
                Member.builder()
                        .groupId(groupId)
                        .username(username)
                        .groupRole(groupRole)
                        .build()
        );
    }

    @Override
    public void removeMember(String groupId, String username) {
        if(!groupRepository.existsById(groupId)) throw new GroupNotExistException(groupId);
        memberRepository.deleteById(
                MemberId.builder()
                        .groupId(groupId)
                        .username(username)
                        .build()
        );
    }
}
