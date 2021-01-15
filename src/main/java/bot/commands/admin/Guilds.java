package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

public class Guilds implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals("398215411998654466")) {
            Monitor.myBot.getGuilds().get(0).getName();
            for (int i = 0; i < Monitor.myBot.getGuilds().size(); i++) {
                c.getChannel().sendMessage(Monitor.myBot.getGuilds().get(i).getName()).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "guilds";
    }
}