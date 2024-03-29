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

package io.gbloch.meal.customer.infrastucture.mapper;

import io.gbloch.meal.customer.domain.entity.Customer;
import io.gbloch.meal.customer.infrastucture.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * CustomerMapper.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Mapper(componentModel = "cdi")
public interface CustomerMapper {
    @Mapping(target = "identity.userName", source = "userName")
    @Mapping(target = "identity.firstName", source = "firstName")
    @Mapping(target = "identity.lastName", source = "lastName")
    @Mapping(target = "customerId.value", source = "id")
    Customer toCustomer(CustomerEntity customerEntity);

    @Mapping(target = "userName", source = "identity.userName")
    @Mapping(target = "firstName", source = "identity.firstName")
    @Mapping(target = "lastName", source = "identity.lastName")
    @Mapping(target = "id", source = "id.value")
    CustomerEntity toCustomerEntity(Customer customer);
}
