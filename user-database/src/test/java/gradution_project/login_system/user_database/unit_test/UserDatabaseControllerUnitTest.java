package gradution_project.login_system.user_database.unit_test;

import gradution_project.login_system.user_database.api.UserDatabaseController;
import gradution_project.login_system.user_database.api.dto.AddUserDto;
import gradution_project.login_system.user_database.api.dto.AlterUserDataDto;
import gradution_project.login_system.user_database.api.dto.UserLoginDto;
import gradution_project.login_system.user_database.entity.User;
import gradution_project.login_system.user_database.repository.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class UserDatabaseControllerUnitTest {
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
    @Autowired
    private MockMvc mvc;


    //SUT
    private UserDatabaseController userDatabaseController;

    @BeforeEach
    public void setUp(){
        userDatabaseController = new UserDatabaseController(userRepositoryService);
    }
    @Test
    public void userLogin_success(){
        //Given
        userLoginDto.setUsername(RandomStringUtils.randomAlphabetic( 8 ));
        userLoginDto.setPassword(RandomStringUtils.randomAlphabetic( 8 ));
        userLoginDto.setKeepLogin(false);

        when(userRepositoryService.userLogin(anyString(),anyString(),anyBoolean())).thenReturn(userDto);

        //When
        User.UserDto result = userDatabaseController.userLogin(userLoginDto);
        //Then
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> captorKeepLogin = ArgumentCaptor.forClass(Boolean.class);
        verify(userRepositoryService).userLogin(captorUsername.capture(),captorPassword.capture(),captorKeepLogin.capture());
        assertEquals(userLoginDto.getUsername(),captorUsername.getValue());
        assertEquals(userLoginDto.getPassword(),captorPassword.getValue());
        assertEquals(userLoginDto.isKeepLogin(), captorKeepLogin.getValue());
        verify(userRepositoryService,times(1)).userLogin(anyString(),anyString(),anyBoolean());
        assertEquals(result, userDto);
    }
    @Test
    public void addUser_success(){
        //Given
        addUserDto.setUsername(RandomStringUtils.randomAlphabetic( 8 ));
        addUserDto.setPassword(RandomStringUtils.randomAlphabetic( 8 ));
        addUserDto.setUserDisplayName(RandomStringUtils.randomAlphabetic( 8 ));

        doNothing().when(userRepositoryService).addUser(anyString(),anyString(),anyString());

        //When
        userDatabaseController.addUser(addUserDto);
        //Then
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorUserDisplayName = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).addUser(captorUsername.capture(),captorPassword.capture(), captorUserDisplayName.capture());
        assertEquals(addUserDto.getUsername(),captorUsername.getValue());
        assertEquals(addUserDto.getPassword(),captorPassword.getValue());
        assertEquals(addUserDto.getUserDisplayName(), captorUserDisplayName.getValue());
        verify(userRepositoryService,times(1)).addUser(anyString(),anyString(),anyString());
    }
    @Test
    public void alterUser_alter_both_password_and_displayName_success(){
        //Given
        String username = RandomStringUtils.randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(RandomStringUtils.randomAlphabetic( 8 ));
        alterUserDataDto.setNewUserDisplayName(RandomStringUtils.randomAlphabetic( 8 ));

        doNothing().when(userRepositoryService).alterPassword(anyString(),anyString());
        doNothing().when(userRepositoryService).alterUserDisplayName(anyString(),anyString());

        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
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

        verify(userRepositoryService,times(1)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(1)).alterUserDisplayName(anyString(),anyString());
    }
    @Test
    public void alterUser_alter_password_success(){
        //Given
        String username = RandomStringUtils.randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(RandomStringUtils.randomAlphabetic( 8 ));
        alterUserDataDto.setNewUserDisplayName(null);

        doNothing().when(userRepositoryService).alterPassword(anyString(),anyString());

        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).alterPassword(captorUsername.capture(),captorPassword.capture());
        assertEquals(username,captorUsername.getValue());
        assertEquals(alterUserDataDto.getNewPassword(),captorPassword.getValue());

        verify(userRepositoryService,times(1)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(0)).alterUserDisplayName(anyString(),anyString());
    }
    @Test
    public void alterUser_alter_displayName_success(){
        //Given
        String username = RandomStringUtils.randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(null);
        alterUserDataDto.setNewUserDisplayName(RandomStringUtils.randomAlphabetic( 8 ));

        doNothing().when(userRepositoryService).alterUserDisplayName(anyString(),anyString());

        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorUserDisplayName = ArgumentCaptor.forClass(String.class);

        verify(userRepositoryService).alterUserDisplayName(captorUsername.capture(),captorUserDisplayName.capture());
        assertEquals(username,captorUsername.getValue());
        assertEquals(alterUserDataDto.getNewUserDisplayName(), captorUserDisplayName.getValue());

        verify(userRepositoryService,times(0)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(1)).alterUserDisplayName(anyString(),anyString());
    }

    @Test
    public void alterUser_alter_nothing_success(){
        //Given
        String username = RandomStringUtils.randomAlphabetic( 8 );
        alterUserDataDto.setNewPassword(null);
        alterUserDataDto.setNewUserDisplayName(null);

        //When
        userDatabaseController.alterUserData(username,alterUserDataDto);
        //Then
        verify(userRepositoryService,times(0)).alterPassword(anyString(),anyString());
        verify(userRepositoryService,times(0)).alterUserDisplayName(anyString(),anyString());
    }
    @Test
    public void deleteUser_success(){
        //Given
        String username = RandomStringUtils.randomAlphabetic( 8 );
        doNothing().when(userRepositoryService).deleteUser(anyString());

        //When
        userDatabaseController.deleteUser(username);
        //Then
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryService).deleteUser(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        verify(userRepositoryService,times(1)).deleteUser(anyString());
    }



}
