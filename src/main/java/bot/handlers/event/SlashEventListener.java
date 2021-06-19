package bot.handlers.event;

import bot.commands.SlashCommandInterface;
import bot.handlers.command.SlashCommandManager;
import bot.handlers.database.DataSource;
import bot.handlers.utilities.Constants;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashEventListener extends ListenerAdapter {

    private static final Logger botLogger = LoggerFactory.getLogger(SlashEventListener.class);
    private final List<SlashCommandInterface> slashCommandList = new SlashCommandManager().getAllSlashCommands();
    public static String prefix;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        botLogger.info("Slash commands setup successfully!");
        botLogger.info("Loaded {} slash commands!", slashCommandList.size());

        for(int i =0; i< slashCommandList.size(); i++) {
            botLogger.info(i + 1 +". "+ slashCommandList.get(i).name());
        }

        for (SlashCommandInterface slashCommandInterface : slashCommandList) {
            event.getJDA().upsertCommand(slashCommandInterface.name(), slashCommandInterface.description()).queue();
        }
    }

    @Override
    public void onSlashCommand(@Nonnull SlashCommandEvent event) {
        prefix = Constants.PREFIXES.computeIfAbsent(Objects.requireNonNull(event.getGuild()).getIdLong(), this::loadPrefix);

        for (SlashCommandInterface slashCommand : slashCommandList) {
            if (slashCommand.name().equals(event.getName())) {
                slashCommand.execute(event);
            }
        }
    }

    public String loadPrefix(long guildId) {
        try (final PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {
            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("prefix");
                }
            }

            try (final PreparedStatement insertStatement = DataSource.getConnection().prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {
                insertStatement.setString(1, String.valueOf(guildId));
                insertStatement.execute();
            }
        }
        catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return System.getenv("DEFAULT_PREFIX");
    }
}