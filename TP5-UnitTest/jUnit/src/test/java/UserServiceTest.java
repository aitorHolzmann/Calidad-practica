
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    EmailSender emailSender; // Mockito crea una implementación falsa de la interfaz

    @Test
    void register_validEmail_SendsOnce() {
        // Arrange
        UserService userService= new UserService(emailSender);
        String email = "hola@gmail.com";
        // Act
        userService.register(email);

        // Assert
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test 
    void register_invalidEmail_SendsNever(){
        
        //Arrange
        UserService userService = new UserService(emailSender);
        String invalidEmail = "holaarrobagmail.com";

        //Act o Assert?
        assertThrows(IllegalArgumentException.class, () -> userService.register(invalidEmail));

        //Assert
        verify(emailSender, never()).send(anyString(), anyString(), anyString());
    }

    @Test 
    void register_emailsToStore_hasEmails(){
        //Arrange 
        FakeEmailSender fakeEmailSender = new FakeEmailSender();
        UserService userService = new UserService(fakeEmailSender);
        String email = "hola@gmail.com";

        //Act
        userService.register(email);

        //Assert
        assertEquals(1, fakeEmailSender.getNumberEmails());
    }
}    


