package behavioural.strategy;


public class PaymentSystem {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

       
        cart.setPaymentStrategy(new CreditCardStrategy("1234567890123456", "John Doe"));
        cart.checkout(100);

        
        cart.setPaymentStrategy(new PayPalStrategy("john.doe@example.com"));
        cart.checkout(200);
    }
}
