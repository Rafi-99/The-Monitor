package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Clear implements CommandInterface {

    @Override
    public void handle(CommandContext c) {

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if (Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.getAudioManager(c).getConnectedChannel() && MusicUtility.getAudioManager(c).isConnected()) {
                MusicUtility.getMusicManager(c).scheduler.getQueue().clear();
                MusicUtility.getMusicManager(c).player.stopTrack();
                MusicUtility.getMusicManager(c).player.setPaused(false);
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("The queue has been cleared successfully!").queue();
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to clear the queue.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "clear";
    }
}