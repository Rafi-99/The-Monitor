package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;

import bot.handlers.utilities.Constants;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Objects;

public class prefix implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandEvent event) {
        if(event.getChannelType().equals(ChannelType.TEXT)) {
            event.reply("The current prefix is: "+ Constants.PREFIXES.get(Objects.requireNonNull(event.getGuild()).getIdLong())).setEphemeral(true).queue();
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