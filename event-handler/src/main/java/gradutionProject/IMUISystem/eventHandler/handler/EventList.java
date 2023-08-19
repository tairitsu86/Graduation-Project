package gradutionProject.IMUISystem.eventHandler.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EventList {
    UNKNOWN,
    EXIT,
    MENU,
    //Unknown user Step
    UNKNOWN_USER_START,

    //Sign up Step
    SIGN_UP_START("Sign up"),
    SIGN_UP_WAIT_USERNAME,
    SIGN_UP_WAIT_PASSWORD,
    SIGN_UP_WAIT_USER_DISPLAY_NAME,


    //Login Step
    LOGIN_START("Login"),
    LOGIN_WAIT_USERNAME,
    LOGIN_WAIT_PASSWORD,

    //Logout Step
    LOGOUT_START("Logout"),

    //Profile Step
    PROFILE_START("Get profile"),

    //Alter Step
    ALTER_USER_DISPLAY_NAME_START("Alter display name"),
    ALTER_PASSWORD_START("Alter password"),
    ALTER_WAIT_USER_DISPLAY_NAME,
    ALTER_WAIT_PASSWORD,

    //Broadcast Step
    BROADCAST_START("Broadcast"),
    BROADCAST_WAIT_MESSAGE;
//    EventList(String reportMessage){
//        this.reportMessage = reportMessage;
//    }
    private String description;
//    private String reportMessage;
}
