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
import java.util.Map;

/**
 * NumberValueTooLowException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
@SuppressWarnings({ "NullAway", "hiddenfield" })
public final class NumberValueTooLowException extends ValidationException {

    @Serial
    private static final long serialVersionUID = -6048652641079400053L;

    private final String min;
    private final String value;

    private NumberValueTooLowException(NumberValueTooLowExceptionBuilder builder) {
        super(builder.field, builder.message());
        min = builder.minValue;
        value = builder.value;
    }

    public static NumberValueTooLowExceptionBuilder builder() {
        return new NumberValueTooLowExceptionBuilder();
    }

    @Override
    public ValidationErrorType type() {
        return ValidationErrorType.NUMBER_VALUE_TOO_LOW;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("min", min, "value", value);
    }

    public static class NumberValueTooLowExceptionBuilder {

        private String field;
        private String minValue;
        private String value;

        public NumberValueTooLowExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        public NumberValueTooLowExceptionBuilder minValue(String minValue) {
            this.minValue = minValue;

            return this;
        }

        public NumberValueTooLowExceptionBuilder value(String value) {
            this.value = value;

            return this;
        }

        public String message() {
            return (
                "Value of field \"" +
                field +
                "\" must be at least " +
                minValue +
                " but was " +
                value
            );
        }

        public NumberValueTooLowException build() {
            return new NumberValueTooLowException(this);
        }
    }
}
