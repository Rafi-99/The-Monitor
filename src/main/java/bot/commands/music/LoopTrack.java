package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class LoopTrack implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.guildAudioManager.getConnectedChannel() && MusicUtility.guildAudioManager.isConnected()) {
                final boolean trackRepeat = !MusicUtility.guildMusicManager.scheduler.trackLoop;
                MusicUtility.guildMusicManager.scheduler.trackLoop = trackRepeat;
                c.getChannel().sendTyping().queue();

                if(trackRepeat) {
                    c.getChannel().sendMessage("Looping the current song.").queue();
                }
                else {
                    c.getChannel().sendMessage("Track looping has been disabled").queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to loop a song.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "loopTrack";
    }
}