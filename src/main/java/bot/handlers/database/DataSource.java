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
            "id SERIAL PRIMARY KEY," +
            "guild_id VARCHAR(20) NOT NULL," +
            "prefix VARCHAR(255) NOT NULL DEFAULT ' "+ System.getenv("DEFAULT_PREFIX") +" ' "+");"
            );

            LOGGER.info("PostgreSQL database table successfully created!");
        } 
        catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        return DriverManager.getConnection(dbUrl, username, password);
    }
}