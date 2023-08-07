package gradutionproject.loginsystem.userdatabase.api;

import gradutionproject.loginsystem.userdatabase.api.dto.*;
import gradutionproject.loginsystem.userdatabase.entity.User;
import gradutionproject.loginsystem.userdatabase.repository.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/login")
public class UserDatabaseController {
    @Autowired
    private UserRepositoryService userRepositoryService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody AddUserDto addUserDto){
        userRepositoryService.addUser(
                addUserDto.getUsername()
                ,addUserDto.getPassword()
                ,addUserDto.getUserDisplayName()
        );
    }
    @PatchMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterUserDisplayName(@RequestBody AlterUserDisplayNameDto alterUserDisplayNameDto){
        userRepositoryService.alterUserDisplayName(
                alterUserDisplayNameDto.getUsername()
                ,alterUserDisplayNameDto.getNewUserDisplayName()
        );
    }
    @PatchMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterPassword(@RequestBody AlterPasswordDto alterPasswordDto){
        userRepositoryService.alterPassword(
                alterPasswordDto.getUsername(),
                alterPasswordDto.getNewPassword()
        );
    }
    @PatchMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestBody DeleteUserDto deleteUserDto){
        userRepositoryService.deleteUser(
                deleteUserDto.getUsername()
        );
    }
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public User.UserDto userLogin(@RequestBody UserLoginDto userLoginDto){
        return userRepositoryService.userLogin(
                userLoginDto.getUsername(),
                userLoginDto.getPassword(),
                userLoginDto.isKeepLogin()
        );
    }
}
