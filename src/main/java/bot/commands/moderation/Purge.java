package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.FunUtility;
import bot.handlers.event.ModerationUtility;

import net.dv8tion.jda.api.Permission;

public class Purge implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

            if(c.getCommandParameters().isEmpty()) {
                FunUtility.setEmbed(c.getEvent(), "Message Deletion Usage", "Usage: " + Monitor.prefix + "purge [# of messages]");
            }
            else {

                try {

                    int num = Integer.parseInt(c.getCommandParameters().get(0));

                    if(num > 0 && num <= 1000) {
                        c.getChannel().getIterableHistory().takeAsync(num).thenAccept(messages -> {
                            c.getChannel().purgeMessages(messages);
                            FunUtility.setEmbed(c.getEvent(), "✅ Success! ✅", "You have successfully deleted " + messages.size() + " messages.");
                        });
                    }
                    else {
                        FunUtility.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter a number between 1 and 1000.");
                    }
                }
                catch(Exception e) {
                    FunUtility.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter a number between 1 and 1000.");
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "delete";
    }
}