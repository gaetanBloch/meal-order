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

package io.gbloch.meal.order.application.port.output.repository;

import io.gbloch.meal.application.port.output.repository.Repository;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.order.domain.entity.Order;
import io.gbloch.meal.order.domain.vo.TrackingId;
import java.util.Optional;

/**
 * OrderRepository.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
public interface OrderRepository extends Repository<OrderId, Order> {
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
