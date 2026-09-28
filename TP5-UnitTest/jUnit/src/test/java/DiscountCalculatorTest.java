
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

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
    
    @Test
    public void applyDiscount_regularCustomer_ReturnsMontoSinDescuento(){
        //Arrange
        DiscountCalculator discountCalculator = new DiscountCalculator();
        String cliente = "REGULAR";
        double montoInicial = 100.0;
        double montoEsperado = 100.0; //no deberia tener descuento

        //Act
        double total = discountCalculator.applyDiscount(montoInicial, cliente);

        //Assert 
        assertEquals(montoEsperado, total);
    }

    @Test
    public void applyDiscount_vipCustomer_RetornaMontoCon10Descuento(){
        //Arrange
        DiscountCalculator discountCalculator = new DiscountCalculator();
        String cliente = "VIP";
        double montoInicial = 100.0;
        double montoEsperado = 90.0; //no deberia tener descuento

        //Act
        double total = discountCalculator.applyDiscount(montoInicial, cliente);

        //Assert 
        assertEquals(montoEsperado, total);
    }

    @Test
    public void applyDiscount_employeeCustomer_RetornaMontoCon30Descuento(){
        //Arrange
        DiscountCalculator discountCalculator = new DiscountCalculator();
        String cliente = "EMPLOYEE";
        double montoInicial = 100.0;
        double montoEsperado = 70.0; //no deberia tener descuento

        //Act
        double total = discountCalculator.applyDiscount(montoInicial, cliente);

        //Assert 
        assertEquals(montoEsperado, total);
    }

    @Test
    public void applyDiscount_clienteIncorrecto_ThrowException(){
        //Arrange
        DiscountCalculator discountCalculator = new DiscountCalculator();
        String cliente = "EMPLO";
        double montoInicial = 100.0;
        //Act
              //Assert
        assertThrows(IllegalArgumentException.class, () -> discountCalculator.applyDiscount(montoInicial, cliente));
        //Assert 
    
    }
}
