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

package io.gbloch.meal.order.application.cqrs.query;

import io.gbloch.meal.order.application.dto.track.TrackOrderQuery;
import io.gbloch.meal.order.application.dto.track.TrackOrderResponse;
import io.gbloch.meal.order.application.error.OrderApplicationException;
import io.gbloch.meal.order.application.mapper.OrderMapper;
import io.gbloch.meal.order.application.port.input.order.TrackOrderUseCase;
import io.gbloch.meal.order.application.port.output.repository.OrderRepository;
import io.gbloch.meal.order.domain.vo.TrackingId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TrackOrderQueryHandler.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
final class TrackOrderQueryHandler implements TrackOrderUseCase {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public TrackOrderResponse trackOrder(TrackOrderQuery query) {
        var order = orderRepository
            .findByTrackingId(new TrackingId(query.orderTrackingId()))
            .orElseThrow(() ->
                new OrderApplicationException(
                    "Order for tracking ID " + query.orderTrackingId() + " not found"
                )
            );
        return orderMapper.toTrackOrderResponse(order);
    }
}
