package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Pause implements CommandInterface {
    
    boolean pause = true;

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {

                if(pause) {
                    Constants.getMusicManager(c).player.setPaused(true);
                    pause = false;
                    Constants.setEmbed(c.getEvent(), "Current State: Not Playing :play_pause:", "Player has been paused.");
                }
                else {
                    Constants.getMusicManager(c).player.setPaused(false);
                    pause = true;
                    Constants.setEmbed(c.getEvent(), "Current State: Playing :play_pause:", "Player has been resumed.");
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to pause the player.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "pause";
    }
}