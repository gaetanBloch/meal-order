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

package io.gbloch.meal.order.infrastucture.adapter.secondary;

import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.order.application.port.output.repository.OrderRepository;
import io.gbloch.meal.order.domain.entity.Order;
import io.gbloch.meal.order.domain.vo.TrackingId;
import io.gbloch.meal.order.infrastucture.entity.OrderEntity;
import io.gbloch.meal.order.infrastucture.mapper.OrderMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * OrderRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class OrderRepositoryImpl implements OrderRepository, PanacheRepository<OrderEntity> {

    private final OrderMapper orderMapper;

    @Override
    public Optional<Order> save(Order order) {
        var orderEntity = this.orderMapper.toOrderEntity(order);
        this.persist(orderEntity);
        return Optional.of(this.orderMapper.toOrder(orderEntity));
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return this.find("id", id.getValue()).firstResultOptional().map(this.orderMapper::toOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return this.find("trackingId", trackingId.getValue())
            .firstResultOptional()
            .map(this.orderMapper::toOrder);
    }
}
