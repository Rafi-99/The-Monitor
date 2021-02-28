package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Clear implements CommandInterface {

    @Override
    public void handle(CommandContext c) {

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if (Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                Constants.getMusicManager(c).scheduler.getQueue().clear();
                Constants.getMusicManager(c).player.stopTrack();
                Constants.getMusicManager(c).player.setPaused(false);
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("The queue has been cleared successfully!").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to clear the queue.").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "clear";
    }
}