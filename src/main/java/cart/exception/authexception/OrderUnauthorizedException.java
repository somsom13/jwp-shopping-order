package cart.exception.authexception;

public class OrderUnauthorizedException extends UnauthorizedException {

    private static final String MESSAGE = "다른 사용자의 주문 내역은 조회할 수 없습니다. orderId = %d";

    public OrderUnauthorizedException(long orderId) {
        super(String.format(MESSAGE, orderId));
    }
}
