package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.FunUtility;

public class Simp implements CommandInterface {
    
    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            FunUtility.setEmbed(c.getEvent(), "Simps be Simping", c.getCommandParameters().get(0) + " is " + (int) (Math.random() * 101) + "% simp.");
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"simp and mention someone to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "simp";
    }
}