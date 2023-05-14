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

package io.gbloch.meal.restaurant.domain.entity;

import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.AvailabilityType;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.ProductId;
import io.gbloch.meal.domain.vo.ProductLabel;
import io.gbloch.meal.domain.vo.Quantity;
import lombok.Getter;

/**
 * Product.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
public final class Product extends EntityBase<ProductId> {

    private final Quantity quantity;
    private ProductLabel label;
    private Money price;
    private AvailabilityType availability;

    private Product(
        ProductId id,
        Quantity quantity,
        ProductLabel label,
        Money price,
        AvailabilityType availability
    ) {
        super(id);
        this.quantity = quantity;
        this.label = label;
        this.price = price;
        this.availability = availability;
    }

    public void updateNameAndAvailability(String name, Money price, AvailabilityType available) {
        this.label = new ProductLabel(name);
        this.price = price;
        this.availability = availability;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {

        private ProductId id;
        private Quantity quantity;
        private ProductLabel label;
        private Money price;
        private AvailabilityType availability;

        ProductBuilder() {}

        public ProductBuilder id(ProductId id) {
            this.id = id;
            return this;
        }

        public ProductBuilder quantity(Quantity quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductBuilder label(ProductLabel label) {
            this.label = label;
            return this;
        }

        public ProductBuilder price(Money price) {
            this.price = price;
            return this;
        }

        public ProductBuilder availability(AvailabilityType availability) {
            this.availability = availability;
            return this;
        }

        public Product build() {
            return new Product(this.id, this.quantity, this.label, this.price, this.availability);
        }
    }
}
