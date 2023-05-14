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
import io.gbloch.meal.domain.vo.OrderApprovalStatus;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.domain.vo.RestaurantId;
import io.gbloch.meal.restaurant.domain.vo.OrderApprovalId;
import lombok.Getter;

/**
 * OrderApproval.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
public final class OrderApproval extends EntityBase<OrderApprovalId> {

    private final RestaurantId restaurantId;
    private final OrderId orderId;
    private final OrderApprovalStatus approvalStatus;

    private OrderApproval(
        OrderApprovalId id,
        RestaurantId restaurantId,
        OrderId orderId,
        OrderApprovalStatus approvalStatus
    ) {
        super(id);
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.approvalStatus = approvalStatus;
    }

    public static OrderApprovalBuilder builder() {
        return new OrderApprovalBuilder();
    }

    public static class OrderApprovalBuilder {

        private OrderApprovalId id;
        private RestaurantId restaurantId;
        private OrderId orderId;
        private OrderApprovalStatus approvalStatus;

        OrderApprovalBuilder() {}

        public OrderApprovalBuilder id(OrderApprovalId id) {
            this.id = id;
            return this;
        }

        public OrderApprovalBuilder restaurantId(RestaurantId restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public OrderApprovalBuilder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderApprovalBuilder approvalStatus(OrderApprovalStatus approvalStatus) {
            this.approvalStatus = approvalStatus;
            return this;
        }

        public OrderApproval build() {
            return new OrderApproval(this.id, this.restaurantId, this.orderId, this.approvalStatus);
        }
    }
}
