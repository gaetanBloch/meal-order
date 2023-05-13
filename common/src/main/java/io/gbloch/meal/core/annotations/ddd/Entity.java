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

package io.gbloch.meal.core.annotations.ddd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DDD Entity.
 *
 * <p>
 * In Domain-Driven Design (DDD), an entity is an object that has a unique identity and
 * is defined by its attributes, rather than its role in a specific use case or operation.
 * Entities are used to represent concepts within a domain that have a distinct
 * and stable identity over time. They are typically modeled as objects with properties
 * and behavior that can change over time, but their identity remains the same.
 * <p/>
 * <p>
 * See <a href="https://bit.ly/3M4XDtW">
 * Definition
 * </a>
 *
 * @author Gaëtan Bloch
 * <br>Created on 08/05/2023
 */
@SuppressWarnings("checkstyle:linelength")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
}
