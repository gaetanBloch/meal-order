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

package io.gbloch.meal.infrastructure.outbox;

import static io.debezium.data.Envelope.FieldName.*;
import static io.debezium.data.Envelope.Operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.config.Configuration;
import io.debezium.data.Envelope;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Java;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

/**
 * DebeziumListener.
 *
 * @author Gaëtan Bloch
 * <br>Created on 15/05/2023
 */
@ApplicationScoped
@Slf4j
public final class DebeziumListener {
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Start the Debezium engine in a different thread
    ManagedExecutor executor;

    // Debezium configuration object
    Configuration configuration;
    // Interface to send events to movies Kafka topic
    @Channel("customers")
    Emitter<Record<String, JsonNode>> customersEmitter;
    private DebeziumEngine<RecordChangeEvent<SourceRecord>> engine;

    public DebeziumListener(ManagedExecutor executor, Configuration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    void onStart(@Observes StartupEvent event) {

        // Configures Debezium engine
        this.engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
            .using(this.configuration.asProperties())
            // For each event triggered by Debezium, the handleChangeEvnt method is called
            .notifying(this::handleChangeEvent)
            .build();

        // Starts Debezium in different thread
        this.executor.execute(this.engine);
    }

    void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {

        // For each triggered event, we get the information
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        log.error("Source record: {}", sourceRecord);
        Struct sourceRecordChangeValue = (Struct) sourceRecord.value();
        log.error("Source record change value: {}", sourceRecordChangeValue);

        if (sourceRecordChangeValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

//             Only insert operations are processed
            if (operation == Operation.CREATE) {

                // Get insertion info
                Struct struct = (Struct) sourceRecordChangeValue.get(AFTER);
                log.error("Struct: {}", struct);
                String type = struct.getString("type");
                String payload = struct.getString("payload");
                log.error("Type: {}", type);
                log.error("Payload: {}", payload);
                if ("CUSTOMER_CREATED".equals(type)) {
                    try {
                        final JsonNode payloadJson = objectMapper.readValue(payload, JsonNode.class);
                        String id = payloadJson.get("id").toString();

//                         Populate content to Kafka topic
                        customersEmitter.send(Record.of(id, payloadJson));
                    } catch (JsonProcessingException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }

    }

    void onStop(@Observes ShutdownEvent event) throws IOException {
        if (this.engine != null) {
            this.engine.close();
        }
    }
}
