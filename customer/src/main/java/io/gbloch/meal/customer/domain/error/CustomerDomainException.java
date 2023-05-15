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

package io.gbloch.meal.customer.domain.error;

import io.gbloch.meal.domain.error.DomainException;
import java.io.Serial;

/**
 * CustomerDomainException.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
public final class CustomerDomainException extends DomainException {

    @Serial
    private static final long serialVersionUID = 5357355200625483846L;

    public CustomerDomainException(String message) {
        super(message);
    }

    public CustomerDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
