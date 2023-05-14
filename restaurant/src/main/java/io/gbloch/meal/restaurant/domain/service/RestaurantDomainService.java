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

package io.gbloch.meal.restaurant.domain.service;

import io.gbloch.meal.domain.vo.OrderApprovalStatus;
import io.gbloch.meal.restaurant.domain.entity.Restaurant;
import io.gbloch.meal.restaurant.domain.event.OrderApprovalEvent;
import io.gbloch.meal.restaurant.domain.event.OrderApprovedEvent;
import io.gbloch.meal.restaurant.domain.event.OrderRejectedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * PaymentDomainService.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Slf4j
@ApplicationScoped
public final class RestaurantDomainService {

    public OrderApprovalEvent validateOrder(Restaurant restaurant, List<String> failureMessages) {
        restaurant.validateOrder(failureMessages);
        log.info("Validating order with id: {}", restaurant.getOrderDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info(
                "Order is approved for order id: {}",
                restaurant.getOrderDetail().getId().getValue()
            );
            restaurant.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(restaurant.getOrderApproval(), failureMessages);
        } else {
            log.info(
                "Order is rejected for order id: {}",
                restaurant.getOrderDetail().getId().getValue()
            );
            restaurant.constructOrderApproval(OrderApprovalStatus.REJECTED);
            return new OrderRejectedEvent(restaurant.getOrderApproval(), failureMessages);
        }
    }
}
