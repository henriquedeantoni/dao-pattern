package model.entities.enums;

public enum OrderStatus {
    ONAPPROVAL(1),
    PAID(2),
    DELIVERED(3),
    CANCELED(4),
    LOST(5);

    private int code;

    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus valueOf(int code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code: " + code);
    }
	
}
