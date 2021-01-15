package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.GuildMusicManager;
import bot.handlers.music.PlayerManager;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.managers.AudioManager;

public class Leave implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        AudioManager manager = c.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(c.getGuild());

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == manager.getConnectedChannel() && manager.isConnected()) {
                guildMusicManager.scheduler.trackLoop = false;
                guildMusicManager.scheduler.getQueue().clear();
                guildMusicManager.player.stopTrack();
                manager.closeAudioConnection();
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