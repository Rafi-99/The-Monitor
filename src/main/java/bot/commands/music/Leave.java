package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Leave implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.guildAudioManager.getConnectedChannel() && MusicUtility.guildAudioManager.isConnected()) {
                MusicUtility.guildMusicManager.scheduler.trackLoop = false;
                MusicUtility.guildMusicManager.scheduler.getQueue().clear();
                MusicUtility.guildMusicManager.player.stopTrack();
                MusicUtility.guildAudioManager.closeAudioConnection();
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Successfully disconnected and cleared the queue!").queue();
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me in order to disconnect.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "leave";
    }
}