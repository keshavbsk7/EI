package behavioural.strategy;

public class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    private String name;

    public CreditCardStrategy(String cardNumber, String name) {
        this.cardNumber = cardNumber;
        this.name = name;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid with credit card.");
    }
}
