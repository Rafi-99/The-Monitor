package bot.handlers.music;

import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicUtility {

    public static GuildMessageReceivedEvent event;
    public static AudioManager guildAudioManager = event.getGuild().getAudioManager();
    public static GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
    public static BlockingQueue<AudioTrack> playerQueue = PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue(); 

}