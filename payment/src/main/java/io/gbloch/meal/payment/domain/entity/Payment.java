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

package io.gbloch.meal.payment.domain.entity;

import io.gbloch.meal.core.annotations.ddd.AggregateRoot;
import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.domain.vo.PaymentStatus;
import io.gbloch.meal.payment.domain.vo.PaymentId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

/**
 * Order.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
@AggregateRoot
public final class Payment extends EntityBase<PaymentId> {

    private final OrderId orderId;

    private final CustomerId customerId;

    private final Money price;

    private PaymentStatus paymentStatus;

    private ZonedDateTime createdAt;

    private Payment(
        PaymentId id,
        OrderId orderId,
        CustomerId customerId,
        Money price,
        PaymentStatus paymentStatus,
        ZonedDateTime createdAt
    ) {
        super(id);
        this.orderId = orderId;
        this.customerId = customerId;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public void initializePayment() {
        setId(new PaymentId(UUID.randomUUID()));
        this.createdAt = ZonedDateTime.now();
    }

    public void validatePayment(List<String> failureMessages) {
        if (price == null || !price.isGreaterThanZero()) {
            failureMessages.add("Price must be greater than zero");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {

        private PaymentId id;
        private OrderId orderId;
        private CustomerId customerId;
        private Money price;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;

        PaymentBuilder() {}

        public PaymentBuilder id(PaymentId id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public PaymentBuilder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public PaymentBuilder price(Money price) {
            this.price = price;
            return this;
        }

        public PaymentBuilder paymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public PaymentBuilder createdAt(ZonedDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Payment build() {
            return new Payment(
                this.id,
                this.orderId,
                this.customerId,
                this.price,
                this.paymentStatus,
                this.createdAt
            );
        }
    }
}
