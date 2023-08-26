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

package io.gbloch.meal.customer.application.cqrs.command;

import io.gbloch.meal.customer.application.dto.CreateCustomerCommand;
import io.gbloch.meal.customer.application.dto.CreateCustomerResponse;
import io.gbloch.meal.customer.application.mapper.CustomerMapper;
import io.gbloch.meal.customer.application.port.input.CreateCustomerUseCase;
import io.gbloch.meal.customer.application.port.output.repository.CustomerRepository;
import io.gbloch.meal.customer.domain.entity.Customer;
import io.gbloch.meal.customer.domain.error.CustomerDomainException;
import io.gbloch.meal.customer.domain.event.CustomerCreatedEvent;
import io.gbloch.meal.customer.domain.service.CustomerDomainService;
import io.gbloch.meal.domain.vo.CustomerId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
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
final class CustomerCommandHandler implements CreateCustomerUseCase {

    private final CustomerDomainService customerDomainService;

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CreateCustomerResponse createCustomer(CreateCustomerCommand createCustomerCommand) {
        log.info("Received CreateCustomerCommand: {}", createCustomerCommand);
        Customer customer = customerMapper.toCustomer(createCustomerCommand);
        customer.setId(new CustomerId(UUID.randomUUID()));
        Customer savedCustomer = customerRepository
            .save(customer)
            .orElseThrow(() ->
                new CustomerDomainException(
                    "Could not save customer with id " + customer.getId()
                )
            );
        // Persist the event to Outbox table
        customerDomainService.createCustomer(savedCustomer);
        return customerMapper.toCreateCustomerResponse(
            savedCustomer,
            "Customer created successfully"
        );
    }
}
