public class DiscountCalculator {
    int limit = 0;
    /**
     * InnerDiscountCalculator
     */
    public enum InnerDiscountCalculator {
        REGULAR,
        VIP,
        EMPLOYEE
        
    }   
    public double applyDiscount(double amount, String customerType) {
        if (amount < limit){
            throw new IllegalArgumentException("Monto invalido");
        }
        return 0;
    }
}