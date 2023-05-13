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

/**
 * ValidationErrorType.
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
public enum ValidationErrorType {
    MISSING_MANDATORY_VALUE,
    NOT_AFTER_TIME,
    NOT_BEFORE_TIME,
    NULL_ELEMENT_IN_COLLECTION,
    NUMBER_VALUE_TOO_HIGH,
    NUMBER_VALUE_TOO_LOW,
    STRING_TOO_LONG,
    STRING_TOO_SHORT,
    TOO_MANY_ELEMENTS,
}
