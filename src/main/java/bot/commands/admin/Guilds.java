package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

public class Guilds implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals("398215411998654466")) {
            System.out.println("Total # of Guilds: "+ c.getJDA().getGuilds().size());
            for (int i = 0; i < Monitor.myBot.getGuilds().size(); i++) {
                System.out.println(i + 1 +". "+ Monitor.myBot.getGuilds().get(i).getName());
            }
        }
    }

    @Override
    public String getName() {
        return "guilds";
    }
}