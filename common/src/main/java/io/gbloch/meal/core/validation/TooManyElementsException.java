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
public final class TooManyElementsException extends ValidationException {

    @Serial
    private static final long serialVersionUID = 5701103294043314611L;

    private final String maxSize;
    private final String currentSize;

    public TooManyElementsException(TooManyElementsExceptionBuilder builder) {
        super(builder.field, builder.message());
        maxSize = String.valueOf(builder.maxSize);
        currentSize = String.valueOf(builder.size);
    }

    public static TooManyElementsExceptionBuilder builder() {
        return new TooManyElementsExceptionBuilder();
    }

    @Override
    public ValidationErrorType type() {
        return ValidationErrorType.TOO_MANY_ELEMENTS;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("maxSize", maxSize, "currentSize", currentSize);
    }

    public static class TooManyElementsExceptionBuilder {

        private String field;
        private int maxSize;
        private int size;

        public TooManyElementsExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        public TooManyElementsExceptionBuilder maxSize(int maxSize) {
            this.maxSize = maxSize;

            return this;
        }

        public TooManyElementsExceptionBuilder size(int size) {
            this.size = size;

            return this;
        }

        private String message() {
            return (
                "Size of collection \"" +
                field +
                "\" must be at most " +
                maxSize +
                " but was " +
                size
            );
        }

        public TooManyElementsException build() {
            return new TooManyElementsException(this);
        }
    }
}
