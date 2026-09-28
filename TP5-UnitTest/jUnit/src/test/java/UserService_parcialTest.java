
import org.junit.jupiter.api.*;
import org.mockito.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserService_parcialTest
{
    @Mock 
    EmailSender_parcial email_sender;

    @Test 
    public void register_normalEmail_callSendOnce(){
        //Arrange
        String email = "juliangonzales@gmail.com";
        UserService_parcial userService = new UserService_parcial(email_sender);

        //Act
        userService.register(email);

        //Assert
        verify(email_sender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test 
    public void register_noarroba_throwException(){

        //Arrange
        UserService_parcial userService = new UserService_parcial(email_sender);
        String correo = "juliangonzalesarrobagmail.com";

        //Act y Assert
        assertThrows(IllegalArgumentException.class, ()->userService.register(correo));
        
        //Assert
        verify(email_sender, never()).send(anyString(), anyString(), anyString());
    }

    @Test
    public void register_normalEmail_tieneEmail(){
        //Arrange
        String email = "juliangonzales@gmail.com";
        FakeEmailSender_parcial emailSender = new FakeEmailSender_parcial();
        UserService_parcial userService = new UserService_parcial(emailSender);

        //Act
        userService.register(email);

        //Assert
        assertEquals(1, emailSender.getEmailCount());
    }

  
}
