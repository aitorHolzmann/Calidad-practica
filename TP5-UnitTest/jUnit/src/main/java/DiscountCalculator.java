public class DiscountCalculator {
    int limit = 0;
    /**
     * InnerDiscountCalculator
     */
    public enum tipoCliente {
        REGULAR,
        VIP,
        EMPLOYEE

    }   
 
    public double applyDiscount(double amount, String customerType) {
        if (amount < limit){
            throw new IllegalArgumentException("Monto invalido");
        }

        if (customerType.equals("REGULAR")){
            return amount;
        }else if(customerType.equals("VIP")){
            return amount * 0.9;
        }else if (customerType.equals("EMPLOYEE")){
            return amount * 0.7;
        }else{
            throw new IllegalArgumentException("Cliente invalido");
        }
        
        //return 0;
    }
}