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
import io.gbloch.meal.payment.application.port.output.repository.CreditEntryRepository;
import io.gbloch.meal.payment.domain.entity.CreditEntry;
import io.gbloch.meal.payment.domain.vo.CreditEntryId;
import io.gbloch.meal.payment.infrastucture.entity.CreditEntryEntity;
import io.gbloch.meal.payment.infrastucture.mapper.CreditEntryMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * CreditEntryRepositoryImpl.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
public final class CreditEntryRepositoryImpl implements CreditEntryRepository, PanacheRepository<CreditEntryEntity> {

    private final CreditEntryMapper creditEntryMapper;

    @Override
    public Optional<CreditEntry> save(CreditEntry entity) {
        var creditEntryEntity = this.creditEntryMapper.toCreditEntryEntity(entity);
        this.persist(creditEntryEntity);
        return Optional.of(this.creditEntryMapper.toCreditEntry(creditEntryEntity));
    }

    @Override
    public Optional<CreditEntry> findById(CreditEntryId id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(CustomerId customerId) {
        return this.find("customerId", customerId.getValue())
            .firstResultOptional()
            .map(this.creditEntryMapper::toCreditEntry);
    }
}
