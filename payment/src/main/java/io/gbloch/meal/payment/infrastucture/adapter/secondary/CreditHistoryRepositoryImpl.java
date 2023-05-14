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

import io.gbloch.meal.domain.vo.CustomerId;
import io.gbloch.meal.payment.application.port.output.repository.CreditHistoryRepository;
import io.gbloch.meal.payment.domain.entity.CreditHistory;
import io.gbloch.meal.payment.domain.vo.CreditHistoryId;
import io.gbloch.meal.payment.infrastucture.entity.CreditHistoryEntity;
import io.gbloch.meal.payment.infrastucture.mapper.CreditHistoryMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * CreditHistoryRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class CreditHistoryRepositoryImpl
    implements CreditHistoryRepository, PanacheRepository<CreditHistoryEntity> {

    private final CreditHistoryMapper creditHistoryMapper;

    @Override
    public Optional<CreditHistory> save(CreditHistory entity) {
        var creditHistoryEntity = creditHistoryMapper.toCreditHistoryEntity(entity);
        persist(creditHistoryEntity);
        return Optional.of(creditHistoryMapper.toCreditHistory(creditHistoryEntity));
    }

    @Override
    public Optional<CreditHistory> findById(CreditHistoryId id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CreditHistory> findByCustomerId(CustomerId customerId) {
        return find("customerId", customerId)
            .stream().map(creditHistoryMapper::toCreditHistory).toList();
    }
}
