package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;

public class SetPrefix implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MANAGE_SERVER)) {

            if(c.getCommandParameters().size() == 1) {
                Monitor.prefix = c.getCommandParameters().get(0);
                Constants.setEmbed(c.getEvent(), "✅ Success! ✅", "The prefix has now been set to " + Monitor.prefix);
                Monitor.myBot.getPresence().setActivity(Activity.playing(Monitor.prefix + "botInfo"));
            }
            else {
                Constants.setEmbed(c.getEvent(), "Prefix Command Usage", "Usage: " + Monitor.prefix + "setPrefix [prefix]");
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
}