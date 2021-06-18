package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Objects;

public class help implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandEvent event) {

        if(event.getChannelType().equals(ChannelType.TEXT)) {
            event.getJDA().retrieveApplicationInfo().queue(botOwner -> {
                EmbedBuilder info = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("**The Monitor ™ Bot Information**")
                .setDescription("A multi-purpose Discord server bot in development.")
                .setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .addField("**Current Prefix**", Constants.PREFIXES.get(Objects.requireNonNull(event.getGuild()).getIdLong()), true)
                .addField("**Command Usage Example**", Constants.PREFIXES.get(event.getGuild().getIdLong()) + "botInfo", false)
                .addField("**Moderation**", "setPrefix, ticketSetup, invite, mute, unmute, purge, kick, ban, unban", true)
                .addField("**General**", "botInfo, serverInfo, ping", true)
                .addField("**Fun**", "roast, wholesome, simp, avatar, pp, rps, meme, emotes", true)
                .addField("**Music**", "join, leave, np, play, loopTrack, volume, pause, skip, queue, clear", true)
                .setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                event.replyEmbeds(info.build()).setEphemeral(true).queue();
                info.clear();
            });
        }        
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "basic bot information and help";
    }   
}