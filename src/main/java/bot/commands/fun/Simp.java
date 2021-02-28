package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

public class Simp implements CommandInterface {
    
    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            Constants.setEmbed(c.getEvent(), "Simps be Simping", c.getCommandParameters().get(0) + " is " + (int) (Math.random() * 101) + "% simp.");
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Constants.getCurrentPrefix(c) +"simp and mention someone to use this command!").reference(c.getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "simp";
    }
}