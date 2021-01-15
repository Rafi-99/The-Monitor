package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class Purge implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

            if(c.getCommandParameters().size() < 1) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0x05055e);
                usage.setTitle("Message Deletion Usage");
                usage.setDescription("Usage: " + Monitor.prefix + "purge [# of messages]");
                usage.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(usage.build()).queue();
                usage.clear();
            }
            else {
                
                try {
                    int messageCount = Integer.parseInt(c.getCommandParameters().get(0));
                    List<Message> messages = c.getChannel().getHistory().retrievePast(messageCount).complete();
                    c.getChannel().purgeMessages(messages);
                    /*
                     * Notifies user if messages have been successfully deleted.
                     */
                    EmbedBuilder purgeSuccess = new EmbedBuilder();
                    purgeSuccess.setColor(0x05055e);
                    purgeSuccess.setTitle("✅ Success! ✅");
                    purgeSuccess.setDescription("You have successfully deleted " + messageCount + " messages.");
                    purgeSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(purgeSuccess.build()).queue();
                    purgeSuccess.clear();
                }
                catch(NumberFormatException n) {
                    EmbedBuilder purgeError = new EmbedBuilder();
                    purgeError.setColor(0x05055e);
                    purgeError.setTitle("❌ Invalid Argument ❌");
                    purgeError.setDescription("Enter a number.");
                    purgeError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(purgeError.build()).queue();
                    purgeError.clear();
                }
                catch (IllegalArgumentException e) {

                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                        /*
                         * Messages >100 API limit error.
                         */
                        EmbedBuilder purgeError = new EmbedBuilder();
                        purgeError.setColor(0x05055e);
                        purgeError.setTitle("❌ Selected Messages Are Out of Range ❌");
                        purgeError.setDescription("Only 1-100 messages can be deleted at a time.");
                        purgeError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(purgeError.build()).queue();
                        purgeError.clear();
                    }
                    else {
                        /*
                         * Messages are too old error.
                         */
                        EmbedBuilder purgeError = new EmbedBuilder();
                        purgeError.setColor(0x05055e);
                        purgeError.setTitle("❌ Selected Messages Are Older Than 2 Weeks ❌");
                        purgeError.setDescription("Messages older than 2 weeks cannot be deleted.");
                        purgeError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(purgeError.build()).queue();
                        purgeError.clear();
                    }
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "purge";
    }
}