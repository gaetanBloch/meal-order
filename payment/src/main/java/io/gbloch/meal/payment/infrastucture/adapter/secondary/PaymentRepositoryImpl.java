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

package io.gbloch.meal.payment.infrastucture.adapter.secondary;

import io.gbloch.meal.domain.vo.OrderId;
import io.gbloch.meal.payment.application.port.output.repository.PaymentRepository;
import io.gbloch.meal.payment.domain.entity.Payment;
import io.gbloch.meal.payment.domain.vo.PaymentId;
import io.gbloch.meal.payment.infrastucture.entity.PaymentEntity;
import io.gbloch.meal.payment.infrastucture.mapper.PaymentMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * PaymentRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class PaymentRepositoryImpl
    implements PaymentRepository, PanacheRepository<PaymentEntity> {

    private final PaymentMapper paymentMapper;

    @Override
    public Optional<Payment> save(Payment entity) {
        var paymentEntity = paymentMapper.toPaymentEntity(entity);
        persist(paymentEntity);
        return Optional.of(paymentMapper.toPayment(paymentEntity));
    }

    @Override
    public Optional<Payment> findById(PaymentId id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Payment> findByOrderId(OrderId orderId) {
        return find("orderId", orderId).firstResultOptional().map(paymentMapper::toPayment);
    }
}
