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

package io.gbloch.meal.restaurant.domain.event;

import io.gbloch.meal.restaurant.domain.entity.OrderApproval;
import java.util.List;

/**
 * OrderRejectedEvent.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
public class OrderRejectedEvent extends OrderApprovalEvent {

    public static final String ORDER_REJECTED_EVENT = "OrderRejectedEvent";

    public OrderRejectedEvent(OrderApproval payload, List<String> failureMessages) {
        super(payload, failureMessages, ORDER_REJECTED_EVENT);
    }
}
