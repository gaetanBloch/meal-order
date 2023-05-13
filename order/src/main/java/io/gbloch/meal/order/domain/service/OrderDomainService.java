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

package io.gbloch.meal.order.domain.service;

import io.gbloch.meal.domain.error.ErrorMessages;
import io.gbloch.meal.domain.vo.ActiveType;
import io.gbloch.meal.order.domain.entity.Order;
import io.gbloch.meal.order.domain.entity.Restaurant;
import io.gbloch.meal.order.domain.error.OrderDomainException;
import io.gbloch.meal.order.domain.event.OrderCancelledEvent;
import io.gbloch.meal.order.domain.event.OrderCreatedEvent;
import io.gbloch.meal.order.domain.event.OrderPaidEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * OrderDomainService.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Slf4j
@ApplicationScoped
public final class OrderDomainService {

    public OrderCreatedEvent createOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setProductInfo(order, restaurant);
        order.validate();
        order.initialize();
        log.info("Order {} created", order.getId().getValue());
        return new OrderCreatedEvent(order);
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (restaurant.getActive().equals(ActiveType.INACTIVE)) {
            throw new OrderDomainException(
                String.format("Restaurant %s is not active", restaurant.getId().getValue())
            );
        }
    }

    private void setProductInfo(Order order, Restaurant restaurant) {
        order
            .getItems()
            .forEach(item -> {
                restaurant
                    .getProducts()
                    .stream()
                    .filter(product -> product.equals(item.getProduct()))
                    .findFirst()
                    .ifPresent(product ->
                        product.updateProductInfo(
                            item.getProduct().getLabel(),
                            item.getProduct().getPrice()
                        )
                    );
            });
    }

    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order {} paid", order.getId().getValue());
        return new OrderPaidEvent(order);
    }

    public void confirmOrder(Order order) {
        order.confirm();
        log.info("Order {} confirmed", order.getId().getValue());
    }

    public OrderCancelledEvent cancelPayment(Order order, ErrorMessages errorMessages) {
        order.cancelling(errorMessages);
        log.info("Order {} payment cancelled", order.getId().getValue());
        return new OrderCancelledEvent(order);
    }

    public OrderCancelledEvent cancelOrder(Order order, ErrorMessages errorMessages) {
        order.cancel(errorMessages);
        log.info("Order {} cancelled", order.getId().getValue());
        return new OrderCancelledEvent(order);
    }
}
