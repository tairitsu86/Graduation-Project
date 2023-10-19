package graduationProject.loginSystem.groupManager.controller;

import graduationProject.loginSystem.groupManager.dto.CreateGroupDto;
import graduationProject.loginSystem.groupManager.entity.Group;
import graduationProject.loginSystem.groupManager.entity.GroupRole;
import graduationProject.loginSystem.groupManager.dto.GroupDetailDto;
import graduationProject.loginSystem.groupManager.dto.GroupDto;
import graduationProject.loginSystem.groupManager.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupManagerController {
    private final RepositoryService repositoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is group manager!";
    }

    @GetMapping("/users/{username}/groups")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getGroups(@PathVariable String username){
        return repositoryService.getGroups(username);
    }

    @GetMapping("/groups/{groupId}/members")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getMembers(@PathVariable String groupId){
        return repositoryService.getMembers(groupId);
    }

    @GetMapping("/groups/{groupId}/detail")
    @ResponseStatus(HttpStatus.OK)
    public GroupDetailDto getGroupDetail(@PathVariable String groupId){
        return repositoryService.getGroupDetail(groupId);
    }

    @GetMapping("/groups/search")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupDto> searchGroup(@RequestParam String keyword){
        return repositoryService.searchGroup(keyword);
    }

    @PostMapping("/groups/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Group createGroup(@RequestBody CreateGroupDto createGroupDto, @RequestParam String owner){
        Group group = repositoryService.createGroup(createGroupDto);
        repositoryService.addMember(group.getGroupId(), owner, GroupRole.OWNER);
        return group;
    }

    @DeleteMapping("/groups/{groupId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable String groupId){
        repositoryService.deleteGroup(groupId);
    }

    @PostMapping("/groups/{groupId}/members/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMember(@PathVariable String groupId, @RequestParam String username){
        repositoryService.addMember(groupId,username, GroupRole.MEMBER);
    }

    @DeleteMapping("/groups/{groupId}/members/{username}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(@PathVariable String groupId, @PathVariable String username){
        repositoryService.removeMember(groupId,username);
    }

}




















