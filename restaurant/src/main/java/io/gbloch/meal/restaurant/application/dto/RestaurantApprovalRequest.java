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

package io.gbloch.meal.restaurant.application.dto;

import io.gbloch.meal.domain.vo.RestaurantOrderStatus;
import io.gbloch.meal.restaurant.domain.entity.Product;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * RestaurantApprovalRequest.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
@Builder
@Setter
@AllArgsConstructor
public final class RestaurantApprovalRequest {

    private UUID id;
    private UUID restaurantId;
    private UUID orderId;
    private RestaurantOrderStatus restaurantOrderStatus;
    private List<Product> products;
    private BigDecimal price;
    private Instant createdAt;
}
