package bot.handlers.music;

import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicUtility  {
    
    public static AudioManager getAudioManager(GuildMessageReceivedEvent event) {
        return event.getGuild().getAudioManager();
    }

    public static GuildMusicManager getMusicManager(GuildMessageReceivedEvent event) {
        return PlayerManager.getInstance().getMusicManager(event.getGuild());
    }

    public static BlockingQueue<AudioTrack> getQueue(GuildMessageReceivedEvent event) {
        return PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue();
    }
}