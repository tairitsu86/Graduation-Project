package gradutionProject.loginSystem.userDatabase.api;

import gradutionProject.loginSystem.userDatabase.dto.AddUserDto;
import gradutionProject.loginSystem.userDatabase.dto.AlterUserDataDto;
import gradutionProject.loginSystem.userDatabase.dto.UserLoginDto;
import gradutionProject.loginSystem.userDatabase.entity.User;
import gradutionProject.loginSystem.userDatabase.repository.UserRepositoryService;
import gradutionProject.loginSystem.userDatabase.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserDatabaseController {
    private final UserRepositoryService userRepositoryService;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is user database!";
    }
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User.UserDto getUserData(@Valid @PathVariable String username){
        return userRepositoryService.getUserData(username);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User.UserDto userLogin(@Valid @RequestBody UserLoginDto userLoginDto){
        return userRepositoryService.userLogin(
                userLoginDto.getUsername()
                ,userLoginDto.getPassword()
                ,userLoginDto.isKeepLogin()
        );
    }
    @PostMapping("/users/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@Valid @RequestBody AddUserDto addUserDto){
        userRepositoryService.addUser(
                addUserDto.getUsername()
                ,addUserDto.getPassword()
                ,addUserDto.getUserDisplayName()
        );
    }
    @PatchMapping("/users/{username}/alter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterUserData(@Valid @PathVariable String username, @RequestBody AlterUserDataDto alterUserDataDto){
        if(alterUserDataDto.getNewPassword()!=null)
            userRepositoryService.alterPassword(username, alterUserDataDto.getNewPassword());
        if(alterUserDataDto.getNewUserDisplayName()!=null)
            userRepositoryService.alterUserDisplayName(username, alterUserDataDto.getNewUserDisplayName());
    }

    @PatchMapping("/users/{username}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Valid @PathVariable String username){
        userRepositoryService.deleteUser(username);
    }

}
