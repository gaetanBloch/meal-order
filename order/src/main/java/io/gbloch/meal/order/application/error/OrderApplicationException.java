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

package io.gbloch.meal.order.application.error;

import io.gbloch.meal.application.error.ApplicationException;
import java.io.Serial;

/**
 * OrderApplicationException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
public final class OrderApplicationException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 8614524790439747716L;

    public OrderApplicationException(String message) {
        super(message);
    }

    public OrderApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
