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

import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.domain.vo.Quantity;
import io.gbloch.meal.order.domain.vo.OrderItemId;
import lombok.Getter;

/**
 * OrderItem.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
public final class OrderItem extends EntityBase<OrderItemId> {

    private OrderId orderId;
    private final Product product;
    private final Quantity quantity;
    private final Money price;
    private final Money subTotal;

    OrderItem(
        OrderItemId id,
        OrderId orderId,
        Product product,
        Quantity quantity,
        Money price,
        Money subTotal
    ) {
        super(id);
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    void initialize(OrderId orderId, OrderItemId id) {
        this.orderId = orderId;
        setId(id);
    }

    public boolean isPriceValid() {
        return (
            price.isGreaterThanZero() &&
            price.equals(product.getPrice()) &&
            subTotal.equals(price.multiply(quantity.quantity()))
        );
    }

    public static class OrderItemBuilder {

        private OrderItemId id;
        private OrderId orderId;
        private Product product;
        private Quantity quantity;
        private Money price;
        private Money totalPrice;

        OrderItemBuilder() {}

        public OrderItemBuilder id(OrderItemId id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderItemBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderItemBuilder quantity(Quantity quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder price(Money price) {
            this.price = price;
            return this;
        }

        public OrderItemBuilder subTotal(Money totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(
                this.id,
                this.orderId,
                this.product,
                this.quantity,
                this.price,
                this.totalPrice
            );
        }
    }
}
