package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderRequestLineItemsDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderListItems;
import com.example.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).build();
        List<OrderListItems> orderListItemsList = orderRequest.getOrderRequestLineItemsDto().stream().map(this::maptoDto).toList();

        order.setOrderListItemsList(orderListItemsList);
        orderRepository.save(order);
    }

    private OrderListItems maptoDto(OrderRequestLineItemsDto orderRequestLineItemsDto) {
            OrderListItems orderListItems = OrderListItems.builder().price(orderRequestLineItemsDto.getPrice())
                    .quantity(orderRequestLineItemsDto.getQuantity()).skucode(orderRequestLineItemsDto.getSkucode()).build();
            return orderListItems;
    }

}
