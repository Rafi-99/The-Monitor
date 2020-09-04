package bot.music;

import bot.driver.Monitor;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommands extends ListenerAdapter {

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
          
            
     }
    
}