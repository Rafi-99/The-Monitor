package bot.music;

import bot.driver.Monitor;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommands extends ListenerAdapter {
     boolean pause = true;

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          AudioManager manager = event.getGuild().getAudioManager();
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
                    event.getChannel().sendMessage(Monitor.prefix + "clearQueue").complete();
                    manager.closeAudioConnection();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Successfully disconnected!").queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in the same voice channel as me in order to disconnect.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "play")  && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (commands.length == 2)  && (event.getMember().getVoiceState() != null)) {
               // make an if-else here using command.length for a how-to embed 
               // m!play returns the how-to
               if(manager.isConnected()) {
                    PlayerManager.getInstance().loadAndPlay(event.getChannel(), commands[1]);
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("I have to be in a voice channel first to play anything. Use the join command to summon me.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "clearQueue") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (event.getMember().getVoiceState() != null) && commands.length == 1) {
               if (manager.isConnected()) {
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue().clear();
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).player.stopTrack();
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).player.setPaused(false);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("The queue has been cleared successfully!").queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in a voice channel with me to clear the queue.").queue();
               }
          }
          //pause command both pauses the player and resumes it as well 
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "pause") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (event.getMember().getVoiceState() != null) && commands.length == 1) {
               if(manager.isConnected()) {
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
                    event.getChannel().sendMessage("You have to be in a voice channel with me to pause the player.").queue();
               }
          }
          else if(commands[0].equalsIgnoreCase(Monitor.prefix + "skip") && event.getMember().hasPermission(Permission.VOICE_CONNECT) && (event.getMember().getVoiceState() != null) && commands.length == 1) {
               if(manager.isConnected()) {
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.nextTrack();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Track skipped.").queue();
                    event.getChannel().sendMessage("Now playing: " + PlayerManager.getInstance().getMusicManager(event.getGuild()).player.getPlayingTrack().getInfo().title).queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have to be in a voice channel with me to skip songs.").queue();
               }
          }

          
            
     }
    
}