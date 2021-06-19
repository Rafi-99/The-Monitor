package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;
import bot.handlers.database.DataSource;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class prefix implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandEvent event) {
        String prefix = Constants.PREFIXES.computeIfAbsent(event.getGuild().getIdLong(), this::loadPrefix);

        if(event.getChannelType().equals(ChannelType.TEXT)) {
            event.reply("The current prefix is: "+ prefix).setEphemeral(true).queue();
        }
    }

    @Override
    public String name() {
        return "prefix";
    }

    @Override
    public String description() {
        return "displays bot prefix";
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