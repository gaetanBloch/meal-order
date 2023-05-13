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

package io.gbloch.meal.order.application.message;

import io.gbloch.meal.order.application.dto.message.PaymentResponse;
import io.gbloch.meal.order.application.port.input.payment.PaymentCancelledUseCase;
import io.gbloch.meal.order.application.port.input.payment.PaymentCompletedUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * PaymentResponseMessageListener.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ApplicationScoped
@Slf4j
public final class PaymentMessageListener implements PaymentCancelledUseCase, PaymentCompletedUseCase {

    @Override
    public void paymentCancelled(PaymentResponse response) {}

    @Override
    public void paymentCompleted(PaymentResponse response) {}
}
