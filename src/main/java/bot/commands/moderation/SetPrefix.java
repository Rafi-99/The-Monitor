package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.database.DataSource;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.Permission;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetPrefix implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MANAGE_SERVER)) {

            if(c.getCommandParameters().size() == 1) {
                final String NEW_PREFIX = c.getCommandParameters().get(0);
                updatePrefix(c.getGuild().getIdLong(), NEW_PREFIX);
                Constants.setEmbed(c.getEvent(), "✅ Success! ✅", "The prefix has now been set to " + NEW_PREFIX);
            }
            else {
                Constants.setEmbed(c.getEvent(), "Prefix Command Usage", "Usage: " + Constants.getCurrentPrefix(c) + "setPrefix [prefix]");
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "setPrefix";
    }

    private void updatePrefix(long guildId, String prefix) {
        Constants.PREFIXES.put(guildId, prefix);

        try (final PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement("UPDATE guild_settings SET prefix = ? WHERE guild_id = ?")) {
            preparedStatement.setString(1, prefix);
            preparedStatement.setString(2, String.valueOf(guildId));
            preparedStatement.executeUpdate();
        } 
        catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}