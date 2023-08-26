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

package io.gbloch.meal.core;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;

/**
 * PostgresJdbcParserUtils.
 *
 * @author Gaëtan Bloch
 * <br>Created on 15/05/2023
 */
@Getter
@ToString
@Slf4j
public final class PostgresJdbcParser {

    private String host;
    private String port;
    private String database;

    public static PostgresJdbcParser parse(String jdbcUrl) {
        PostgresJdbcParser parser = new PostgresJdbcParser();

        int pos, pos1, pos2;
        String connUri;

        if (jdbcUrl == null || !jdbcUrl.startsWith("jdbc:")
            || (pos1 = jdbcUrl.indexOf(':', 5)) == -1)
            throw new IllegalArgumentException("Invalid JDBC url.");

        if ((pos2 = jdbcUrl.indexOf(';', pos1)) == -1) {
            connUri = jdbcUrl.substring(pos1 + 1);
        } else {
            connUri = jdbcUrl.substring(pos1 + 1, pos2);
        }

        if (connUri.startsWith("//")) {
            if ((pos = connUri.indexOf('/', 2)) != -1) {
                parser.host = connUri.substring(2, pos);
                parser.database = removePath(connUri.substring(pos + 1));
                log.error("parser.database: {}", parser.database);

                if ((pos = parser.host.indexOf(':')) != -1) {
                    parser.port = parser.host.substring(pos + 1);
                    parser.host = parser.host.substring(0, pos);
                }
            }
        } else {
            parser.database = removePath(connUri);
            log.error("parser.database: {}", parser.database);
        }

        return parser;
    }

    private static String removePath(String jdbcUrl) {
        int pos = jdbcUrl.indexOf('?');
        return pos == -1 ? jdbcUrl : jdbcUrl.substring(0, pos);
    }
}
