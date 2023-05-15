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

package io.gbloch.meal.customer.infrastucture.adapter.secondary;

import io.gbloch.meal.customer.application.port.output.repository.CustomerRepository;
import io.gbloch.meal.customer.domain.entity.Customer;
import io.gbloch.meal.customer.infrastucture.entity.CustomerEntity;
import io.gbloch.meal.customer.infrastucture.mapper.CustomerMapper;
import io.gbloch.meal.domain.vo.CustomerId;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CustomerRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public final class CustomerRepositoryImpl
    implements CustomerRepository, PanacheRepository<CustomerEntity> {

    private final CustomerMapper mapper;

    @Override
    public Optional<Customer> save(Customer entity) {
        var customerEntity = mapper.toCustomerEntity(entity);
        log.info("Saving customer: {}", customerEntity);
        persist(customerEntity);
        Customer customer = mapper.toCustomer(customerEntity);
        log.info("Saved customer: {}", customer);
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return find("id", id.getValue()).firstResultOptional().map(mapper::toCustomer);
    }
}
