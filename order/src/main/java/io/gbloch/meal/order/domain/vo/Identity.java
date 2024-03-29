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

package io.gbloch.meal.order.domain.vo;

import io.gbloch.meal.core.annotations.ddd.ValueObject;
import io.gbloch.meal.core.validation.Validation;

/**
 * Identity.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@ValueObject
public record Identity(String userName, String firstName, String lastName) {
    public Identity {
        Validation.field("userName", userName).notBlank().maxLength(255);
        Validation.field("firstName", firstName).notBlank().maxLength(255);
        Validation.field("lastName", lastName).notBlank().maxLength(255);
    }
}
