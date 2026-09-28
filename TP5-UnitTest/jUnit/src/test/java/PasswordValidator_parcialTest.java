import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class PasswordValidator_parcialTest {
    
    @Test 
    public void isValid_NullString_ReturnsFalse(){

        //Arrange
        String pass = "";
        PasswordValidator_parcial pv = new PasswordValidator_parcial();
        
        //Act
        boolean resultado = pv.isValid(pass);
        boolean isfalse = false;
        
        //Assert
        assertEquals(isfalse, resultado);
    
    }

    @Test 
    public void isValid_9caracterPass_ReturnsFalse(){

        //Arrange
        String pass = "abcdefghi";
        PasswordValidator_parcial pv = new PasswordValidator_parcial();
        
        //Act
        boolean resultado = pv.isValid(pass);
        boolean isfalse = false;
        
        //Assert
        assertEquals(isfalse, resultado);
    
    }

}
