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
 * ValidationException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
public abstract class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2193975201953091105L;

    private final String field;

    protected ValidationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public abstract ValidationErrorType type();

    public String field() {
        return field;
    }

    public Map<String, String> parameters() {
        return Map.of();
    }
}
