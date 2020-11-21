package bot.commands;

import bot.driver.Monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Admin extends ListenerAdapter {
     boolean stop = false;

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          String [] admin = event.getMessage().getContentRaw().split("\\s+"); 

          if(admin[0].equalsIgnoreCase(Monitor.prefix + "test") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               ScheduledExecutorService test = Executors.newSingleThreadScheduledExecutor();
               test.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                         event.getChannel().sendMessage("Poggers!").queue();
                         if (stop) {
                              event.getChannel().sendTyping().complete();
                              event.getChannel().sendMessage("Ending spam now...").complete();
                              event.getChannel().sendMessage("Ended.").complete();
                              test.shutdownNow();
                              stop = false;
                         }
                    }
               }, 0, 1, TimeUnit.SECONDS);
          } 
          else if (admin[0].equalsIgnoreCase(Monitor.prefix + "stopTest") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               stop = true;
          }

          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "testing")) {
               event.getChannel().sendMessage("Sent to DM!").queue();
               event.getAuthor().openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("Hello there!").queue();
                    privateChannel.close().queue();
               });
          }

          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "terminate") && admin.length == 1) {
               if(event.getAuthor().getId().equals("398215411998654466") || event.getAuthor().getId().equals("658118412098076682")) {
                    event.getChannel().sendTyping().complete();
                    event.getChannel().sendMessage("Terminating...").complete();
                    event.getChannel().sendMessage("Bot is now offline.").complete();
                    System.exit(0);
               }
               else {
                    event.getChannel().sendMessage("Sorry, only the bot owner can terminate me.").queue();
               }
          }
          
          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "link") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               event.getChannel().sendTyping().queue();
               event.getChannel().sendMessage(Monitor.myBot.getInviteUrl(Permission.ADMINISTRATOR)).queue();
          }
          
          // Testing automated link spam deletion
          else if(event.getMessage().getContentRaw().startsWith("https://") || event.getMessage().getContentRaw().startsWith("http://")) {
               if(event.getChannel().getId().equals("713310744145428540")) {
                    event.getMessage().delete().queue(); 
               }
          }
     }
}