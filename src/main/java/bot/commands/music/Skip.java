package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.GuildMusicManager;
import bot.handlers.music.PlayerManager;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.managers.AudioManager;

public class Skip implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        AudioManager manager = c.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(c.getGuild());
        BlockingQueue<AudioTrack> playerQueue = PlayerManager.getInstance().getMusicManager(c.getGuild()).scheduler.getQueue();

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == manager.getConnectedChannel() && manager.isConnected()) {

                if(playerQueue.size() > 0) {
                    guildMusicManager.scheduler.nextTrack();
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Track skipped.").queue();
                    c.getChannel().sendMessage("Now playing: " + guildMusicManager.player.getPlayingTrack().getInfo().title).queue();
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("There are no songs left in the queue to skip.").queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to skip songs.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "skip";
    }
}