package bot.music;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import bot.driver.Monitor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommands extends ListenerAdapter {
     private final YouTube youTube;

     public MusicCommands() {
          YouTube temp = null;

          try {
               temp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
               .setApplicationName("Monitor Discord Bot")
               .build();
          } catch (Exception e) {
               e.printStackTrace();
          } 
          youTube = temp;
     }
     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          final AudioManager guildAudioManager = event.getGuild().getAudioManager();
          final GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
          final AudioPlayer audioPlayer = guildMusicManager.player;
          VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
          boolean voicePerms = event.getMember().hasPermission(Permission.VOICE_CONNECT);
          boolean pause = true;
          BlockingQueue<AudioTrack> playerQueue = guildMusicManager.scheduler.getQueue();
          String [] commands = event.getMessage().getContentRaw().split("\\s+");

          if(commands[0].equalsIgnoreCase(Monitor.prefix + "join") && voicePerms) {
               if(!guildAudioManager.isConnected()) {
                    if(voiceChannel != null) {
                         guildAudioManager.openAudioConnection(voiceChannel);
                         String channelName = voiceChannel.toString().replace("VC:", "");
                         event.getChannel().sendMessage("Successfully connected to: " + channelName.substring(channelName.indexOf(""), channelName.indexOf("("))).queue();
                    }
                    else {
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("You have to be in a voice channel first!").queue();
                    }
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("I am already connected to a voice channel!").queue();
               }     
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "leave") && voicePerms && guildAudioManager.isConnected()) {
               if((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    guildAudioManager.closeAudioConnection();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Successfully disconnected!").queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me in order to disconnect.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "play") && voicePerms && guildAudioManager.isConnected()) {
               // make an if-else here using command.length for a how-to embed 
               // m!play returns the how-to
               if((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    String link = String.join(" ", commands).replace(commands[0], "");
                    
                    if(!isUrl(link)) {
                         String ytSearch = youtubeSearch(link);
                         if(ytSearch == null) {
                              event.getChannel().sendMessage("Sorry, YouTube returned no results for your query.").queue();
                              return;
                         }
                         link = ytSearch;
                         PlayerManager.getInstance().loadAndPlay(event.getChannel(), link);
                    }
                    else {
                         String formatLink = link.substring(link.indexOf("h"));
                         PlayerManager.getInstance().loadAndPlay(event.getChannel(), formatLink);
                    }   
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to play anything. Use the join command to summon me.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "np") && voicePerms && (guildAudioManager.isConnected()) && commands.length == 1) {
               if((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    if(audioPlayer.getPlayingTrack() == null) {
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Nothing is being played currently.").queue();
                         return;
                    }
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Currently playing: " + audioPlayer.getPlayingTrack().getInfo().title).queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to see what's playing currently.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "clear") && voicePerms && (guildAudioManager.isConnected()) && commands.length == 1) {
               if ((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    guildMusicManager.scheduler.getQueue().clear();
                    audioPlayer.stopTrack();
                    audioPlayer.setPaused(false);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("The queue has been cleared successfully!").queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to clear the queue.").queue();
               }
          }
          //pause command both pauses the player and resumes it as well 
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "pause") && voicePerms && (guildAudioManager.isConnected()) && commands.length == 1) {
               if((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    if(pause) {
                         audioPlayer.setPaused(pause);
                         pause = false;
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Player has been paused.").queue();
                    }
                    else {
                         audioPlayer.setPaused(pause);
                         pause = true;
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Player has been resumed.").queue();
                    }
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to pause the player.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "skip") && voicePerms && (guildAudioManager.isConnected()) && commands.length == 1) {
               if((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    if(playerQueue.size() > 0) {
                         guildMusicManager.scheduler.nextTrack();
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Track skipped.").queue();
                         event.getChannel().sendMessage("Now playing: " + audioPlayer.getPlayingTrack().getInfo().title).queue();
                    }
                    else {
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("There are no songs left in the queue to skip.").queue();
                    }
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to skip songs.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "queue") && voicePerms && (guildAudioManager.isConnected()) && commands.length == 1) {
               if((voiceChannel != null) && (voiceChannel == guildAudioManager.getConnectedChannel())) {
                    if (playerQueue.isEmpty()) {
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("The queue is empty.").queue();
                    }
                    else {
                         int minQueueView = Math.min(playerQueue.size(), 30);
                         List<AudioTrack> tracks = new ArrayList<>(playerQueue);
                         
                         EmbedBuilder queue = new EmbedBuilder();
                         queue.setColor(0x05055e);
                         queue.setTitle("**Current Queue: **" + playerQueue.size() + " **Tracks**");

                         for (int i = 0; i < minQueueView; i++) {
                              AudioTrackInfo trackInfo = tracks.get(i).getInfo();
                              queue.appendDescription(String.format("%s - %s\n", trackInfo.title, trackInfo.author));
                              
                         }

                         queue.setThumbnail(Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                         queue.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage(queue.build()).queue();
                         queue.clear();

                    } 
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to view the queue.").queue();
               }
          }
     }
     private boolean isUrl(String url) {
          try {
               new URL(url).toURI();
               return true;
          } catch (Exception e) {
               return false;
          }
     }
     private String youtubeSearch(String searchInput) {
          try {
               List<SearchResult> result = youTube.search()
               .list("id,snippet")
               .setQ(searchInput)
               .setMaxResults(1L)
               //.setType("video, playlist")
               .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
               .setKey("AIzaSyBHEJQW65A0ZQweGShQFGEM-L2PjzXBR6c")
               .execute()
               .getItems();

               if(!result.isEmpty()) {
                    String videoID = result.get(0).getId().getVideoId();
                    String playlistID = result.get(0).getId().getPlaylistId();

                    if(result.get(0).getId().getKind().equals("youtube#video")) {
                         return "https://www.youtube.com/watch?v=" + videoID;
                    }
                    else if(result.get(0).getId().getKind().equals("youtube#playlist")) {
                         return "https://www.youtube.com/playlist?list=" + playlistID;
                    }
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
          return null;
     }
}