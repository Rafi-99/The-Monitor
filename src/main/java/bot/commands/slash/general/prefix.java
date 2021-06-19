package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;
import bot.handlers.event.SlashEventListener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class prefix implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandEvent event) {
        if(event.getChannelType().equals(ChannelType.TEXT)) {
            event.reply("The current prefix is: "+ SlashEventListener.prefix).setEphemeral(true).queue();
        }
    }

    @Override
    public String name() {
        return "prefix";
    }

    @Override
    public String description() {
        return "displays bot prefix";
    }
}