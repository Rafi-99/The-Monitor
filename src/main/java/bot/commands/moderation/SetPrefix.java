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
        if(c.getEvent().getMember().hasPermission(Permission.MANAGE_SERVER)) {

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
