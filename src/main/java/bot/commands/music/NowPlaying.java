package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class NowPlaying implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.getAudioManager(c.getEvent()).getConnectedChannel() && MusicUtility.getAudioManager(c.getEvent()).isConnected()) {

                if(MusicUtility.getMusicManager(c.getEvent()).player.getPlayingTrack() == null) {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Nothing is being played currently.").queue();
                    return;
                }
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Currently playing: "+ MusicUtility.getMusicManager(c.getEvent()).player.getPlayingTrack().getInfo().title).queue();
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