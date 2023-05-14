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

package io.gbloch.meal.order.domain.entity;

import io.gbloch.meal.core.annotations.ddd.AggregateRoot;
import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.error.ErrorMessages;
import io.gbloch.meal.domain.vo.Address;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.domain.vo.OrderStatus;
import io.gbloch.meal.domain.vo.RestaurantId;
import io.gbloch.meal.order.domain.error.OrderDomainException;
import io.gbloch.meal.order.domain.vo.OrderItemId;
import io.gbloch.meal.order.domain.vo.TrackingId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;

/**
 * Order.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
@AggregateRoot
public final class Order extends EntityBase<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final Address deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus status;
    private ErrorMessages errors;

    public static final String FAILURE_MESSAGE_DELIMITER = ",";

    private Order(OrderBuilder builder) {
        super(builder.id);
        this.customerId = builder.customerId;
        this.restaurantId = builder.restaurantId;
        this.deliveryAddress = builder.deliveryAddress;
        this.price = builder.price;
        this.items = builder.items;
        this.trackingId = builder.trackingId;
        this.status = builder.status;
        this.errors = builder.errors;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void initialize() {
        setId(new OrderId());
        trackingId = new TrackingId(UUID.randomUUID());
        this.status = OrderStatus.PENDING;

        initializeItems();
    }

    private void initializeItems() {
        AtomicLong itemId = new AtomicLong(1);
        items.forEach(item -> item.initialize(getId(), new OrderItemId(itemId.getAndIncrement())));
    }

    public void validate() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateInitialOrder() {
        if (status != null || getId() != null) {
            throw new OrderDomainException("Order should not be initialized!");
        }
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("The total price should be greater than zero!");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items
            .stream()
            .map(orderItem -> {
                validateItemPrice(orderItem);
                return orderItem.getSubTotal();
            })
            .reduce(Money.ZERO, Money::add);

        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException(
                "Total price: " +
                price.amount() +
                " is not equal to Order items total: " +
                orderItemsTotal.amount() +
                "!"
            );
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException(
                "Order item price: " +
                orderItem.getPrice().amount() +
                " is not valid for product " +
                orderItem.getProduct().getId()
            );
        }
    }

    private void updateErrors(ErrorMessages errors) {
        if (errors != null && !errors.hasErrors()) {
            if (this.errors == null) {
                this.errors = errors;
            } else {
                this.errors.messages()
                    .addAll(errors.messages().stream().filter(String::isEmpty).toList());
            }
        }
    }

    public void pay() {
        if (status != OrderStatus.PENDING) {
            throw new OrderDomainException("Order should be in PENDING status!");
        }
        this.status = OrderStatus.PAID;
    }

    public void confirm() {
        if (status != OrderStatus.PAID) {
            throw new OrderDomainException("Order should be in PAID status!");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancelling(ErrorMessages errors) {
        if (status != OrderStatus.PAID && status != OrderStatus.CONFIRMED) {
            throw new OrderDomainException("Order should be in PAID or CONFIRMED status!");
        }
        this.status = OrderStatus.CANCELLING;
        updateErrors(errors);
    }

    public void cancel(ErrorMessages errors) {
        if (status != OrderStatus.CANCELLING && status != OrderStatus.PENDING) {
            throw new OrderDomainException("Order should be in CANCELLING or PENDING status!");
        }
        this.status = OrderStatus.CANCELLED;
        updateErrors(errors);
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {

        private OrderId id;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private Address deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus status;
        private ErrorMessages errors;

        OrderBuilder() {}

        public OrderBuilder id(OrderId id) {
            this.id = id;
            return this;
        }

        public OrderBuilder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderBuilder restaurantId(RestaurantId restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public OrderBuilder deliveryAddress(Address deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public OrderBuilder price(Money totalPrice) {
            this.price = totalPrice;
            return this;
        }

        public OrderBuilder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public OrderBuilder trackingId(TrackingId trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public OrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder errors(ErrorMessages errors) {
            this.errors = errors;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
