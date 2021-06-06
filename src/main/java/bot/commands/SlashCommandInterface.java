package bot.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public interface SlashCommandInterface {

    void execute(SlashCommandEvent event);

    String name();

    String description();
}