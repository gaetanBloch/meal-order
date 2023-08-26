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

import io.debezium.config.Configuration;
import io.gbloch.meal.core.PostgresJdbcParser;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * DebeziumConfiguration.
 *
 * @author Gaëtan Bloch
 * <br>Created on 15/05/2023
 */
@Dependent
@Slf4j
public final class DebeziumConfiguration {
    // Debezium needs Database URL and credentials to login and
    // monitor transaction logs
    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String url;

    @ConfigProperty(name = "quarkus.datasource.password")
    String password;

    @ConfigProperty(name = "quarkus.datasource.username")
    String username;

    @Produces
    public Configuration configure() {

        PostgresJdbcParser parser = PostgresJdbcParser.parse(url);

        log.warn("parser: {}", parser);
        log.warn("url: {}", url);
        log.warn("username: {}", username);
        log.warn("password: {}", password);


        File fileOffset;
        File fileDbHistory;
        try {
            fileOffset = File.createTempFile("offset", ".dat");
            fileDbHistory = File.createTempFile("dbhistory", ".dat");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Configuration.create()
            .with("name", "customers-postgres-connector")
            .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
            .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
            .with("offset.storage.file.filename", fileOffset.getAbsolutePath())
            .with("offset.flush.interval.ms", 60000)
            .with("database.hostname", parser.getHost())
            .with("database.port", parser.getPort())
            .with("database.user", username)
            .with("database.password", password)
            .with("database.dbname", parser.getDatabase())
//            .with("database.include.list", parser.getDatabase())
//            .with("schema.include.list", "public")
            .with("table.include.list", "public.customers_outbox_events")
            .with("include.schema.changes", "false")
            .with("database.server.name", "dbserver1")
            .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
            .with("database.history.file.filename", fileDbHistory.getAbsolutePath())
            .with("topic.prefix", "meal.")
            .with("plugin.name", "pgoutput")
            .with("tasks.max", "1")
//            .with("transforms", "outbox")
//            .with("transforms.outbox.type", "io.debezium.transforms.outbox.EventRouter")
            .build();
    }
}
