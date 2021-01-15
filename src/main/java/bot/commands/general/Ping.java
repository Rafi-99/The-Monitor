package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

public class Ping implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            c.getChannel().sendTyping().queue();
            c.getJDA().getRestPing().queue((ping) -> c.getChannel().sendMessage("Bot Latency: "+ ping +" ms | Discord API Latency: "+ c.getJDA().getGatewayPing() +" ms").queue());
        }
    }

    @Override
    public String getName() {
        return "ping";
    }
}