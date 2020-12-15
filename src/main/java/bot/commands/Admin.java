package bot.commands;

import bot.driver.Monitor;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Admin extends ListenerAdapter {
     boolean stop = false;

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          
          String [] admin = event.getMessage().getContentRaw().split("\\s+"); 

          if(admin[0].equalsIgnoreCase(Monitor.prefix + "test") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               ScheduledExecutorService test = Executors.newSingleThreadScheduledExecutor();
               test.scheduleAtFixedRate(() -> {
                    event.getChannel().sendMessage("Poggers!").queue();
                    if(stop) {
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage("Ending spam now...").queue();
                         event.getChannel().sendMessage("Ended.").queue();
                         test.shutdownNow();
                         stop = false;
                    }
               }, 0, 1, TimeUnit.SECONDS);
          } 

          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "stopTest") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               stop = true;
          }

          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "admin") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               event.getChannel().sendMessage("The information has been sent to your DM!").queue();
               event.getAuthor().openPrivateChannel().queue(privateChannel -> {
                    EmbedBuilder adminInfo = new EmbedBuilder();
                    adminInfo.setColor(0x05055e);
                    adminInfo.setTitle("Admin");
                    adminInfo.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    adminInfo.setDescription("Commands available for your usage: \n ``` test \n stopTest \n admin \n restart \n link \n guilds ```");
                    privateChannel.sendMessage(adminInfo.build()).queue();
                    adminInfo.clear();
                    privateChannel.close().queue();
               });
          }

          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "restart") && admin.length == 1) {
               if(event.getAuthor().getId().equals("398215411998654466") || event.getAuthor().getId().equals("658118412098076682")) {
                    event.getChannel().sendTyping().complete();
                    event.getChannel().sendMessage("Terminating...").complete();
                    event.getChannel().sendMessage("Bot is now going offline and restarting.").complete(); 
                    System.exit(0);
               }
               else {
                    event.getChannel().sendMessage("Access denied.").queue();
               }
          }
          
          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "link") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               event.getChannel().sendTyping().queue();
               event.getChannel().sendMessage(Monitor.myBot.getInviteUrl(Permission.ADMINISTRATOR)).queue();
          }
          
          else if(admin[0].equalsIgnoreCase(Monitor.prefix + "guilds") && event.getAuthor().getId().equals("398215411998654466") && admin.length == 1) {
               event.getChannel().sendMessage(Monitor.myBot.getGuilds().toString()).queue();
          }

          // Automated link spam deletion in Goddess's Parthenon 
          else if(event.getMessage().getContentRaw().contains("https://") || event.getMessage().getContentRaw().contains("http://")) {
               Role staff = event.getGuild().getRoleById("710398399085805599");
               if(event.getChannel().getId().equals("709259200651591747") && !Objects.requireNonNull(event.getMember()).getRoles().contains(staff)) {
                    event.getMessage().delete().complete(); 
               }
          }

          // Automated discord link spam deletion in Goddess's Parthenon 
          else if(event.getMessage().getContentRaw().contains("https://discord.gg/") && event.getGuild().getName().equals("The Goddess's Parthenon")) {
               event.getMessage().delete().complete();
          }
     }
}