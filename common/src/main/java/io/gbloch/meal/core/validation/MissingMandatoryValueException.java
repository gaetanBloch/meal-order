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
 * MissingMandatoryValueException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
public final class MissingMandatoryValueException extends ValidationException {

    @Serial
    private static final long serialVersionUID = -8156298541773639184L;

    private MissingMandatoryValueException(String field, String message) {
        super(field, message);
    }

    public static MissingMandatoryValueException forBlankValue(String field) {
        return new MissingMandatoryValueException(field, defaultMessage(field, "blank"));
    }

    public static MissingMandatoryValueException forNullValue(String field) {
        return new MissingMandatoryValueException(field, defaultMessage(field, "null"));
    }

    public static MissingMandatoryValueException forEmptyValue(String field) {
        return new MissingMandatoryValueException(field, defaultMessage(field, "empty"));
    }

    private static String defaultMessage(String field, String reason) {
        return "The field \"" + field + "\" is mandatory and wasn't set" + " (" + reason + ")";
    }

    @Override
    public ValidationErrorType type() {
        return ValidationErrorType.MISSING_MANDATORY_VALUE;
    }
}
