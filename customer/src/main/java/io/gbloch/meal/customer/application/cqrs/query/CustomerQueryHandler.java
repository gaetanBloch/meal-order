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

package io.gbloch.meal.customer.application.cqrs.query;

import io.gbloch.meal.application.error.ApplicationException;
import io.gbloch.meal.customer.application.dto.GetCustomerResponse;
import io.gbloch.meal.customer.application.error.CustomerApplicationException;
import io.gbloch.meal.customer.application.mapper.CustomerMapper;
import io.gbloch.meal.customer.application.port.input.GetCustomerUseCase;
import io.gbloch.meal.customer.application.port.output.repository.CustomerRepository;
import io.gbloch.meal.domain.vo.CustomerId;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * CustomerQueryHandler.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class CustomerQueryHandler implements GetCustomerUseCase {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public GetCustomerResponse getCustomer(UUID customerId) {
        return customerRepository
            .findById(new CustomerId(customerId))
            .map(customerMapper::toGetCustomerResponse)
            .orElseThrow(() ->
                new CustomerApplicationException("Customer with id " + customerId + " not found")
            );
    }
}
