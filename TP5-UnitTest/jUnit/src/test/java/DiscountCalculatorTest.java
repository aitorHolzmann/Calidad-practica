
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountCalculatorTest {
   
    @Test 
    public void applyDiscount_amountBelow0_ThrowException(){
        //Arrange
        DiscountCalculator calculador = new DiscountCalculator();
        double amount = -1.0;
        String tipoCliente = "nada";
        
        //Act
        
        //Assert
        assertThrows(IllegalArgumentException.class, () -> calculador.applyDiscount(amount, tipoCliente));
    }
}
