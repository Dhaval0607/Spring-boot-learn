package com.example.orderservice.service;

import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderRequestLineItemsDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderListItems;
import com.example.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).build();
        List<OrderListItems> orderListItemsList = orderRequest.getOrderRequestLineItemsDto().stream().map(this::maptoDto).toList();

        order.setOrderListItemsList(orderListItemsList);
        List<String> skuCodes = order.getOrderListItemsList().stream().
                map(OrderListItems::getSkucode).toList();

        InventoryResponse[] inventoryResponsesArray=  webClient.build().get().uri("http://inventory-service/api/inventory"
                ,uriBuilder -> uriBuilder.queryParam("skucode",skuCodes).build()).
                retrieve().
                        bodyToMono(InventoryResponse[].class).block();
        boolean allProductMatch = Arrays.stream(inventoryResponsesArray).
                allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if (allProductMatch){
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    private OrderListItems maptoDto(OrderRequestLineItemsDto orderRequestLineItemsDto) {
            OrderListItems orderListItems = OrderListItems.builder().price(orderRequestLineItemsDto.getPrice())
                    .quantity(orderRequestLineItemsDto.getQuantity()).skucode(orderRequestLineItemsDto.getSkucode()).build();
            return orderListItems;
    }

}
