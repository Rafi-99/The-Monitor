package bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
     private static PlayerManager INSTANCE;
     private final AudioPlayerManager playerManager;
     private final Map<Long, GuildMusicManager> musicManagers;

     private PlayerManager() {
          this.musicManagers = new HashMap<>();
          this.playerManager = new DefaultAudioPlayerManager();
          AudioSourceManagers.registerRemoteSources(playerManager);
          AudioSourceManagers.registerLocalSource(playerManager);
     }

     public GuildMusicManager getMusicManager(Guild guild) {
          return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
               final GuildMusicManager guildMusicManager = new GuildMusicManager(this.playerManager);
               guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
               return guildMusicManager;
          });
     } 

     public void loadAndPlay(TextChannel channel, String trackUrl) {
          final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
          this.playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
               @Override
               public void trackLoaded(AudioTrack track) {
                    musicManager.scheduler.queue(track);
                    channel.sendTyping().queue();
                    channel.sendMessage("Adding "+ track.getInfo().title + " to the queue.").queue();
               }
               @Override
               public void playlistLoaded(AudioPlaylist playlist) {
                    for (int i = 0; i < playlist.getTracks().size(); i++) {
                         musicManager.scheduler.queue(playlist.getTracks().get(i));
                    }
                    channel.sendTyping().queue();
                    channel.sendMessage("Adding " + playlist.getTracks().size() + " tracks from "+ playlist.getName() + " to the queue.").queue();
               }
               @Override
               public void noMatches() {
                    channel.sendTyping().queue();
                    channel.sendMessage("Sorry, no matches were found from your request.").queue();
               }
               @Override
               public void loadFailed(FriendlyException exception) {
                    exception.printStackTrace();
                    channel.sendTyping().queue();
                    channel.sendMessage("Sorry, failed to load your request. Invalid Format.").queue();
               }
          });
     }

     public static PlayerManager getInstance() {
          if(INSTANCE == null) {
               INSTANCE = new PlayerManager();
          }
          return INSTANCE;
     }
}