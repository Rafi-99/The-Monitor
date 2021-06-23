/*
 * Copyright 2020 Md Rafi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bot.handlers.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSource.class);

    static {
        try (final Statement statement = getConnection().createStatement()) {
            statement.execute
            ("CREATE TABLE IF NOT EXISTS guild_settings (" +
            "guild_id VARCHAR(20) NOT NULL," +
            "prefix VARCHAR(255) NOT NULL DEFAULT '"+ System.getenv("DEFAULT_PREFIX") +"',"+
            "PRIMARY KEY (guild_id)" +");"
            );

            LOGGER.info("Database loaded successfully!");
        } 
        catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws URISyntaxException, SQLException {
        URI databaseUrl = new URI(System.getenv("DATABASE_URL"));
        String username = databaseUrl.getUserInfo().split(":")[0];
        String password = databaseUrl.getUserInfo().split(":")[1];
        String connection = "jdbc:postgresql://" + databaseUrl.getHost() + ':' + databaseUrl.getPort() + databaseUrl.getPath() + "?sslmode=require";
        
        return DriverManager.getConnection(connection, username, password);
    }
}