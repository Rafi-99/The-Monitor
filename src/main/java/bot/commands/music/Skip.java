package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Skip implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.getAudioManager(c).getConnectedChannel() && MusicUtility.getAudioManager(c).isConnected()) {

                if(MusicUtility.getQueue(c).size() > 0) {
                    MusicUtility.getMusicManager(c).scheduler.nextTrack();
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Track skipped.").queue();
                    c.getChannel().sendMessage("Now playing: " + MusicUtility.getMusicManager(c).player.getPlayingTrack().getInfo().title).queue();
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("There are no songs left in the queue to skip.").queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to skip songs.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "skip";
    }
}