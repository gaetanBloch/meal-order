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

package io.gbloch.meal.domain.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.gbloch.meal.domain.vo.IdBase;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * DomainEvent.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Setter
@Getter
@RequiredArgsConstructor
public abstract class DomainEvent<ID extends IdBase<UUID>, T> implements ExportedEvent<String, JsonNode> {

    protected static ObjectMapper mapper = new ObjectMapper();

    protected final EventHeader header;
    protected final ID id;
    protected final Instant timestamp;
    protected final JsonNode payload;

    protected T payloadObject;
}
