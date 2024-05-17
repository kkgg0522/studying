package hello.core.order;

public interface OrderService {
   // Order createOrder(Long memberId, Long orderId);
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
