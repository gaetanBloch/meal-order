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

package io.gbloch.meal.domain.vo;

import io.gbloch.meal.core.annotations.ddd.ValueObject;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Money.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ValueObject
public record Money(BigDecimal amount) {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public boolean isGreaterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return amount != null && amount.compareTo(money.amount()) > 0;
    }

    public Money add(Money money) {
        return new Money(setScale(amount.add(money.amount())));
    }

    public Money subtract(Money money) {
        return new Money(setScale(amount.subtract(money.amount())));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(amount.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
