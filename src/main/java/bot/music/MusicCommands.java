package bot.music;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import bot.driver.Monitor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommands extends ListenerAdapter {

     private final YouTube youTube;
     boolean pause = true;

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
          AudioManager manager = event.getGuild().getAudioManager();
          BlockingQueue<AudioTrack> playerQueue = PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue();
          String [] commands = event.getMessage().getContentRaw().split("\\s+");

          if(commands[0].equalsIgnoreCase(Monitor.prefix + "join") && event.getMember().hasPermission(Permission.VOICE_CONNECT)) {
               if(!manager.isConnected()) {
                    if(event.getMember().getVoiceState().getChannel() != null) {
                         manager.openAudioConnection(event.getMember().getVoiceState().getChannel());
                         String name = event.getMember().getVoiceState().getChannel().toString().replace("VC:", "");
                         event.getChannel().sendMessage("Successfully connected to: " + name.substring(name.indexOf(""), name.indexOf("("))).queue();
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
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "leave") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && manager.isConnected()) {
               if((event.getMember().getVoiceState().getChannel() != null) && (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel())) {
                    manager.closeAudioConnection();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Successfully disconnected!").queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me in order to disconnect.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "play") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && manager.isConnected()) {
               // make an if-else here using command.length for a how-to embed 
               // m!play returns the how-to
               if((event.getMember().getVoiceState().getChannel() != null) && (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel())) {
                    String link = String.join(" ", commands).replace(commands[0], "");
                    
                    System.out.println(isUrl(link));
                    System.out.println(link);
                    
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
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "clear") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (manager.isConnected()) && commands.length == 1) {
               if ((event.getMember().getVoiceState().getChannel() != null) && (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel())) {
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue().clear();
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).player.stopTrack();
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).player.setPaused(false);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("The queue has been cleared successfully!").queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me to clear the queue.").queue();
               }
          }
          //pause command both pauses the player and resumes it as well 
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "pause") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (manager.isConnected()) && commands.length == 1) {
               if((event.getMember().getVoiceState().getChannel() != null) && (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel())) {
                    if(pause) {
                         PlayerManager.getInstance().getMusicManager(event.getGuild()).player.setPaused(pause);
                         pause = false;
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Player has been paused.").queue();
                    }
                    else {
                         PlayerManager.getInstance().getMusicManager(event.getGuild()).player.setPaused(pause);
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
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "skip") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (manager.isConnected()) && commands.length == 1) {
               if((event.getMember().getVoiceState().getChannel() != null) && (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel())) {
                    if(playerQueue.size() > 0) {
                         PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.nextTrack();
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Track skipped.").queue();
                         event.getChannel().sendMessage("Now playing: " + PlayerManager.getInstance().getMusicManager(event.getGuild()).player.getPlayingTrack().getInfo().title).queue();
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
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "queue") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (manager.isConnected()) && commands.length == 1) {
               if((event.getMember().getVoiceState().getChannel() != null) && (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel())) {
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
               (new java.net.URL(url)).openStream().close();
               return true;
          } catch (IOException e) {
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