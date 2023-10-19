package gradutionProject.loginSystem.groupManager.repository;

import gradutionProject.loginSystem.groupManager.controller.exception.GroupAlreadyExistException;
import gradutionProject.loginSystem.groupManager.controller.exception.GroupNotExistException;
import gradutionProject.loginSystem.groupManager.dto.GroupDetailDto;
import gradutionProject.loginSystem.groupManager.dto.GroupDto;
import gradutionProject.loginSystem.groupManager.entity.Group;
import gradutionProject.loginSystem.groupManager.entity.GroupRole;
import gradutionProject.loginSystem.groupManager.entity.Member;
import gradutionProject.loginSystem.groupManager.entity.MemberId;
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
