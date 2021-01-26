package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.utilities.Constants;

public class Roast implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(c.getCommandParameters().get(0) +" "+ Constants.roasts[(int) (Math.random() * 20)]).queue();
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"roast and mention the person you want to roast!").queue();
        }
    }

    @Override
    public String getName() {
        return "roast";
    }
}