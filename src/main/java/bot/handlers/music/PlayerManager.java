package bot.handlers.music;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

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

                    if(trackUrl.contains("soundcloud")) {
                         File logo = new File("src\\main\\java\\images\\SoundCloud.jpeg");
                         EmbedBuilder player = new EmbedBuilder()
                         .setColor(0x05055e)
                         .setTitle(track.getInfo().title, track.getInfo().uri)
                         .setThumbnail("attachment://SoundCloud.jpeg")
                         .setDescription("Adding "+ track.getInfo().title +" to the queue.")
                         .setFooter("The Monitor ™ | © 2021", "https://cdn.discordapp.com/avatars/711703852977487903/a7886964b1b5edab5c5d2eb5544a7da9.webp?size=512");
                         channel.sendTyping().queue();
                         channel.sendFile(logo, logo.getName()).embed(player.build()).queue();
                         player.clear();
                    }

                    else {
                         EmbedBuilder player = new EmbedBuilder()
                         .setColor(0x05055e)
                         .setTitle(track.getInfo().title, track.getInfo().uri)
                         .setThumbnail("https://img.youtube.com/vi/"+ track.getInfo().identifier +"/default.jpg")
                         .setDescription("Adding "+ track.getInfo().title +" to the queue.")
                         .setFooter("The Monitor ™ | © 2021", "https://cdn.discordapp.com/avatars/711703852977487903/a7886964b1b5edab5c5d2eb5544a7da9.webp?size=512");
                         channel.sendTyping().queue();
                         channel.sendMessage(player.build()).queue();
                         player.clear();
                    }
               }
               @Override
               public void playlistLoaded(AudioPlaylist playlist) {
                    for (int i = 0; i < playlist.getTracks().size(); i++) {
                         musicManager.scheduler.queue(playlist.getTracks().get(i));
                    }

                    if (trackUrl.contains("soundcloud")) {
                         File logo = new File("src\\main\\java\\images\\SoundCloud.jpeg");
                         EmbedBuilder player = new EmbedBuilder()
                         .setColor(0x05055e)
                         .setTitle(playlist.getName(), trackUrl)
                         .setThumbnail("attachment://SoundCloud.jpeg")
                         .setDescription("Adding " + playlist.getTracks().size() + " tracks from "+ playlist.getName() +" to the queue.")
                         .setFooter("The Monitor ™ | © 2021", "https://cdn.discordapp.com/avatars/711703852977487903/a7886964b1b5edab5c5d2eb5544a7da9.webp?size=512");
                         channel.sendTyping().queue();
                         channel.sendFile(logo, logo.getName()).embed(player.build()).queue();
                         player.clear();
                    }

                    else {
                         EmbedBuilder player = new EmbedBuilder()
                         .setColor(0x05055e)
                         .setTitle(playlist.getName(), trackUrl)
                         .setThumbnail("https://img.youtube.com/vi/"+ playlist.getTracks().get(0).getInfo().identifier +"/default.jpg")
                         .setDescription("Adding " + playlist.getTracks().size() + " tracks from "+ playlist.getName() +" to the queue.")
                         .setFooter("The Monitor ™ | © 2021", "https://cdn.discordapp.com/avatars/711703852977487903/a7886964b1b5edab5c5d2eb5544a7da9.webp?size=512");
                         channel.sendTyping().queue();
                         channel.sendMessage(player.build()).queue();
                         player.clear();
                    }
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
                    channel.sendMessage("Sorry, we have failed to load your request. Please try again. If this problem persists, please contact the developer: Rafi ™#6927").queue();
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