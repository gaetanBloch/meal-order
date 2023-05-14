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

package io.gbloch.meal.payment.domain.entity;

import io.gbloch.meal.domain.entity.EntityBase;
import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.payment.domain.vo.CreditEntryId;
import lombok.Getter;

/**
 * CreditEntry.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
public final class CreditEntry extends EntityBase<CreditEntryId> {

    private final CustomerId customerId;

    private Money totalCreditAmount;

    private CreditEntry(CreditEntryId id, CustomerId customerId, Money totalCreditAmount) {
        super(id);
        this.customerId = customerId;
        this.totalCreditAmount = totalCreditAmount;
    }

    public void addCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    public static CreditEntryBuilder builder() {
        return new CreditEntryBuilder();
    }

    public static class CreditEntryBuilder {

        private CreditEntryId id;
        private CustomerId customerId;
        private Money totalCreditAmount;

        CreditEntryBuilder() {}

        public CreditEntryBuilder id(CreditEntryId id) {
            this.id = id;
            return this;
        }

        public CreditEntryBuilder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public CreditEntryBuilder totalCreditAmount(Money totalCreditAmount) {
            this.totalCreditAmount = totalCreditAmount;
            return this;
        }

        public CreditEntry build() {
            return new CreditEntry(this.id, this.customerId, this.totalCreditAmount);
        }
    }
}
