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

import io.gbloch.meal.domain.vo.RestaurantId;
import io.gbloch.meal.order.application.port.output.repository.RestaurantRepository;
import io.gbloch.meal.order.domain.entity.Restaurant;
import io.gbloch.meal.order.infrastucture.entity.OrderRestaurantEntity;
import io.gbloch.meal.order.infrastucture.mapper.RestaurantMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * RestaurantRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class RestaurantRepositoryImpl
    implements RestaurantRepository, PanacheRepository<OrderRestaurantEntity> {

    private final RestaurantMapper mapper;

    @Override
    public Optional<Restaurant> save(Restaurant entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Restaurant> findById(RestaurantId id) {
        return find("id", id).firstResultOptional().map(mapper::toRestaurant);
    }
}
