package gradutionproject.loginsystem.userdatabase.api.excpetion;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsernameAlreadyExistException extends RuntimeException{
    private String username;

    @Override
    public String toString() {
        return String.format("Username '%s' is already exist!",username);
    }
}
