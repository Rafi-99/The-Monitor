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