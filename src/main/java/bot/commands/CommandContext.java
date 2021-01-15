package bot.commands;

import java.util.List;

import me.duncte123.botcommons.commands.ICommandContext;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandContext implements ICommandContext {

    private final GuildMessageReceivedEvent event;
    private final List<String> commandParameters;

    public CommandContext(GuildMessageReceivedEvent event, List<String> commandParameters) {
        this.event = event;
        this.commandParameters = commandParameters;
    }

    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getCommandParameters() {
        return this.commandParameters;
    }
}