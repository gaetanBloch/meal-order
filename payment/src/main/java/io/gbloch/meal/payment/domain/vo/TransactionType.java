package io.gbloch.meal.payment.domain.vo;

import io.gbloch.meal.core.annotations.ddd.ValueObject;

@ValueObject
public enum TransactionType {
    DEBIT,
    CREDIT,
}
