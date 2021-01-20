package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Pause implements CommandInterface {
    
    boolean pause = true;

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.getAudioManager(c.getEvent()).getConnectedChannel() && MusicUtility.getAudioManager(c.getEvent()).isConnected()) {

                if(pause) {
                    MusicUtility.getMusicManager(c.getEvent()).player.setPaused(true);
                    pause = false;
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Player has been paused.").queue();
                }
                else {
                    MusicUtility.getMusicManager(c.getEvent()).player.setPaused(false);
                    pause = true;
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Player has been resumed.").queue();
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