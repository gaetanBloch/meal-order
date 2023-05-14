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

import io.gbloch.meal.core.annotations.ddd.AggregateRoot;
import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.ActiveType;
import io.gbloch.meal.domain.vo.AvailabilityType;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.OrderApprovalStatus;
import io.gbloch.meal.domain.vo.OrderStatus;
import io.gbloch.meal.domain.vo.RestaurantId;
import io.gbloch.meal.restaurant.domain.vo.OrderApprovalId;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

/**
 * Restaurant.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
@AggregateRoot
public final class Restaurant extends EntityBase<RestaurantId> {

    private final OrderDetail orderDetail;
    private OrderApproval orderApproval;
    private ActiveType activeType;

    private Restaurant(
        RestaurantId id,
        OrderDetail orderDetail,
        OrderApproval orderApproval,
        ActiveType activeType
    ) {
        super(id);
        this.orderDetail = orderDetail;
        this.orderApproval = orderApproval;
        this.activeType = activeType;
    }

    public void validateOrder(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Payment is not completed for order: " + orderDetail.getId());
        }
        Money totalAmount = orderDetail
            .getProducts()
            .stream()
            .map(product -> {
                if (!product.getAvailability().equals(AvailabilityType.AVAILABLE)) {
                    failureMessages.add(
                        "Product with id: " + product.getId().getValue() + " is not available"
                    );
                }
                return product.getPrice().multiply(product.getQuantity().quantity());
            })
            .reduce(Money.ZERO, Money::add);

        if (!totalAmount.equals(orderDetail.getTotalAmount())) {
            failureMessages.add("Price total is not correct for order: " + orderDetail.getId());
        }
    }

    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval =
            OrderApproval
                .builder()
                .id(new OrderApprovalId(UUID.randomUUID()))
                .restaurantId(this.getId())
                .orderId(this.getOrderDetail().getId())
                .approvalStatus(orderApprovalStatus)
                .build();
    }

    public void setActiveType(ActiveType activeType) {
        this.activeType = activeType;
    }

    public static RestaurantBuilder builder() {
        return new RestaurantBuilder();
    }

    public static class RestaurantBuilder {

        private RestaurantId id;
        private OrderDetail orderDetail;
        private OrderApproval orderApproval;
        private ActiveType activeType;

        RestaurantBuilder() {}

        public RestaurantBuilder id(RestaurantId id) {
            this.id = id;
            return this;
        }

        public RestaurantBuilder orderDetail(OrderDetail orderDetail) {
            this.orderDetail = orderDetail;
            return this;
        }

        public RestaurantBuilder orderApproval(OrderApproval orderApproval) {
            this.orderApproval = orderApproval;
            return this;
        }

        public RestaurantBuilder activeType(ActiveType activeType) {
            this.activeType = activeType;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this.id, this.orderDetail, this.orderApproval, this.activeType);
        }
    }
}
