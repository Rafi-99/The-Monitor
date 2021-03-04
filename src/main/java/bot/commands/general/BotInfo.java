package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.EmbedBuilder;

public class BotInfo implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        /*
         * Code for the bot info command. Lambda is used to create the embed and
         * get the retrieved owner name and avatar inside the footer of the embed.
         */
        if(c.getCommandParameters().isEmpty()) {
            c.getEvent().getJDA().retrieveApplicationInfo().queue(botOwner -> {
                EmbedBuilder info = new EmbedBuilder();
                info.setColor(0x05055e);
                info.setTitle("**The Monitor ™ Bot Information**");
                info.setDescription("A multi-purpose Discord server bot in development.");
                info.setThumbnail(c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                info.addField("**Current Prefix**", Constants.getCurrentPrefix(c), true);
                info.addField("**Command Usage Example**", Constants.getCurrentPrefix(c) + "botInfo", false);
                info.addField("**Moderation**", "setPrefix, ticketSetup, invite, mute, unmute, purge, kick, ban, unban", true);
                info.addField("**General**", "botInfo, serverInfo, ping", true);
                info.addField("**Fun**", "roast, wholesome, simp, avatar, pp, rps, meme, emotes", true);
                info.addField("**Music**", "join, leave, np, play, loopTrack, volume, pause, skip, queue, clear", true);
                info.setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(info.build()).reference(c.getMessage()).mentionRepliedUser(false).queue();
                info.clear();
            });
        }
    }

    @Override
    public String getName() {
        return "botInfo";
    }
}