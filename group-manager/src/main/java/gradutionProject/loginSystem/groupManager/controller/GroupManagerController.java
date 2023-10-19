package gradutionProject.loginSystem.groupManager.controller;

import gradutionProject.loginSystem.groupManager.dto.GroupDetailDto;
import gradutionProject.loginSystem.groupManager.dto.GroupDto;
import gradutionProject.loginSystem.groupManager.entity.Group;
import gradutionProject.loginSystem.groupManager.repository.RepositoryService;
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
    public List<GroupDto> getGroups(@PathVariable String username){
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
    public void createGroup(@RequestBody Group group){
        repositoryService.createGroup(group);
    }

    @DeleteMapping("/groups/{groupId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable String groupId){
        repositoryService.deleteGroup(groupId);
    }

    @PostMapping("/groups/{groupId}/members/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMember(@PathVariable String groupId, @RequestParam String username){
        repositoryService.addMember(groupId,username);
    }

    @DeleteMapping("/groups/{groupId}/members/{username}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(@PathVariable String groupId, @PathVariable String username){
        repositoryService.removeMember(groupId,username);
    }

}




















