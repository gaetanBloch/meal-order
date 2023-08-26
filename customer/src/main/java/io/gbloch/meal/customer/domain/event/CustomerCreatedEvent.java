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

package io.gbloch.meal.customer.domain.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.gbloch.meal.customer.domain.entity.Customer;
import io.gbloch.meal.domain.entity.AggregateType;
import io.gbloch.meal.domain.event.DomainEvent;
import io.gbloch.meal.domain.event.EventHeader;
import io.gbloch.meal.domain.event.EventType;
import io.gbloch.meal.domain.vo.CustomerId;
import java.time.Instant;
import java.util.Map;

/**
 * CustomerCreatedEvent.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
public final class CustomerCreatedEvent extends DomainEvent<CustomerId, Customer> {

    private CustomerCreatedEvent(CustomerId id, JsonNode payload) {
        super(
            EventHeader.of(),
            id,
            Instant.now(),
            payload
        );
    }

    public static CustomerCreatedEvent of(Customer customer) {
//        ObjectNode asJson = mapper.convertValue(customer, ObjectNode.class);
        ObjectNode asJson = mapper.createObjectNode()
            .put("id", customer.getId().getValue().toString())
            .put("userName", customer.getIdentity().userName())
            .put("firstName", customer.getIdentity().firstName())
            .put("lastName", customer.getIdentity().lastName());
        return new CustomerCreatedEvent(customer.getId(), asJson);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(this.id.getValue());
    }

    @Override
    public String getAggregateType() {
        return AggregateType.CUSTOMER.name();
    }

    @Override
    public String getType() {
        return EventType.CUSTOMER_CREATED.name();
    }

    @Override
    public JsonNode getPayload() {
        return payload;
    }

    @Override
    public Map<String, Object> getAdditionalFieldValues() {
        return super.getAdditionalFieldValues();
    }
}
