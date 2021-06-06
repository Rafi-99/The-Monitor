package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class ping implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandEvent event) {
        event.getJDA().getRestPing().queue((ping) -> event.reply("Bot Latency: "+ ping +" ms \nDiscord API Latency: "+ event.getJDA().getGatewayPing() +" ms \nAverage Shard Ping: "+ ((JDA) event).getShardManager().getAverageGatewayPing() + " ms").setEphemeral(true).queue());
    }

    @Override
    public String name() {
        return "ping";
    }

    @Override
    public String description() {
        return "displays ping";
    }   
}