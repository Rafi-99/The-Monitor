package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.FunUtility;
import bot.handlers.event.ModerationUtility;

import java.util.List;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class Purge implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

            if(c.getCommandParameters().size() < 1) {
                FunUtility.setEmbed(c.getEvent(), "Message Deletion Usage", "Usage: " + Monitor.prefix + "purge [# of messages]");
            }
            else {
                
                try {
                    int messageCount = Integer.parseInt(c.getCommandParameters().get(0));
                    List<Message> messages = c.getChannel().getHistory().retrievePast(messageCount).complete();
                    c.getChannel().purgeMessages(messages);
                    /*
                     * Notifies user if messages have been successfully deleted.
                     */
                    FunUtility.setEmbed(c.getEvent(), "✅ Success! ✅", "You have successfully deleted " + messageCount + " messages.");
                }
                catch(NumberFormatException n) {
                    FunUtility.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter the number of messages you want to delete.");
                }
                catch (IllegalArgumentException e) {

                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                        /*
                         * Messages >100 API limit error.
                         */
                        FunUtility.setEmbed(c.getEvent(), "❌ Selected Messages Are Out of Range ❌", "Only 1-100 messages can be deleted at a time.");
                    }
                    else {
                        /*
                         * Messages are too old error.
                         */
                        FunUtility.setEmbed(c.getEvent(), "❌ Selected Messages Are Older Than 2 Weeks ❌", "Messages older than 2 weeks cannot be deleted.");
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