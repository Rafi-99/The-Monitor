package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.utilities.Constants;

public class RockPaperScissors implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        int rps = (int) (Math.random() * 3) + 1;

        if(c.getCommandParameters().size() == 1) {
            Constants.rpsGame(c.getEvent(), c.getCommandParameters().toArray(new String[0]), rps);
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"rps [rock/paper/scissors] to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "rps";
    }
}