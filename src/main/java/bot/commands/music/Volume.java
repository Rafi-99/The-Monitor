package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Volume implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(c.getCommandParameters().isEmpty()) {
                Constants.setEmbed(c.getEvent(), "Volume Command Usage 🔊", Constants.getCurrentPrefix(c) +"volume [0-200]"+"\n"+"Current Volume: "+ Constants.getMusicManager(c).player.getVolume() +"%");
            }
            else {

                if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                    
                    try {
                        int playerVolume = Integer.parseInt(c.getCommandParameters().get(0));

                        if(playerVolume >= 0 && playerVolume <=200) {
                            Constants.getMusicManager(c).player.setVolume(playerVolume);
                            c.getChannel().sendTyping().queue();
                            c.getChannel().sendMessage("The player volume has been set to "+ Constants.getMusicManager(c).player.getVolume() +"%").reference(c.getMessage()).mentionRepliedUser(false).queue();
                            return;
                        }
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage("Please enter a number between 0 and 200 inclusive.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                    } 
                    catch (Exception e) {
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage("Please enter a number between 0 and 200 inclusive.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                    }
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("You have to be in the same voice channel as me to change the player volume.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                }
            } 
        }
    }

    @Override
    public String getName() {
        return "volume";
    }
}