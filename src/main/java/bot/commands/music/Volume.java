package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Volume implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1 && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {
            
            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                
                try {
                    int playerVolume = Integer.parseInt(c.getCommandParameters().get(0));

                    if(playerVolume >= 0 && playerVolume <=200) {
                        Constants.getMusicManager(c).player.setVolume(playerVolume);
                    }
                } 
                catch (Exception e) {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Please enter a number between 0 and 200 inclusive.").queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to change the player volume.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "volume";
    }
    
}