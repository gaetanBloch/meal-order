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

package io.gbloch.meal.order.application.cqrs.command;

import io.gbloch.meal.application.unitofwork.UnitOfWork;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.order.application.dto.create.CreateOrderCommand;
import io.gbloch.meal.order.application.dto.create.CreateOrderResponse;
import io.gbloch.meal.order.application.error.OrderApplicationException;
import io.gbloch.meal.order.application.mapper.OrderMapper;
import io.gbloch.meal.order.application.port.input.order.CreateOrderUseCase;
import io.gbloch.meal.order.application.port.output.repository.CustomerRepository;
import io.gbloch.meal.order.application.port.output.repository.OrderRepository;
import io.gbloch.meal.order.application.port.output.repository.RestaurantRepository;
import io.gbloch.meal.order.domain.entity.Customer;
import io.gbloch.meal.order.domain.entity.Order;
import io.gbloch.meal.order.domain.entity.Restaurant;
import io.gbloch.meal.order.domain.event.OrderCreatedEvent;
import io.gbloch.meal.order.domain.service.OrderDomainService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OrderCommandHandler.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
final class OrderCommandHandler implements CreateOrderUseCase {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderMapper orderMapper;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand command) {
        checkIfCustomerExists(command.customerId());
        var restaurant = getRestaurant(command);
        var order = orderMapper.toOrder(command);
        var orderCreatedEvent = orderDomainService.createOrder(order, restaurant);
        // TODO: 14/05/2023 Persist event to Outbox table
        var savedOrder = saveOrder(order);
        return orderMapper.toOrderResponse(savedOrder, "Order created");
    }

    private Order saveOrder(Order order) {
        var savedOrder = orderRepository
            .save(order)
            .orElseThrow(() ->
                new OrderApplicationException("Could not save order with id " + order.getId())
            );
        log.info("Order with id {} created", savedOrder.getId());
        return savedOrder;
    }

    private void checkIfCustomerExists(UUID uuid) {
        Optional<Customer> customer = customerRepository.findById(new CustomerId(uuid));
        if (customer.isEmpty()) {
            log.warn("Customer with id {} does not exist", uuid);
            throw new OrderApplicationException("Customer with id " + uuid + " does not exist");
        }
    }

    private Restaurant getRestaurant(CreateOrderCommand command) {
        var restaurant = orderMapper.toRestaurant(command);
        return restaurantRepository
            .findById(restaurant.getId())
            .orElseThrow(() ->
                new OrderApplicationException(
                    "Restaurant with id " + restaurant.getId() + " does not exist"
                )
            );
    }
}
