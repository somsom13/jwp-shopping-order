package cart.repository.mapper;

import cart.dao.dto.OrderItemProductDto;
import cart.dao.entity.OrderEntity;
import cart.dao.entity.OrderItemEntity;
import cart.domain.Member;
import cart.domain.Money;
import cart.domain.Order;
import cart.domain.OrderItem;
import cart.domain.Product;
import cart.domain.Quantity;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderItemEntity toOrderItemEntity(OrderItem orderItem, long orderId) {
        Product product = orderItem.getProduct();
        return new OrderItemEntity(
            orderId,
            product.getId(),
            orderItem.getQuantityCount(),
            product.getPriceIntValue()
        );
    }

    public static OrderItem toOrderItem(OrderItemProductDto orderItemProductDto) {
        return new OrderItem(
            orderItemProductDto.getOrderItemId(),
            new Product(orderItemProductDto.getProductId(), orderItemProductDto.getProductName(), Money.from(orderItemProductDto.getPrice()),
                orderItemProductDto.getProductImageUrl()),
            Quantity.from(orderItemProductDto.getQuantity())
        );
    }

    public static OrderEntity toOrderEntity(Order order, Member member) {
        return new OrderEntity(
            member.getId()
        );
    }

    public static Order toOrder(OrderEntity orderEntity, List<OrderItemProductDto> orderItemProducts) {
        List<OrderItem> orderItems = orderItemProducts.stream()
            .map(OrderMapper::toOrderItem)
            .collect(Collectors.toList());
        return new Order(orderEntity.getId(), orderItems);

    }

}
