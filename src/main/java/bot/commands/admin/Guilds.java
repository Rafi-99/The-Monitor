package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

public class Guilds implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals("398215411998654466")) {
            System.out.println("Total # of Guilds: "+ c.getJDA().getGuilds().size());
            for (int i = 0; i < c.getEvent().getJDA().getGuilds().size(); i++) {
                System.out.println(i + 1 +". "+ c.getEvent().getJDA().getGuilds().get(i).getName());
            }
        }
    }

    @Override
    public String getName() {
        return "guilds";
    }
}