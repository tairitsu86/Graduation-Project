package gradutionProject.IMUISystem.loginTracker.api;

import gradutionProject.IMUISystem.loginTracker.dto.GetIMDataDto;
import gradutionProject.IMUISystem.loginTracker.dto.GetUsernameDto;
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
        return "Hello! This is Login Tracker";
    }
    @GetMapping("/{IM}/users/{IMUserId}")
    @ResponseStatus(HttpStatus.OK)
    public GetUsernameDto getUsername(@Valid @PathVariable String IM, @Valid @PathVariable String IMUserId){
        LoginUser loginUser = loginUserRepositoryService.getLoginUser(IM,IMUserId);
        return GetUsernameDto.builder()
                .Username(loginUser.getUsername())
                .build();
    }
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public GetIMDataDto getIMData(@Valid @PathVariable String username){
        LoginUser loginUser = loginUserRepositoryService.getLoginUser(username);
        return GetIMDataDto.builder()
                .IM(loginUser.getIM())
                .IMUserId(loginUser.getIMUserId())
                .build();
    }

}
