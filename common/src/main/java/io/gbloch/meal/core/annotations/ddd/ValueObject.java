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
 * DDD ValueObject.
 * <p>
 * In Domain-Driven Design (DDD), a value object is an immutable object that
 * represents a concept or value within a domain. Unlike entities,
 * which are defined by their identity, value objects are defined by their attributes
 * and have no intrinsic identity of their own. Value objects are typically used to
 * encapsulate simple, self-contained concepts within a domain, such as dates, times,
 * or geographic coordinates.
 * </p>
 * See <a href="https://martinfowler.com/bliki/ValueObject.html">Definition</a>
 *
 * @author Gaëtan Bloch
 * <br>Created on 08/05/2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValueObject {
}
