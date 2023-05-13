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

import io.gbloch.meal.core.validation.Validation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * BaseId.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class IdBase<T> {

    public static final String ID_FIELD = "id";

    @EqualsAndHashCode.Include
    private final T value;

    protected IdBase() {
        this.value = null;
    }

    protected IdBase(T value) {
        Validation.notNull(ID_FIELD, value);
        this.value = value;
    }
}
