package gradutionproject.loginsystem.userdatabase.api.excpetion;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserNotExistException extends Exception{
    private String username;

    @Override
    public String toString() {
        return String.format("Username:%s is not exist!",username);
    }
}
