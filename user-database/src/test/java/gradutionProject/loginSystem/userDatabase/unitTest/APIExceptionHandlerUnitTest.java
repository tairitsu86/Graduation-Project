package gradutionProject.loginSystem.userDatabase.unitTest;

import gradutionProject.loginSystem.userDatabase.api.APIExceptionHandler;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UserLoginWithIncorrectAccountException;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UserNotExistException;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UsernameAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class APIExceptionHandlerUnitTest {
    //SUT
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
        //check parameters

        //check method call times
        //check return/exception values
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
        //check parameters
        //check method call times
        //check return/exception values
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
        //check parameters
        //check method call times
        //check return/exception values
        assertEquals(result,errorMessage);
    }

}
