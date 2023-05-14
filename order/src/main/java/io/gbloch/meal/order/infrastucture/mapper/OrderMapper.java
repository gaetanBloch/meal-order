/*
 * Copyright (c) 2023 Gaëtan Bloch and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gbloch.meal.order.infrastucture.mapper;

import static io.gbloch.meal.order.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

import io.gbloch.meal.domain.error.ErrorMessages;
import io.gbloch.meal.domain.vo.Address;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.domain.vo.ProductId;
import io.gbloch.meal.domain.vo.Quantity;
import io.gbloch.meal.domain.vo.RestaurantId;
import io.gbloch.meal.order.domain.entity.Order;
import io.gbloch.meal.order.domain.entity.OrderItem;
import io.gbloch.meal.order.domain.entity.Product;
import io.gbloch.meal.order.domain.vo.OrderItemId;
import io.gbloch.meal.order.domain.vo.TrackingId;
import io.gbloch.meal.order.infrastucture.entity.OrderAddressEntity;
import io.gbloch.meal.order.infrastucture.entity.OrderEntity;
import io.gbloch.meal.order.infrastucture.entity.OrderItemEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderMapper.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
public final class OrderMapper {

    public OrderEntity toOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity
            .builder()
            .id(order.getId().getValue())
            .customerId(order.getCustomerId().getValue())
            .restaurantId(order.getRestaurantId().getValue())
            .trackingId(order.getTrackingId().getValue())
            .address(toAddressEntity(order.getDeliveryAddress()))
            .price(order.getPrice().amount())
            .items(orderItemsToOrderItemEntities(order.getItems()))
            .orderStatus(order.getStatus())
            .failureMessages(
                order.getErrors() != null
                    ? String.join(FAILURE_MESSAGE_DELIMITER, order.getErrors().messages())
                    : ""
            )
            .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }

    public Order toOrder(OrderEntity orderEntity) {
        return Order
            .builder()
            .id(new OrderId(orderEntity.getId()))
            .customerId(new CustomerId(orderEntity.getCustomerId()))
            .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
            .deliveryAddress(toDeliveryAddress(orderEntity.getAddress()))
            .price(new Money(orderEntity.getPrice()))
            .items(toOrderItems(orderEntity.getItems()))
            .trackingId(new TrackingId(orderEntity.getTrackingId()))
            .status(orderEntity.getOrderStatus())
            .errors(
                orderEntity.getFailureMessages().isEmpty()
                    ? new ErrorMessages(new ArrayList<>(0))
                    : new ErrorMessages(
                        new ArrayList<>(
                            Arrays.asList(
                                orderEntity.getFailureMessages().split(FAILURE_MESSAGE_DELIMITER)
                            )
                        )
                    )
            )
            .build();
    }

    private List<OrderItem> toOrderItems(List<OrderItemEntity> items) {
        return items
            .stream()
            .map(orderItemEntity ->
                OrderItem
                    .builder()
                    .id(new OrderItemId(orderItemEntity.getId()))
                    .product(new Product(new ProductId(orderItemEntity.getProductId())))
                    .price(new Money(orderItemEntity.getPrice()))
                    .quantity(new Quantity(orderItemEntity.getQuantity()))
                    .subTotal(new Money(orderItemEntity.getSubTotal()))
                    .build()
            )
            .collect(Collectors.toList());
    }

    private Address toDeliveryAddress(OrderAddressEntity address) {
        return new Address(
            address.getId(),
            address.getStreet(),
            address.getZipCode(),
            address.getCity(),
            address.getCountry()
        );
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items
            .stream()
            .map(orderItem ->
                OrderItemEntity
                    .builder()
                    .id(orderItem.getId().getValue())
                    .productId(orderItem.getProduct().getId().getValue())
                    .price(orderItem.getPrice().amount())
                    .quantity(orderItem.getQuantity().quantity())
                    .subTotal(orderItem.getSubTotal().amount())
                    .build()
            )
            .collect(Collectors.toList());
    }

    private OrderAddressEntity toAddressEntity(Address deliveryAddress) {
        return OrderAddressEntity
            .builder()
            .id(deliveryAddress.id())
            .street(deliveryAddress.street())
            .zipCode(deliveryAddress.zipCode())
            .city(deliveryAddress.city())
            .country(deliveryAddress.country())
            .build();
    }
}
