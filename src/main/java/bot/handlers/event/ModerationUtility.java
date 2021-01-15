package bot.handlers.event;

import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ModerationUtility {

    public static void accessDenied(GuildMessageReceivedEvent event) {
        EmbedBuilder denied = new EmbedBuilder();
        denied.setColor(0x05055e);
        denied.setTitle("❌ Access Denied! ❌");
        denied.setDescription("Sorry, you don't have the required permissions to use this command.");
        denied.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(denied.build()).queue();
        denied.clear();
    }
}