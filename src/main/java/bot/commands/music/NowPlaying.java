package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.GuildMusicManager;
import bot.handlers.music.PlayerManager;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.managers.AudioManager;

public class NowPlaying implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        AudioManager manager = c.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(c.getGuild());

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == manager.getConnectedChannel() && manager.isConnected()) {

                if(guildMusicManager.player.getPlayingTrack() == null) {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Nothing is being played currently.").queue();
                    return;
                }
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Currently playing: "+ guildMusicManager.player.getPlayingTrack().getInfo().title).queue();
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to see what's playing currently.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "np";
    }
}