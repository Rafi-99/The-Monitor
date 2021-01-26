package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.utilities.Constants;

public class Wholesome implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            Constants.setEmbed(c.getEvent(), "Such Wholesome", c.getCommandParameters().get(0) + " is " + (int) (Math.random() * 101) + "% wholesome.");
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"wholesome and mention someone to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "wholesome";
    }
}