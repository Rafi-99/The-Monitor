package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.Permission;

public class Purge implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

            if(c.getCommandParameters().isEmpty()) {
                Constants.setEmbed(c.getEvent(), "Message Deletion Usage", "Usage: " + Constants.getCurrentPrefix(c) + "purge [# of messages]");
            }
            else {

                try {

                    int num = Integer.parseInt(c.getCommandParameters().get(0));

                    if(num > 0 && num <= 1000) {
                        c.getChannel().getIterableHistory().takeAsync(num).thenAccept(messages -> {
                            c.getChannel().purgeMessages(messages);
                            Constants.setEmbed(c.getEvent(), "✅ Success! ✅", "You have successfully deleted " + messages.size() + " messages.");
                        });
                    }
                    else {
                        Constants.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter a number between 1 and 1000.");
                    }
                }
                catch(Exception e) {
                    Constants.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter a number between 1 and 1000.");
                }
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "purge";
    }
}