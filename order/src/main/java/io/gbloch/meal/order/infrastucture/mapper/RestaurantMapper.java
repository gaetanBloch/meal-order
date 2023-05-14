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

package io.gbloch.meal.order.infrastucture.mapper;

import io.gbloch.meal.domain.vo.ActiveType;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.ProductLabel;
import io.gbloch.meal.order.domain.entity.Restaurant;
import io.gbloch.meal.order.infrastucture.entity.OrderRestaurantEntity;
import java.math.BigDecimal;
import org.mapstruct.Mapper;

/**
 * RestaurantMapper.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Mapper(componentModel = "cdi")
public interface RestaurantMapper {
    Restaurant toRestaurant(OrderRestaurantEntity entity);

    ProductLabel map(String label);

    Money map(BigDecimal amount);

    default ActiveType map(boolean active) {
        return ActiveType.mapBoolean(active);
    }
}
