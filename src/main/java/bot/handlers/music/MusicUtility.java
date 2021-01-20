package bot.handlers.music;

import bot.commands.CommandContext;

import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.managers.AudioManager;

public class MusicUtility  {
    
    public static AudioManager getAudioManager(CommandContext c) {
        return c.getGuild().getAudioManager();
    }

    public static GuildMusicManager getMusicManager(CommandContext c) {
        return PlayerManager.getInstance().getMusicManager(c.getGuild());
    }

    public static BlockingQueue<AudioTrack> getQueue(CommandContext c) {
        return PlayerManager.getInstance().getMusicManager(c.getGuild()).scheduler.getQueue();
    }
    
}