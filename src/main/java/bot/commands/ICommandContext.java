package bot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommandContext {

    MessageReceivedEvent getEvent();

    default Guild getGuild() {
        return this.getEvent().getGuild();
    }
}
