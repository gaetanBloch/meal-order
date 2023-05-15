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

package io.gbloch.meal.customer.application.port.output.message.publisher;

import io.gbloch.meal.customer.domain.entity.Customer;
import io.gbloch.meal.customer.domain.event.CustomerCreatedEvent;
import io.gbloch.meal.domain.event.DomainEventPublisher;

/**
 * CustomerMessagePublisher.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
public interface CustomerMessagePublisher
    extends DomainEventPublisher<Customer, CustomerCreatedEvent> {}
