package gradutionProject.loginSystem.userDatabase.api;

import gradutionProject.loginSystem.userDatabase.dto.*;
import gradutionProject.loginSystem.userDatabase.entity.User;
import gradutionProject.loginSystem.userDatabase.rabbitMQ.MQEventPublisher;
import gradutionProject.loginSystem.userDatabase.repository.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class UserDatabaseController {
    private final UserRepositoryService userRepositoryService;
    private final MQEventPublisher mqEventPublisher;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is user database!";
    }
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User.UserDto getUserData(@PathVariable String username){
        return userRepositoryService.getUserData(username);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User.UserDto userLogin(@RequestBody UserLoginDto userLoginDto){
        User.UserDto userDto = userRepositoryService.userLogin(
                userLoginDto.getUsername()
                ,userLoginDto.getPassword()
        );
        mqEventPublisher.publishLoginEvent(LoginEventDto.builder()
                .username(userDto.getUsername())
                .fromPlatform(userLoginDto.getFromPlatform())
                .platformType(userLoginDto.getPlatformType())
                .platformUserId(userLoginDto.getPlatformUserId())
                .build()
        );
        return userDto;
    }
    @DeleteMapping("/{platformType}/{platform}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userLogout(@PathVariable String platformType,@PathVariable String platform,@PathVariable String userId){
        mqEventPublisher.publishLogoutEvent(
                LogoutEventDto.builder()
                        .platformType(platformType)
                        .fromPlatform(platform)
                        .platformUserId(userId)
                        .build()
        );
    }
    @PostMapping("/users/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody AddUserDto addUserDto){
        userRepositoryService.addUser(
                addUserDto.getUsername()
                ,addUserDto.getPassword()
                ,addUserDto.getUserDisplayName()
        );
    }
    @PatchMapping("/users/{username}/alter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterUserData(@PathVariable String username, @RequestBody AlterUserDataDto alterUserDataDto){
        if(alterUserDataDto.getNewPassword()!=null)
            userRepositoryService.alterPassword(username, alterUserDataDto.getNewPassword());
        if(alterUserDataDto.getNewUserDisplayName()!=null)
            userRepositoryService.alterUserDisplayName(username, alterUserDataDto.getNewUserDisplayName());
    }

    @PatchMapping("/users/{username}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String username){
        userRepositoryService.deleteUser(username);
    }

}
