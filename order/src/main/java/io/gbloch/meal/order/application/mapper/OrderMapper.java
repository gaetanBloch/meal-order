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

package io.gbloch.meal.order.application.mapper;

import io.gbloch.meal.domain.vo.Address;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.ProductId;
import io.gbloch.meal.domain.vo.RestaurantId;
import io.gbloch.meal.order.application.dto.create.CreateOrderCommand;
import io.gbloch.meal.order.application.dto.create.CreateOrderResponse;
import io.gbloch.meal.order.application.dto.create.OrderAddress;
import io.gbloch.meal.order.application.dto.track.TrackOrderResponse;
import io.gbloch.meal.order.domain.entity.Order;
import io.gbloch.meal.order.domain.entity.OrderItem;
import io.gbloch.meal.order.domain.entity.Product;
import io.gbloch.meal.order.domain.entity.Restaurant;
import io.gbloch.meal.order.domain.vo.Quantity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * OrderMapper.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ApplicationScoped
public final class OrderMapper {

    public Restaurant toRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant
            .builder()
            .id(new RestaurantId(createOrderCommand.restaurantId()))
            .products(
                createOrderCommand
                    .items()
                    .stream()
                    .map(orderItem -> new Product(new ProductId(orderItem.productId())))
                    .toList()
            )
            .build();
    }

    public Order toOrder(CreateOrderCommand createOrderCommand) {
        return Order
            .builder()
            .customerId(new CustomerId(createOrderCommand.customerId()))
            .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
            .totalPrice(new Money(createOrderCommand.price()))
            .items(toOrderItemEntities(createOrderCommand.items()))
            .deliveryAddress(toAddress(createOrderCommand.address()))
            .build();
    }

    private List<OrderItem> toOrderItemEntities(
        List<io.gbloch.meal.order.application.dto.create.OrderItem> items
    ) {
        return items
            .stream()
            .map(item ->
                OrderItem
                    .builder()
                    .product(new Product(new ProductId(item.productId())))
                    .price(new Money(item.price()))
                    .quantity(new Quantity(item.quantity()))
                    .totalPrice(new Money(item.totalPrice()))
                    .build()
            )
            .toList();
    }

    private Address toAddress(OrderAddress address) {
        return new Address(address.street(), address.city(), address.zipCode(), address.country());
    }

    public CreateOrderResponse toOrderResponse(Order order, String message) {
        return CreateOrderResponse
            .builder()
            .orderTrackingId(order.getTrackingId().getValue())
            .orderStatus(order.getStatus())
            .message(message)
            .build();
    }

    public TrackOrderResponse toTrackOrderResponse(Order order) {
        return TrackOrderResponse
            .builder()
            .orderTrackingId(order.getTrackingId().getValue())
            .orderStatus(order.getStatus())
            .errorMessages(order.getErrors().messages())
            .build();
    }
}
