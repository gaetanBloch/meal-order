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

package io.gbloch.meal.domain.vo;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Id.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Getter
@Setter
@NoArgsConstructor
public final class Id extends IdBase<UUID> {

    private Id(UUID id) {
        super(id);
    }

    public static Id withoutId() {
        return new Id(UUID.randomUUID());
    }

    public static Id withId(UUID id) {
        return new Id(id);
    }
}
