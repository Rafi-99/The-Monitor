package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class prefix implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandEvent event) {
        if(event.getChannelType() == ChannelType.TEXT) {
            event.reply("The current prefix is: "+ Constants.PREFIXES.get(event.getGuild().getIdLong())).setEphemeral(true).queue();
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