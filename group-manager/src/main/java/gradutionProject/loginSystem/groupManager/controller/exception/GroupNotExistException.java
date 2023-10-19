package gradutionProject.loginSystem.groupManager.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupNotExistException extends RuntimeException{
    private String groupId;

    @Override
    public String toString() {
        return String.format("Group with id [%s] is not exist!",groupId);
    }
}
