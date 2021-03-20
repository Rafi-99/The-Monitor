package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

public class Roast implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(c.getCommandParameters().get(0) +" "+ Constants.ROASTS[(int) (Math.random() * 20)]).reference(c.getMessage()).mentionRepliedUser(false).queue();
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Constants.getCurrentPrefix(c) +"roast and mention the person you want to roast!").reference(c.getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "roast";
    }
}