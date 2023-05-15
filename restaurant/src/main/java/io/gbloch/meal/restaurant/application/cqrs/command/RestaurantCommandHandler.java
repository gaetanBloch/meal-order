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

package io.gbloch.meal.restaurant.application.cqrs.command;

import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.restaurant.application.dto.RestaurantApprovalRequest;
import io.gbloch.meal.restaurant.application.error.RestaurantApplicationException;
import io.gbloch.meal.restaurant.application.mapper.RestaurantMapper;
import io.gbloch.meal.restaurant.application.port.input.ApproveOrderUseCase;
import io.gbloch.meal.restaurant.application.port.output.repository.OrderApprovalRepository;
import io.gbloch.meal.restaurant.application.port.output.repository.RestaurantRepository;
import io.gbloch.meal.restaurant.domain.entity.Restaurant;
import io.gbloch.meal.restaurant.domain.event.OrderApprovalEvent;
import io.gbloch.meal.restaurant.domain.service.RestaurantDomainService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PaymentCommandHandler.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
final class RestaurantCommandHandler implements ApproveOrderUseCase {

    private final RestaurantDomainService restaurantDomainService;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;

    @Transactional
    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info(
            "Processing restaurant approval for order id: {}",
            restaurantApprovalRequest.getOrderId()
        );
        List<String> failureMessages = new ArrayList<>();
        Restaurant restaurant = findRestaurant(restaurantApprovalRequest);
        OrderApprovalEvent orderApprovalEvent = restaurantDomainService.validateOrder(
            restaurant,
            failureMessages
        );
        // TODO: 13/05/2023 Persist order approval event to outbox table
        orderApprovalRepository.save(restaurant.getOrderApproval());
    }

    private Restaurant findRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        Restaurant restaurant = restaurantMapper.toRestaurant(restaurantApprovalRequest);
        Optional<Restaurant> restaurantResult = restaurantRepository.findById(restaurant.getId());
        if (restaurantResult.isEmpty()) {
            log.error("Restaurant with id " + restaurant.getId().getValue() + " not found!");
            throw new RestaurantApplicationException(
                "Restaurant with id " + restaurant.getId().getValue() + " not found!"
            );
        }

        Restaurant restaurantEntity = restaurantResult.get();
        restaurant.setActiveType(restaurantEntity.getActiveType());
        restaurant
            .getOrderDetail()
            .getProducts()
            .forEach(product ->
                restaurantEntity
                    .getOrderDetail()
                    .getProducts()
                    .forEach(p -> {
                        if (p.getId().equals(product.getId())) {
                            product.updateNameAndAvailability(
                                p.getLabel().label(),
                                p.getPrice(),
                                p.getAvailability()
                            );
                        }
                    })
            );
        restaurant.getOrderDetail().setId(new OrderId(restaurantApprovalRequest.getOrderId()));

        return restaurant;
    }
}
