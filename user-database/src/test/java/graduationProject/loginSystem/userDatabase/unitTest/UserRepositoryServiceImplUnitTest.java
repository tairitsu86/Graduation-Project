package graduationProject.loginSystem.userDatabase.unitTest;

import graduationProject.loginSystem.userDatabase.entity.User;
import graduationProject.loginSystem.userDatabase.controller.excpetion.UserLoginWithIncorrectAccountException;
import graduationProject.loginSystem.userDatabase.controller.excpetion.UserNotExistException;
import graduationProject.loginSystem.userDatabase.controller.excpetion.UsernameAlreadyExistException;
import graduationProject.loginSystem.userDatabase.repository.UserRepository;
import graduationProject.loginSystem.userDatabase.repository.UserRepositoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryServiceImplUnitTest {
    //SUT
    private UserRepositoryServiceImpl userRepositoryServiceImpl;
    @Spy
    private UserRepository userRepository;
    @Mock
    private User user;

    @BeforeEach
    public void setup(){
        userRepositoryServiceImpl = new UserRepositoryServiceImpl(userRepository);
    }
    @Test
    public void checkUserExist_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(true);
        //When
        userRepositoryServiceImpl.checkUserExist(username);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        //check return/exception values
    }

    @Test
    public void checkUserExist_user_not_exist_should_throw_UserNotExistException_sad_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(false);
        //When
        UserNotExistException userNotExistException =assertThrows(UserNotExistException.class,()->
            userRepositoryServiceImpl.checkUserExist(username)
        );
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());

        assertEquals(username,userNotExistException.getUsername());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        //check return/exception values
        assertEquals(username,userNotExistException.getUsername());
    }

    @Test
    public void addUser_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String password = randomAlphabetic( 8 );
        String userDisplayName = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        //When
        userRepositoryServiceImpl.addUser(username,password,userDisplayName);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());

        ArgumentCaptor<User> captorUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captorUser.capture());
        assertEquals(username,captorUser.getValue().getUsername());
        assertEquals(password,captorUser.getValue().getPassword());
        assertEquals(userDisplayName, captorUser.getValue().getUserDisplayName());
        assertEquals(User.Permission.Normal,captorUser.getValue().getPermission());
        //check method call times
        verify(userRepository,times(1)).save(any());
        //check return/exception values

    }
    @Test
    public void addUser_user_already_exist_should_throw_UsernameAlreadyExistException_sad_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String password = randomAlphabetic( 8 );
        String userDisplayName = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(true);
        //When
        UsernameAlreadyExistException usernameAlreadyExistException = assertThrows(UsernameAlreadyExistException.class,()->
            userRepositoryServiceImpl.addUser(username,password,userDisplayName)
        );
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());

        assertEquals(username,usernameAlreadyExistException.getUsername());
        //check method call times
        verify(userRepository,times(0)).save(any());
        //check return/exception values
        assertEquals(username,usernameAlreadyExistException.getUsername());
    }

    @Test
    public void alterUserDisplayName_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String newUserDisplayName = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.getReferenceById(anyString())).thenReturn(user);
        //When
        userRepositoryServiceImpl.alterUserDisplayName(username,newUserDisplayName);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).getReferenceById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        ArgumentCaptor<String> captorUserDisplayName = ArgumentCaptor.forClass(String.class);
        verify(user).setUserDisplayName(captorUserDisplayName.capture());
        assertEquals(newUserDisplayName,captorUserDisplayName.getValue());
        //check method call times
        verify(userRepository,times(1)).save(any());
        //check return/exception values
    }
    @Test
    public void alterUserDisplayName_user_not_exist_should_throw_UserNotExistException_sad_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String newUserDisplayName = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(false);
        //When
        UserNotExistException userNotExistException =assertThrows(UserNotExistException.class,()->
            userRepositoryServiceImpl.alterUserDisplayName(username,newUserDisplayName)
        );
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        assertEquals(username,userNotExistException.getUsername());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        verify(userRepository,times(0)).save(any());
        //check return/exception values
        assertEquals(username,userNotExistException.getUsername());
    }
    @Test
    public void alterPassword_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String newPassword = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.getReferenceById(anyString())).thenReturn(user);
        //When
        userRepositoryServiceImpl.alterPassword(username,newPassword);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).getReferenceById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        ArgumentCaptor<String> captorPassword = ArgumentCaptor.forClass(String.class);
        verify(user).setPassword(captorPassword.capture());
        assertEquals(newPassword,captorPassword.getValue());
        //check method call times
        verify(userRepository,times(1)).save(any());
        //check return/exception values
    }
    @Test
    public void alterPassword_user_not_exist_should_throw_UserNotExistException_sad_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String newPassword = randomAlphabetic( 8 );
        when(userRepository.existsById(anyString())).thenReturn(false);
        //When
        UserNotExistException userNotExistException =assertThrows(UserNotExistException.class,()->
            userRepositoryServiceImpl.alterPassword(username,newPassword)
        );
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());

        assertEquals(username,userNotExistException.getUsername());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        verify(userRepository,times(0)).save(any());
        //check return/exception values
        assertEquals(username,userNotExistException.getUsername());
    }
    @Test
    public void deleteUser_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        doNothing().when(userRepository).deleteById(anyString());
        //When
        userRepositoryServiceImpl.deleteUser(username);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).deleteById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        //check method call times
        verify(userRepository,times(1)).deleteById(anyString());
        //check return/exception values
    }
    @Test
    public void userLogin_happy_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String password = randomAlphabetic( 8 );
        boolean keepLogin = false;
        String userDisplayName = randomAlphabetic( 8 );
        User.Permission permission = User.Permission.Normal;
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.getReferenceById(anyString())).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);
        when(user.getUserDisplayName()).thenReturn(userDisplayName);
        when(user.getPermission()).thenReturn(permission);
        //When
        User.UserDto userDto = userRepositoryServiceImpl.userLogin(username,password);
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername1 = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername1.capture());
        assertEquals(username,captorUsername1.getValue());

        ArgumentCaptor<String> captorUsername2 = ArgumentCaptor.forClass(String.class);
        verify(userRepository).getReferenceById(captorUsername2.capture());
        assertEquals(username,captorUsername2.getValue());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        verify(userRepository,times(1)).getReferenceById(anyString());
        //check return/exception values
        assertEquals(username,userDto.getUsername());
        assertEquals(userDisplayName,userDto.getUserDisplayName());
        assertEquals(permission,userDto.getPermission());
    }
    @Test
    public void userLogin_username_incorrect_should_throw_UserLoginWithIncorrectAccountException_sad_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String password = randomAlphabetic( 8 );
        boolean keepLogin = false;
        when(userRepository.existsById(anyString())).thenReturn(false);
        //When
        UserLoginWithIncorrectAccountException userLoginWithIncorrectAccountException
                = assertThrows(UserLoginWithIncorrectAccountException.class,()->
            userRepositoryServiceImpl.userLogin(username,password)
        );
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername.capture());
        assertEquals(username,captorUsername.getValue());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        verify(userRepository,times(0)).getReferenceById(anyString());
        //check return/exception values
        assertEquals(username,userLoginWithIncorrectAccountException.getUsername());
    }
    @Test
    public void userLogin_password_incorrect_should_throw_UserLoginWithIncorrectAccountException_sad_path_test(){
        //Given
        String username = randomAlphabetic( 8 );
        String password = randomAlphabetic( 8 );
        boolean keepLogin = false;
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.getReferenceById(anyString())).thenReturn(user);
        when(user.getPassword()).thenReturn(password+"abc");
        //When
        UserLoginWithIncorrectAccountException userLoginWithIncorrectAccountException
                = assertThrows(UserLoginWithIncorrectAccountException.class,()->
            userRepositoryServiceImpl.userLogin(username,password)
        );
        //Then
        //check parameters
        ArgumentCaptor<String> captorUsername1 = ArgumentCaptor.forClass(String.class);
        verify(userRepository).existsById(captorUsername1.capture());
        assertEquals(username,captorUsername1.getValue());

        ArgumentCaptor<String> captorUsername2 = ArgumentCaptor.forClass(String.class);
        verify(userRepository).getReferenceById(captorUsername2.capture());
        assertEquals(username,captorUsername2.getValue());
        //check method call times
        verify(userRepository,times(1)).existsById(anyString());
        verify(userRepository,times(1)).getReferenceById(anyString());
        //check return/exception values
        assertEquals(username,userLoginWithIncorrectAccountException.getUsername());

    }

}
