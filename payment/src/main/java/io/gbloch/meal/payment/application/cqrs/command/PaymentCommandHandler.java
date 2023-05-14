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

package io.gbloch.meal.payment.application.cqrs.command;

import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.payment.application.dto.PaymentRequest;
import io.gbloch.meal.payment.application.error.PaymentApplicationException;
import io.gbloch.meal.payment.application.mapper.PaymentMapper;
import io.gbloch.meal.payment.application.port.input.CancelPaymentUseCase;
import io.gbloch.meal.payment.application.port.input.CompletePaymentUseCase;
import io.gbloch.meal.payment.application.port.output.message.publisher.CancelPaymentMessagePublisher;
import io.gbloch.meal.payment.application.port.output.message.publisher.CompletePaymentMessagePublisher;
import io.gbloch.meal.payment.application.port.output.repository.CreditEntryRepository;
import io.gbloch.meal.payment.application.port.output.repository.CreditHistoryRepository;
import io.gbloch.meal.payment.application.port.output.repository.PaymentRepository;
import io.gbloch.meal.payment.domain.entity.CreditEntry;
import io.gbloch.meal.payment.domain.entity.CreditHistory;
import io.gbloch.meal.payment.domain.entity.Payment;
import io.gbloch.meal.payment.domain.event.PaymentEvent;
import io.gbloch.meal.payment.domain.service.PaymentDomainService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PaymentCommandHandler.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
final class PaymentCommandHandler implements CompletePaymentUseCase, CancelPaymentUseCase {

    private final PaymentDomainService paymentDomainService;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final CompletePaymentMessagePublisher completePaymentMessagePublisher;
    private final CancelPaymentMessagePublisher cancelPaymentMessagePublisher;

    @Override
    @Transactional
    public void completePayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentMapper.toPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(
            payment,
            creditEntry,
            creditHistories,
            failureMessages
        );
        // TODO: 13/05/2023 Persist payment event to Outbox table
        persistAll(payment, creditEntry, creditHistories, failureMessages);
    }

    private CreditEntry getCreditEntry(CustomerId customerId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByCustomerId(customerId);
        if (creditEntry.isEmpty()) {
            log.error("Could not find credit entry for customer: {}", customerId.getValue());
            throw new PaymentApplicationException(
                "Could not find credit entry for customer: " + customerId.getValue()
            );
        }
        return creditEntry.get();
    }

    private List<CreditHistory> getCreditHistory(CustomerId customerId) {
        Optional<List<CreditHistory>> creditHistories = creditHistoryRepository.findByCustomerId(
            customerId
        );
        if (creditHistories.isEmpty()) {
            log.error("Could not find credit history for customer: {}", customerId.getValue());
            throw new PaymentApplicationException(
                "Could not find credit history for customer: " + customerId.getValue()
            );
        }
        return creditHistories.get();
    }

    private void persistAll(
        Payment payment,
        CreditEntry creditEntry,
        List<CreditHistory> creditHistories,
        List<String> failureMessages
    ) {
        paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }

    @Override
    @Transactional
    public void cancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment rollback event for order id: {}", paymentRequest.getOrderId());
        Optional<Payment> paymentResponse = paymentRepository.findByOrderId(
            new OrderId(UUID.fromString(paymentRequest.getOrderId()))
        );
        if (paymentResponse.isEmpty()) {
            log.error("Payment with order id: {} could not be found!", paymentRequest.getOrderId());
            throw new PaymentApplicationException(
                "Payment with order id: " + paymentRequest.getOrderId() + " could not be found!"
            );
        }
        Payment payment = paymentResponse.get();
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndCancelPayment(
            payment,
            creditEntry,
            creditHistories,
            failureMessages
        );
        // TODO: 13/05/2023 Persist payment event to Outbox table
        persistAll(payment, creditEntry, creditHistories, failureMessages);
    }
}
