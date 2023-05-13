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

package io.gbloch.meal.core.validation;

import java.io.Serial;

/**
 * NullElementInCollectionException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
public final class NullElementInCollectionException extends ValidationException {

    @Serial
    private static final long serialVersionUID = -6088407813642420519L;

    public NullElementInCollectionException(String field) {
        super(field, message(field));
    }

    private static String message(String field) {
        return "The field \"" + field + "\" contains a null element";
    }

    @Override
    public ValidationErrorType type() {
        return ValidationErrorType.NULL_ELEMENT_IN_COLLECTION;
    }
}
