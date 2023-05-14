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

import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.infrastructure.entity.CustomerEntity;
import io.gbloch.meal.order.application.port.output.repository.CustomerRepository;
import io.gbloch.meal.order.domain.entity.Customer;
import io.gbloch.meal.order.infrastucture.mapper.CustomerMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * CustomerRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class CustomerRepositoryImpl
    implements CustomerRepository, PanacheRepository<CustomerEntity> {

    private final CustomerMapper customerMapper;

    @Override
    public Optional<Customer> save(Customer customer) {
        var customerEntity = this.customerMapper.toCustomerEntity(customer);
        this.persist(customerEntity);
        return Optional.of(this.customerMapper.toCustomer(customerEntity));
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return this.find("id", id.getValue()).firstResultOptional().map(customerMapper::toCustomer);
    }
}
