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

import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.ProductId;
import io.gbloch.meal.order.domain.vo.ProductLabel;
import lombok.Getter;

/**
 * Product.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
public final class Product extends EntityBase<ProductId> {

    private final ProductLabel label;
    private final Money price;

    private Product(ProductId productId, ProductLabel label, Money price) {
        super(productId);
        this.label = label;
        this.price = price;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static class ProductBuilder {
        private ProductId productId;
        private ProductLabel label;
        private Money price;

        ProductBuilder() {
        }

        public ProductBuilder productId(ProductId productId) {
            this.productId = productId;
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

        public Product build() {
            return new Product(this.productId, this.label, this.price);
        }

        public String toString() {
            return "Product.ProductBuilder(label=" + this.label + ", price=" + this.price + ")";
        }
    }
}
