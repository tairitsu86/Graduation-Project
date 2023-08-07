package gradution_project.login_system.user_database.api;

import gradution_project.login_system.user_database.api.dto.*;
import gradution_project.login_system.user_database.entity.User;
import gradution_project.login_system.user_database.repository.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class UserDatabaseController {
    @Autowired
    private UserRepositoryService userRepositoryService;
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User.UserDto userLogin(@RequestBody UserLoginDto userLoginDto){
        return userRepositoryService.userLogin(
                userLoginDto.getUsername()
                ,userLoginDto.getPassword()
                ,userLoginDto.isKeepLogin()
        );
    }
    @PostMapping("/new")
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
