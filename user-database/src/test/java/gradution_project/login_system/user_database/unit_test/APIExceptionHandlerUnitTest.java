package gradution_project.login_system.user_database.unit_test;

import gradution_project.login_system.user_database.api.APIExceptionHandler;
import gradution_project.login_system.user_database.api.excpetion.UserLoginWithIncorrectAccountException;
import gradution_project.login_system.user_database.api.excpetion.UserNotExistException;
import gradution_project.login_system.user_database.api.excpetion.UsernameAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class APIExceptionHandlerUnitTest {
    private APIExceptionHandler apiExceptionHandler;
    @Mock
    private UserNotExistException userNotExistException;
    @Mock
    private UsernameAlreadyExistException usernameAlreadyExistException;
    @Mock
    private UserLoginWithIncorrectAccountException userLoginWithIncorrectAccountException;

    @BeforeEach
    public void setup(){
        apiExceptionHandler = new APIExceptionHandler();
    }

    @Test
    public void userNotExistExceptionHandler_happy_path_test(){
        //Given
        String errorMessage = randomAlphabetic( 8 );
        when(userNotExistException.toString()).thenReturn(errorMessage);
        //When
        String result = apiExceptionHandler.userNotExistExceptionHandler(userNotExistException);
        //Then
        assertEquals(result,errorMessage);
    }

    @Test
    public void usernameAlreadyExistExceptionHandler_happy_path_test(){
        //Given
        String errorMessage = randomAlphabetic( 8 );
        when(usernameAlreadyExistException.toString()).thenReturn(errorMessage);
        //When
        String result = apiExceptionHandler.usernameAlreadyExistExceptionHandler(usernameAlreadyExistException);
        //Then
        assertEquals(result,errorMessage);
    }

    @Test
    public void userLoginErrorExceptionHandler_happy_path_test(){
        //Given
        String errorMessage = randomAlphabetic( 8 );
        when(userLoginWithIncorrectAccountException.toString()).thenReturn(errorMessage);
        //When
        String result = apiExceptionHandler.userLoginWithIncorrectAccountExceptionHandler(userLoginWithIncorrectAccountException);
        //Then
        assertEquals(result,errorMessage);
    }

}
