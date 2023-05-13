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
import java.time.Instant;

/**
 * NotAfterTimeException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
public final class NotAfterTimeException extends ValidationException {

    @Serial
    private static final long serialVersionUID = -8510856851882442522L;

    private NotAfterTimeException(String field, String message) {
        super(field, message);
    }

    public static NotAfterTimeException notStrictlyAfter(String fieldName, Instant other) {
        return new NotAfterTimeException(
            fieldName,
            message(fieldName, "must be strictly after", other)
        );
    }

    public static NotAfterTimeException notAfter(String fieldName, Instant other) {
        return new NotAfterTimeException(fieldName, message(fieldName, "must be after", other));
    }

    private static String message(String fieldName, String hint, Instant other) {
        return "Time in \"" + fieldName + "\" " + hint + " " + other + " but wasn't";
    }

    @Override
    public ValidationErrorType type() {
        return ValidationErrorType.NOT_AFTER_TIME;
    }
}
