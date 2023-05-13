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

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * EventHeader.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Builder
@Setter
@Getter
public final class EventHeader {

    private final UUID eventId;
    private final UUID correlationId;
    private final UUID causationId;
    private final int version;
    private final EventSourceType source;
    private final ZonedDateTime timestamp;

    public static EventHeader of(UUID correlationId, UUID causationId) {
        return EventHeader
            .builder()
            .eventId(UUID.randomUUID())
            .correlationId(correlationId)
            .causationId(causationId)
            .source(EventSourceType.ORDER)
            .version(1)
            .timestamp(ZonedDateTime.now())
            .build();
    }

    public static EventHeader of(UUID correlationId) {
        return EventHeader
            .builder()
            .eventId(UUID.randomUUID())
            .correlationId(correlationId)
            .causationId(correlationId)
            .source(EventSourceType.ORDER)
            .version(1)
            .timestamp(ZonedDateTime.now())
            .build();
    }

    public static EventHeader of() {
        return EventHeader
            .builder()
            .eventId(UUID.randomUUID())
            .correlationId(UUID.randomUUID())
            .causationId(UUID.randomUUID())
            .source(EventSourceType.ORDER)
            .version(1)
            .timestamp(ZonedDateTime.now())
            .build();
    }

    public static EventHeader of(UUID eventId, UUID correlationId, UUID causationId) {
        return EventHeader
            .builder()
            .eventId(eventId)
            .correlationId(correlationId)
            .causationId(causationId)
            .source(EventSourceType.ORDER)
            .version(1)
            .timestamp(ZonedDateTime.now())
            .build();
    }

    public static EventHeader of(ZonedDateTime timestamp) {
        return EventHeader
            .builder()
            .eventId(UUID.randomUUID())
            .correlationId(UUID.randomUUID())
            .causationId(null)
            .source(EventSourceType.ORDER)
            .version(1)
            .timestamp(timestamp)
            .build();
    }
}
