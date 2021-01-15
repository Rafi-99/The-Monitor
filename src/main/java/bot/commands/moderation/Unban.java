package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class Unban implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.BAN_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                EmbedBuilder unban = new EmbedBuilder();
                unban.setColor(0x05055e);
                unban.setTitle("Remove Ban Command Usage");
                unban.setDescription("Usage: "+ Monitor.prefix +"unban [user mention or ID]");
                unban.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(unban.build()).queue();
                unban.clear();
            }
            else {

                try {
                    String [] format = c.getCommandParameters().toArray(new String[0]);
                    String memberID = format[0].replace("<@!", "").replace("<@", "").replace(">", "");
                    c.getGuild().unban(memberID).queue();
                    EmbedBuilder unbanSuccess = new EmbedBuilder();
                    unbanSuccess.setColor(0x05055e);
                    unbanSuccess.setTitle("✅ Success! ✅");
                    unbanSuccess.setDescription("<@" + memberID + ">" + " has been unbanned successfully!");
                    unbanSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(unbanSuccess.build()).queue();
                    unbanSuccess.clear();
                } catch (Exception e) {
                    EmbedBuilder unbanError = new EmbedBuilder();
                    unbanError.setColor(0x05055e);
                    unbanError.setTitle("❌ Invalid Argument ❌");
                    unbanError.setDescription("Users that are no longer in a guild cannot be mentioned. Please try executing the command again with a valid user mention or user ID.");
                    unbanError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(unbanError.build()).queue();
                    unbanError.clear();
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "unban";
    }
}