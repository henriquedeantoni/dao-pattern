package model.entities.enums;

public enum PaymentMethod {
    CASH(1),
    CREDITCARD(2),
    DEBITCARD(3),
    PIX(4),
    PAYPAL(5),
    STRIPE(6);

    private int code;

    private PaymentMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentMethod valueOf(int code) {
        for (PaymentMethod value : PaymentMethod.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code: " + code);
    }
}
