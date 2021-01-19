package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.event.FunUtility;

public class Ping implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            c.getChannel().sendTyping().queue();
            c.getJDA().getRestPing().queue((ping) -> FunUtility.setEmbed(c.getEvent(), "Ping 📶", "Bot Latency: "+ ping +" ms \nDiscord API Latency: "+ c.getJDA().getGatewayPing() +" ms"));
        }
    }

    @Override
    public String getName() {
        return "ping";
    }
}