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

package io.gbloch.meal.restaurant.domain.entity;

import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.domain.vo.OrderStatus;
import java.util.List;
import lombok.Getter;

/**
 * OrderDetail.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
public final class OrderDetail extends EntityBase<OrderId> {

    private final List<Product> products;
    private OrderStatus orderStatus;
    private Money totalAmount;

    private OrderDetail(
        OrderId orderId,
        List<Product> products,
        OrderStatus orderStatus,
        Money totalAmount
    ) {
        super(orderId);
        this.products = products;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    public static OrderDetailBuilder builder() {
        return new OrderDetailBuilder();
    }

    public static class OrderDetailBuilder {

        private OrderId orderId;
        private List<Product> products;
        private OrderStatus orderStatus;
        private Money totalAmount;

        OrderDetailBuilder() {}

        public OrderDetailBuilder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderDetailBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public OrderDetailBuilder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderDetailBuilder totalAmount(Money totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderDetail build() {
            return new OrderDetail(this.orderId, this.products, this.orderStatus, this.totalAmount);
        }
    }
}
