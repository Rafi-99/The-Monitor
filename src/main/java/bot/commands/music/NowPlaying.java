package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class NowPlaying implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        
        long currentPositionMillis = Constants.getMusicManager(c).player.getPlayingTrack().getPosition();
        long totalDurationMillis = Constants.getMusicManager(c).player.getPlayingTrack().getDuration();

        long currentPositionMinutes = (currentPositionMillis/1000)/60;
        long currentPositionSeconds = (currentPositionMillis/1000)%60;

        long totalDurationMinutes = (totalDurationMillis/1000)/60;
        long totalDurationSeconds = (totalDurationMillis/1000)%60;

        String message = "Track Progress: "+ currentPositionMinutes +"m "+ currentPositionSeconds +"s/"+ totalDurationMinutes +"m "+ totalDurationSeconds +"s";

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {

                if(Constants.getMusicManager(c).player.getPlayingTrack() == null) {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Nothing is being played currently.").queue();
                    return;
                }
                Constants.setEmbed(c.getEvent(), "Currently Playing: "+ Constants.getMusicManager(c).player.getPlayingTrack().getInfo().title, message);
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