package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Leave implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                Constants.getMusicManager(c).scheduler.trackLoop = false;
                Constants.getMusicManager(c).scheduler.getQueue().clear();
                Constants.getMusicManager(c).player.stopTrack();
                Constants.getAudioManager(c).closeAudioConnection();
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Successfully disconnected and cleared the queue!").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me in order to disconnect.").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "leave";
    }
}