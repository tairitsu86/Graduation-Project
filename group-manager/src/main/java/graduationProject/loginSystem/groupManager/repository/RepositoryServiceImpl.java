package graduationProject.loginSystem.groupManager.repository;

import graduationProject.loginSystem.groupManager.controller.exception.GroupNotExistException;
import graduationProject.loginSystem.groupManager.dto.CreateGroupDto;
import graduationProject.loginSystem.groupManager.dto.GroupDetailDto;
import graduationProject.loginSystem.groupManager.dto.GroupDto;
import graduationProject.loginSystem.groupManager.entity.Group;
import graduationProject.loginSystem.groupManager.entity.GroupRole;
import graduationProject.loginSystem.groupManager.entity.Member;
import graduationProject.loginSystem.groupManager.entity.MemberId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<String> getGroups(String username) {
        List<String> groups = new ArrayList<>();
        for (Member member: memberRepository.getMemberByUsername(username))
            groups.add(member.getGroupId());
        return groups;
    }

    @Override
    public List<String> getMembers(String groupId) {
        List<String> users = new ArrayList<>();
        for (Member member: memberRepository.getMemberByGroupId(groupId))
            users.add(member.getUsername());
        return users;
    }

    @Override
    public GroupDetailDto getGroupDetail(String groupId) {
        if(!groupRepository.existsById(groupId)) throw new GroupNotExistException(groupId);
        Group group = groupRepository.getReferenceById(groupId);
        return GroupDetailDto.builder()
                .groupId(groupId)
                .groupName(group.getGroupName())
                .description(group.getDescription())
                .build();
    }

    @Override
    public List<GroupDto> searchGroup(String keyword) {
        List<Group> groups = groupRepository.findAll();
        List<GroupDto> result = new ArrayList<>();
        for (Group group:groups)
            if(group.getGroupName()!=null&&group.isVisible()&&group.getGroupName().matches("(?i).*"+keyword+".*"))
                result.add(
                        GroupDto.builder()
                                .groupId(group.getGroupId())
                                .groupName(group.getGroupName())
                                .build()
                );
        return result;
    }

    @Override
    public Group createGroup(CreateGroupDto createGroupDto) {
        Group group = Group.builder()
                .groupId(newGroupId())
                .groupName(createGroupDto.getGroupName())
                .description(createGroupDto.getDescription())
                .visible(createGroupDto.isVisible())
                .joinActively(createGroupDto.isJoinActively())
                .build();
        return groupRepository.save(group);
    }

    @Transactional
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
    public String newGroupId(){
        char newId[] = new char[6];
        for(int i=0,random;i<6;i++){
            random = (int)(Math.random()*62);
            if(random<10)
                newId[i] = (char)(random+48);
            else if (random<36)
                newId[i] = (char)(random-10+65);
            else
                newId[i] = (char)(random-36+97);
        }
        String newGroupId = String.copyValueOf(newId);
        if(groupRepository.existsById(newGroupId)) return newGroupId();
        return newGroupId;
    }
}
