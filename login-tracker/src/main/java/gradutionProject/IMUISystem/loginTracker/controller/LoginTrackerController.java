package gradutionProject.IMUISystem.loginTracker.controller;

import gradutionProject.IMUISystem.loginTracker.dto.GetIMDataDto;
import gradutionProject.IMUISystem.loginTracker.dto.GetUsernameDto;
import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;
import gradutionProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public GetUsernameDto getUsername(@Valid @PathVariable String IMPlatform, @Valid @PathVariable String IMUserId){
        LoginUser loginUser = loginUserRepositoryService.getLoginUser(IMPlatform,IMUserId);
        return GetUsernameDto.builder()
                .Username(loginUser.getUsername())
                .build();
    }
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public GetIMDataDto getIMData(@Valid @PathVariable String username){
        List<LoginUser> loginUsers = loginUserRepositoryService.getLoginUser(username);
        List<IMUserData> imUserDataList = new ArrayList<>();
        for (LoginUser loginUser:loginUsers)
            imUserDataList.add(loginUser.getImUserData());
        return GetIMDataDto.builder()
                .imUserDataList(imUserDataList)
                .build();
    }

}
