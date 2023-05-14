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
import io.gbloch.meal.payment.domain.vo.CreditHistoryId;
import io.gbloch.meal.payment.domain.vo.TransactionType;
import lombok.Getter;

/**
 * CreditHistory.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@Getter
public final class CreditHistory extends EntityBase<CreditHistoryId> {

    private final CustomerId customerId;

    private final Money amount;

    private final TransactionType transactionType;

    private CreditHistory(
        CreditHistoryId id,
        CustomerId customerId,
        Money amount,
        TransactionType transactionType
    ) {
        super(id);
        this.customerId = customerId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public static CreditHistoryBuilder builder() {
        return new CreditHistoryBuilder();
    }

    public static class CreditHistoryBuilder {

        private CreditHistoryId id;
        private CustomerId customerId;
        private Money amount;
        private TransactionType transactionType;

        CreditHistoryBuilder() {}

        public CreditHistoryBuilder id(CreditHistoryId id) {
            this.id = id;
            return this;
        }

        public CreditHistoryBuilder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public CreditHistoryBuilder amount(Money amount) {
            this.amount = amount;
            return this;
        }

        public CreditHistoryBuilder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this.id, this.customerId, this.amount, this.transactionType);
        }
    }
}
