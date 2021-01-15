package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

public class Restart implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {

            if(c.getMember().getId().equals("398215411998654466") || c.getMember().getId().equals("658118412098076682")) {
                c.getChannel().sendTyping().complete();
                c.getChannel().sendMessage("Terminating...").complete();
                c.getChannel().sendMessage("Bot is now going offline and restarting.").complete();
                System.exit(0);
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Access denied.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "restart";
    }
}