package graduationProject.loginSystem.groupManager.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupAlreadyExistException extends RuntimeException{
    private String groupId;

    @Override
    public String toString() {
        return String.format("Group with id [%s] is already exist!",groupId);
    }
}
