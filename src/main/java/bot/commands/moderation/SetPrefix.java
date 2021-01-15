package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class SetPrefix implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MANAGE_SERVER)) {

            if(c.getCommandParameters().size() == 1) {
                Monitor.prefix = c.getCommandParameters().get(0);
                EmbedBuilder prefixSet = new EmbedBuilder();
                prefixSet.setColor(0x05055e);
                prefixSet.setTitle("✅ Success! ✅");
                prefixSet.setDescription("The prefix has now been set to " + Monitor.prefix);
                prefixSet.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(prefixSet.build()).queue();
                prefixSet.clear();
            }
            else {
                EmbedBuilder prefixInfo = new EmbedBuilder();
                prefixInfo.setColor(0x05055e);
                prefixInfo.setTitle("Prefix Command Usage");
                prefixInfo.setDescription("Usage: " + Monitor.prefix + "setPrefix [prefix]");
                prefixInfo.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(prefixInfo.build()).queue();
                prefixInfo.clear();
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "setPrefix";
    }
}