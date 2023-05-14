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

package io.gbloch.meal.restaurant.infrastucture.mapper;

import io.gbloch.meal.restaurant.domain.entity.OrderApproval;
import io.gbloch.meal.restaurant.domain.entity.Restaurant;
import io.gbloch.meal.restaurant.infrastucture.entity.OrderApprovalEntity;
import io.gbloch.meal.restaurant.infrastucture.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * CreditEntryMapper.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Mapper(componentModel = "cdi")
public interface RestaurantMapper {
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);


    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "restaurantId", source = "restaurantId.value")
    @Mapping(target = "orderId", source = "orderId.value")
    OrderApprovalEntity toOrderApprovalEntity(OrderApproval orderApproval);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "restaurantId.value", source = "restaurantId")
    @Mapping(target = "orderId.value", source = "orderId")
    OrderApproval toOrderApproval(OrderApprovalEntity orderApprovalEntity);
}
