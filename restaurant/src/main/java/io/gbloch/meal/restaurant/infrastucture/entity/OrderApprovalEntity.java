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

package io.gbloch.meal.restaurant.infrastucture.entity;

import io.gbloch.meal.domain.vo.OrderApprovalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PaymentEntity.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_approval")
@Entity
public class OrderApprovalEntity {

    @Id
    private UUID id;

    private UUID restaurantId;
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus status;
}
