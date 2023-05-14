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

package io.gbloch.meal.payment.domain.service;

import io.gbloch.meal.domain.vo.Money;
import io.gbloch.meal.domain.vo.PaymentStatus;
import io.gbloch.meal.payment.domain.entity.CreditEntry;
import io.gbloch.meal.payment.domain.entity.CreditHistory;
import io.gbloch.meal.payment.domain.entity.Payment;
import io.gbloch.meal.payment.domain.event.PaymentCancelledEvent;
import io.gbloch.meal.payment.domain.event.PaymentCompletedEvent;
import io.gbloch.meal.payment.domain.event.PaymentEvent;
import io.gbloch.meal.payment.domain.event.PaymentFailedEvent;
import io.gbloch.meal.payment.domain.vo.CreditHistoryId;
import io.gbloch.meal.payment.domain.vo.TransactionType;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * PaymentDomainService.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Slf4j
@ApplicationScoped
public final class PaymentDomainService {

    public PaymentEvent validateAndInitiatePayment(
        Payment payment,
        CreditEntry creditEntry,
        List<CreditHistory> creditHistories,
        List<String> failureMessages
    ) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);

        if (failureMessages.isEmpty()) {
            log.info("Payment is initiated for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment);
        } else {
            log.info("Payment initiation failed for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, failureMessages);
        }
    }

    public PaymentEvent validateAndCancelPayment(
        Payment payment,
        CreditEntry creditEntry,
        List<CreditHistory> creditHistories,
        List<String> failureMessages
    ) {
        payment.validatePayment(failureMessages);
        addCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);

        if (failureMessages.isEmpty()) {
            log.info("Payment is cancelled for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment);
        } else {
            log.info(
                "Payment cancellation failed for order id: {}",
                payment.getOrderId().getValue()
            );
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, failureMessages);
        }
    }

    private void validateCreditEntry(
        Payment payment,
        CreditEntry creditEntry,
        List<String> failureMessages
    ) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error(
                "Customer with id: {} doesn't have enough credit for payment!",
                payment.getCustomerId().getValue()
            );
            failureMessages.add(
                "Customer with id=" +
                    payment.getCustomerId().getValue() +
                    " doesn't have enough credit for payment!"
            );
        }
    }

    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    private void updateCreditHistory(
        Payment payment,
        List<CreditHistory> creditHistories,
        TransactionType transactionType
    ) {
        creditHistories.add(
            CreditHistory
                .builder()
                .id(new CreditHistoryId(UUID.randomUUID()))
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build()
        );
    }

    private void validateCreditHistory(
        CreditEntry creditEntry,
        List<CreditHistory> creditHistories,
        List<String> failureMessages
    ) {
        Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
        Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);

        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            log.error(
                "Customer with id: {} doesn't have enough credit according to credit history",
                creditEntry.getCustomerId().getValue()
            );
            failureMessages.add(
                "Customer with id=" +
                    creditEntry.getCustomerId().getValue() +
                    " doesn't have enough credit according to credit history!"
            );
        }

        if (
            !creditEntry
                .getTotalCreditAmount()
                .equals(totalCreditHistory.subtract(totalDebitHistory))
        ) {
            log.error(
                "Credit history total is not equal to current credit for customer id: {}!",
                creditEntry.getCustomerId().getValue()
            );
            failureMessages.add(
                "Credit history total is not equal to current credit for customer id: " +
                    creditEntry.getCustomerId().getValue() +
                    "!"
            );
        }
    }

    private Money getTotalHistoryAmount(
        List<CreditHistory> creditHistories,
        TransactionType transactionType
    ) {
        return creditHistories
            .stream()
            .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
            .map(CreditHistory::getAmount)
            .reduce(Money.ZERO, Money::add);
    }

    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }
}
