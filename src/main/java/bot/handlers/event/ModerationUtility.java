package bot.handlers.event;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ModerationUtility {

    public static void accessDenied(GuildMessageReceivedEvent event) {
        FunUtility.setEmbed(event, "❌ Access Denied! ❌", "Sorry, you don't have the required permissions to use this command.");
    }
}