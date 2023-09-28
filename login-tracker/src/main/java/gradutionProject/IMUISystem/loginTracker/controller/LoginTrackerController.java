package gradutionProject.IMUISystem.loginTracker.controller;

import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;
import gradutionProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginTrackerController {

    private final LoginUserRepositoryService loginUserRepositoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is Login Tracker!";
    }
    @GetMapping("/{IMPlatform}/users/{IMUserId}")
    @ResponseStatus(HttpStatus.OK)
    public LoginUser getLoginUser(@Valid @PathVariable String IMPlatform, @Valid @PathVariable String IMUserId){
        return loginUserRepositoryService.getLoginUser(IMPlatform,IMUserId);
    }
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public LoginUser getLoginUser(@Valid @PathVariable String username){
        return loginUserRepositoryService.getLoginUser(username);
    }

}
