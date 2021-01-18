package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.event.FunUtility;

import java.util.List;

import net.dv8tion.jda.api.entities.Emote;

public class Emotes implements CommandInterface {

    @Override
    public void handle(CommandContext c) {

        StringBuilder emotes = new StringBuilder();

        List<Emote> guildEmotes = c.getGuild().getEmoteCache().asList();

        for (Emote emote : guildEmotes) {
            emotes.append(emote.getAsMention());
        }

        FunUtility.setEmbed(c.getEvent(), "Server Emotes", String.valueOf(emotes));
    }

    @Override
    public String getName() {
        return "emotes";
    }
}