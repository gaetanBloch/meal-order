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
 * DDD AggregateRoot.
 * <p>
 * In Domain-Driven Design (DDD), an aggregate root is a concept used to define a
 * consistency boundary around a group of related objects, known as an aggregate.
 * The aggregate root is the primary object within the aggregate and serves as the single
 * entry point for all operations that can be performed on the objects within the aggregate.
 * All interactions with objects inside the aggregate must be done through the aggregate root.
 * </p>
 * See <a href="https://martinfowler.com/bliki/DDD_Aggregate.html">Definition</a>
 *
 * @author Gaëtan Bloch
 * <br>Created on 08/05/2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AggregateRoot {
}
