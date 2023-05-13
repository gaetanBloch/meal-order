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
 * ValidationTest.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
@SuppressWarnings({ "NullAway", "hiddenfield" })
public final class StringTooLongException extends ValidationException {

    @Serial
    private static final long serialVersionUID = 1025928168488719258L;

    private final String maxLength;
    private final String currentLength;

    private StringTooLongException(StringTooLongExceptionBuilder builder) {
        super(builder.field, builder.message());
        maxLength = String.valueOf(builder.maxLength);
        currentLength = String.valueOf(builder.value.length());
    }

    public static StringTooLongExceptionBuilder builder() {
        return new StringTooLongExceptionBuilder();
    }

    @Override
    public ValidationErrorType type() {
        return ValidationErrorType.STRING_TOO_LONG;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("maxLength", maxLength, "currentLength", currentLength);
    }

    static final class StringTooLongExceptionBuilder {

        private String value;
        private int maxLength;
        private String field;

        private StringTooLongExceptionBuilder() {}

        StringTooLongExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        StringTooLongExceptionBuilder value(String value) {
            this.value = value;

            return this;
        }

        StringTooLongExceptionBuilder maxLength(int maxLength) {
            this.maxLength = maxLength;

            return this;
        }

        private String message() {
            return (
                "The value in field \"" +
                field +
                "\" must be at most " +
                maxLength +
                " long but was " +
                value.length()
            );
        }

        public StringTooLongException build() {
            return new StringTooLongException(this);
        }
    }
}
