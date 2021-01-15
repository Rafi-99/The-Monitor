package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class Kick implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.KICK_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                EmbedBuilder kick = new EmbedBuilder();
                kick.setColor(0x05055e);
                kick.setTitle("Kick Command Usage");
                kick.setDescription("Usage: "+ Monitor.prefix +"kick [user mention or ID]");
                kick.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(kick.build()).queue();
                kick.clear();
            }
            else {

                try {
                    String [] format = c.getCommandParameters().toArray(new String[0]);
                    String memberID = format[0].replace("<@!", "").replace("<@", "").replace(">", "");

                    if(c.getEvent().getGuild().getMemberById(memberID) != null) {
                        c.getEvent().getGuild().kick(memberID).queue();
                        EmbedBuilder kicked = new EmbedBuilder();
                        kicked.setColor(0x05055e);
                        kicked.setTitle("✅ Success! ✅");
                        kicked.setDescription("<@" + c.getCommandParameters().get(0) + ">" + " has been kicked successfully!");
                        kicked.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(kicked.build()).queue();
                        kicked.clear();
                    }
                    else {
                        EmbedBuilder kickError = new EmbedBuilder();
                        kickError.setColor(0x05055e);
                        kickError.setTitle("❌ Error ❌");
                        kickError.setDescription("Users that are no longer in a guild cannot be kicked. Please try executing the command again with a valid user mention or user ID.");
                        kickError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(kickError.build()).queue();
                        kickError.clear();

                    }
                    
                } catch (Exception e) {
                    EmbedBuilder kickError = new EmbedBuilder();
                    kickError.setColor(0x05055e);
                    kickError.setTitle("❌ Invalid Argument ❌");
                    kickError.setDescription("Invalid format. Please try executing the command again with a valid user mention or user ID.");
                    kickError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(kickError.build()).queue();
                    kickError.clear();
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }

    }

    @Override
    public String getName() {
        return "kick";
    }
}