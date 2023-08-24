package gradutionProject.loginSystem.userDatabase.unitTest;

import gradutionProject.loginSystem.userDatabase.api.UserDatabaseController;
import gradutionProject.loginSystem.userDatabase.dto.AddUserDto;
import gradutionProject.loginSystem.userDatabase.dto.AlterUserDataDto;
import gradutionProject.loginSystem.userDatabase.dto.UserLoginDto;
import gradutionProject.loginSystem.userDatabase.entity.User;
import gradutionProject.loginSystem.userDatabase.repository.UserRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDatabaseControllerUnitTest {
    //SUT
    private UserDatabaseController userDatabaseController;
    @Mock
    private UserRepositoryService userRepositoryService;
    @Mock
    private User.UserDto userDto;
    @Spy
    private UserLoginDto userLoginDto;
    @Spy
    private AddUserDto addUserDto;
    @Spy
    private AlterUserDataDto alterUserDataDto;



    @BeforeEach
    public void setUp(){
        userDatabaseController = new UserDatabaseController(userRepositoryService);
    }
    @Test
    public void userLogin_happy_path_test(){
        //Given
        userLoginDto.setUsername(randomAlphabetic( 8 ));
        userLoginDto.setPassword(randomAlphabetic( 8 ));

        when(userRepositoryService.userLogin(anyString(),anyString())).thenReturn(userDto);

        //When
        User.UserDto result = userDatabaseController.userLogin(userLoginDto);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> captorKeepLogin = ArgumentCaptor.forClass(Boolean.class);
        verify(userRepositoryService).userLogin(captorUsername.capture(),captorPassword.capture());

        assertEquals(userLoginDto.getUsername(),captorUsername.getValue());
        assertEquals(userLoginDto.getPassword(),captorPassword.getValue());
        //check method call times
        verify(userRepositoryService,times(1)).userLogin(anyString(),anyString());
        //check return/exception values
        assertEquals(result, userDto);
    }
    @Test
    public void addUser_happy_path_test(){
        //Given
        addUserDto.setUsername(randomAlphabetic( 8 ));
        addUserDto.setPassword(randomAlphabetic( 8 ));
        addUserDto.setUserDisplayName(randomAlphabetic( 8 ));
        doNothing().when(userRepositoryService).addUser(anyString(),anyString(),anyString());
        //When
        userDatabaseController.addUser(addUserDto);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorUserDisplayName = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).addUser(captorUsername.capture(),captorPassword.capture(), captorUserDisplayName.capture());
        assertEquals(addUserDto.getUsername(),captorUsername.getValue());
        assertEquals(addUserDto.getPassword(),captorPassword.getValue());
        assertEquals(addUserDto.getUserDisplayName(), captorUserDisplayName.getValue());
        //check method call times
        verify(userRepositoryService,times(1)).addUser(anyString(),anyString(),anyString());
        //check return/exception values
    }
    @Test
    public void alterUser_alter_both_password_and_displayName_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(randomAlphabetic( 8 ));
        alterUserDataDto.setNewUserDisplayName(randomAlphabetic( 8 ));
        doNothing().when(userRepositoryService).alterPassword(anyString(),anyString());
        doNothing().when(userRepositoryService).alterUserDisplayName(anyString(),anyString());
        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorUsername2 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorUserDisplayName = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).alterPassword(captorUsername1.capture(),captorPassword.capture());
        assertEquals(username,captorUsername1.getValue());
        assertEquals(alterUserDataDto.getNewPassword(),captorPassword.getValue());

        verify(userRepositoryService).alterUserDisplayName(captorUsername2.capture(),captorUserDisplayName.capture());
        assertEquals(username,captorUsername2.getValue());
        assertEquals(alterUserDataDto.getNewUserDisplayName(), captorUserDisplayName.getValue());
        //check method call times
        verify(userRepositoryService,times(1)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(1)).alterUserDisplayName(anyString(),anyString());
        //check return/exception values
    }
    @Test
    public void alterUser_alter_password_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(randomAlphabetic( 8 ));
        alterUserDataDto.setNewUserDisplayName(null);

        doNothing().when(userRepositoryService).alterPassword(anyString(),anyString());
        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).alterPassword(captorUsername.capture(),captorPassword.capture());
        assertEquals(username,captorUsername.getValue());
        assertEquals(alterUserDataDto.getNewPassword(),captorPassword.getValue());
        //check method call times
        verify(userRepositoryService,times(1)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(0)).alterUserDisplayName(anyString(),anyString());
        //check return/exception values
    }
    @Test
    public void alterUser_alter_displayName_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(null);
        alterUserDataDto.setNewUserDisplayName(randomAlphabetic( 8 ));

        doNothing().when(userRepositoryService).alterUserDisplayName(anyString(),anyString());

        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorUserDisplayName = ArgumentCaptor.forClass(String.class);

        verify(userRepositoryService).alterUserDisplayName(captorUsername.capture(),captorUserDisplayName.capture());
        assertEquals(username,captorUsername.getValue());
        assertEquals(alterUserDataDto.getNewUserDisplayName(), captorUserDisplayName.getValue());
        //check method call times
        verify(userRepositoryService,times(0)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(1)).alterUserDisplayName(anyString(),anyString());
        //check return/exception values
    }

    @Test
    public void alterUser_alter_nothing_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(null);
        alterUserDataDto.setNewUserDisplayName(null);

        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        //check parameters
        //check method call times
        verify(userRepositoryService,times(0)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(0)).alterUserDisplayName(anyString(),anyString());
        //check return/exception values
    }
    @Test
    public void deleteUser_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        doNothing().when(userRepositoryService).deleteUser(anyString());

        //When
        userDatabaseController.deleteUser(username);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).deleteUser(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        //check method call times
        verify(userRepositoryService,times(1)).deleteUser(anyString());
        //check return/exception values
    }



}
