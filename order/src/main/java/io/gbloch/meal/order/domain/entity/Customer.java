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

package io.gbloch.meal.order.domain.entity;

import io.gbloch.meal.core.annotations.ddd.AggregateRoot;
import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.Identity;
import lombok.Getter;

/**
 * Customer.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
@AggregateRoot
public final class Customer extends EntityBase<CustomerId> {
    private final Identity identity;

    private Customer(CustomerId id, Identity identity) {
        super(id);
        this.identity = identity;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder {
        private CustomerId id;
        private Identity identity;

        CustomerBuilder() {
        }

        public CustomerBuilder id(CustomerId id) {
            this.id = id;
            return this;
        }
        public CustomerBuilder identity(Identity identity) {
            this.identity = identity;
            return this;
        }

        public Customer build() {
            return new Customer(this.id, this.identity);
        }
    }
}
