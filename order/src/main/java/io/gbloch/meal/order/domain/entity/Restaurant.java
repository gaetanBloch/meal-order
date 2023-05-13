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
import io.gbloch.meal.domain.vo.ActiveType;
import io.gbloch.meal.domain.vo.RestaurantId;
import java.util.List;
import lombok.Getter;

/**
 * Restaurant.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
@AggregateRoot
public final class Restaurant extends EntityBase<RestaurantId> {
    private final List<Product> products;
    private final ActiveType active;

    Restaurant(RestaurantId id, List<Product> products, ActiveType active) {
        super(id);
        this.products = products;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static RestaurantBuilder builder() {
        return new RestaurantBuilder();
    }

    public static class RestaurantBuilder {
        private RestaurantId id;
        private List<Product> products;
        private ActiveType active;

        RestaurantBuilder() {
        }

        public RestaurantBuilder id(RestaurantId id) {
            this.id = id;
            return this;
        }

        public RestaurantBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public RestaurantBuilder active(ActiveType active) {
            this.active = active;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this.id,this.products, this.active);
        }
    }
}
