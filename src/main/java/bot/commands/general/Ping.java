package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

public class Ping implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            c.getJDA().getRestPing().queue((ping) -> Constants.setEmbed(c.getEvent(), "Ping 📶", "Bot Latency: "+ ping +" ms \nDiscord API Latency: "+ c.getJDA().getGatewayPing() +" ms"));
        }
    }

    @Override
    public String getName() {
        return "ping";
    }
}