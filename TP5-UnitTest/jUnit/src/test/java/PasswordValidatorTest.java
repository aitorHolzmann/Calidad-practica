import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;


public class PasswordValidatorTest {
    
    @Test
    void isValid_NullString_ReturnsFalse(){
        //Arrange
        String nullString = null;

        //Act
        boolean result = PasswordValidator.isValid(nullString);

        //Assert
        assertFalse(result);
    }

    @Test
    void isValid_StringLength9_ReturnsFalse(){
        //Arrange
        String longPass = "Hola1";

        //Act
        boolean result = PasswordValidator.isValid(longPass);

        //Assert
        assertFalse(result);
    }

    @Test 
    void isValid_noUppernoLowernoDigit_ReturnsFalse(){
        //Arrange
        String password = "_____________";

        //Act
        boolean result = PasswordValidator.isValid(password);

        //Assert
        assertFalse(result);
    }

    @Test 
    void isValid_noLower_ReturnsFalse(){
        //Arrange
        String password = "HOLAAAA1";

        //Act
        boolean result = PasswordValidator.isValid(password);

        //Assert
        assertFalse(result);
    }

    @Test 
    void isValid_noUpper_ReturnsFalse(){
        //Arrange
        String password = "holaaaa1";

        //Act
        boolean result = PasswordValidator.isValid(password);

        //Assert
        assertFalse(result);
    }

    @Test 
    void isValid_noDigit_ReturnsFalse(){
        //Arrange
        String password = "Holaaaaa";

        //Act
        boolean result = PasswordValidator.isValid(password);

        //Assert
        assertFalse(result);
    }

    @Test 
    void isValid_LowerUpperDigitPassword_ReturnsTrue(){
        //Arrange
        String password = "Holaaaa1";

        //Act
        boolean result = PasswordValidator.isValid(password);

        //Assert
        assertTrue(result);
    }




}
