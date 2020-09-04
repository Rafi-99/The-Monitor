package bot.commands;

import bot.driver.Monitor;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Moderation extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          String [] args = event.getMessage().getContentRaw().split("\\s+");

          if(args[0].equalsIgnoreCase(Monitor.prefix + "ban") || args[0].equalsIgnoreCase(Monitor.prefix + "unban")) {
               if(event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                    if(args[0].equalsIgnoreCase(Monitor.prefix + "ban")) {
                         if(args.length < 2) {
                              EmbedBuilder ban = new EmbedBuilder();
                              ban.setColor(0x05055e);
                              ban.setTitle("Ban Command Usage");
                              ban.setDescription("Usage: "+ Monitor.prefix +"ban [user mention or ID]");
                              ban.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(ban.build()).queue();
                              ban.clear();
                         }
                         else {
                              event.getGuild().ban(args[1].replace("<@!", "").replace(">", ""), 7).queue();                              
                              EmbedBuilder banSuccess = new EmbedBuilder();
                              banSuccess.setColor(0x05055e);
                              banSuccess.setTitle("✅ Success! ✅");
                              banSuccess.setDescription(args[1] +" has been banned successfully!");
                              banSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(banSuccess.build()).queue();
                              banSuccess.clear();
                         }
                    }
                    else if(args[0].equalsIgnoreCase(Monitor.prefix + "unban")) {
                         if(args.length < 2) {
                              EmbedBuilder unban = new EmbedBuilder();
                              unban.setColor(0x05055e);
                              unban.setTitle("Remove Ban Command Usage");
                              unban.setDescription("Usage: "+ Monitor.prefix +"unban [user mention or ID]");
                              unban.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(unban.build()).queue();
                              unban.clear();
                         }
                         else {
                              event.getGuild().unban(args[1].replace("<@!", "").replace(">", "")).queue();                              
                              EmbedBuilder unbanSuccess = new EmbedBuilder();
                              unbanSuccess.setColor(0x05055e);
                              unbanSuccess.setTitle("✅ Success! ✅");
                              unbanSuccess.setDescription(args[1] +" has been unbanned successfully!");
                              unbanSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(unbanSuccess.build()).queue();
                              unbanSuccess.clear();
                         }
                    }
               }
               else {
                    EmbedBuilder denied = new EmbedBuilder();
                    denied.setColor(0x05055e);
                    denied.setTitle("❌ Access Denied! ❌");
                    denied.setDescription("Sorry, you don't have the permissions required to use this command.");
                    denied.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(denied.build()).queue();
                    denied.clear();                             
               }
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "kick")) {
               if(event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
                    if(args.length < 2) {
                         EmbedBuilder kick = new EmbedBuilder();
                         kick.setColor(0x05055e);
                         kick.setTitle("Kick Command Usage");
                         kick.setDescription("Usage: "+ Monitor.prefix +"kick [user mention or ID]");
                         kick.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage(kick.build()).queue();
                         kick.clear();        		 
                    }
                    else {
                         event.getGuild().kick(args[1].replace("<@!", "").replace(">", "")).queue();        	 
                         EmbedBuilder kicked = new EmbedBuilder();
                         kicked.setColor(0x05055e);
                         kicked.setTitle("✅ Success! ✅");
                         kicked.setDescription(args[1] +" has been kicked successfully!");
                         kicked.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage(kicked.build()).queue();
                         kicked.clear();
                    }
               }
               else {
                    EmbedBuilder denied = new EmbedBuilder();
                    denied.setColor(0x05055e);
                    denied.setTitle("❌ Access Denied! ❌");
                    denied.setDescription("Sorry, you don't have the permissions required to use this command.");
                    denied.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(denied.build()).queue();
                    denied.clear();                                                 
               }
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "purge") || args[0].equalsIgnoreCase(Monitor.prefix + "setPrefix") ) {
               if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    if(args[0].equalsIgnoreCase(Monitor.prefix + "purge")) {
                         if(args.length < 2) {            	  
                              EmbedBuilder usage = new EmbedBuilder();
                              usage.setColor(0x05055e);
                              usage.setTitle("Message Deletion Usage");
                              usage.setDescription("Usage: " + Monitor.prefix + "purge [# of messages]");
                              usage.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(usage.build()).queue();
                              usage.clear();
                         }
                         else {                              
                              try {                                   
                                   List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                                   event.getChannel().purgeMessages(messages);
                                  
                                   //Notifies user if messages have been successfully deleted 
                                   EmbedBuilder purgeSuccess = new EmbedBuilder();
                                   purgeSuccess.setColor(0x05055e);
                                   purgeSuccess.setTitle("✅ Success! ✅");
                                   purgeSuccess.setDescription("You have successfully deleted " + args[1] + " messages.");
                                   purgeSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                   event.getChannel().sendTyping().queue();
                                   event.getChannel().sendMessage(purgeSuccess.build()).queue();
                                   purgeSuccess.clear();
                                   //logic of how to delete the embed is below
                                   //event.getChannel().purgeMessages(event.getChannel().getHistory().retrievePast(1).complete());
                              }
                              catch(NumberFormatException n) {            		
                                   EmbedBuilder purgeError = new EmbedBuilder();
                                   purgeError.setColor(0x05055e);
                                   purgeError.setTitle("❌ Invalid Argument ❌");
                                   purgeError.setDescription("Enter a number.");
                                   purgeError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                   event.getChannel().sendTyping().queue();
                                   event.getChannel().sendMessage(purgeError.build()).queue();
                                   purgeError.clear();
                              }
                              catch (IllegalArgumentException e) {                                   
                                   if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {  
                                        // Messages >100 API limit error
                                        EmbedBuilder purgeError = new EmbedBuilder();
                                        purgeError.setColor(0x05055e);
                                        purgeError.setTitle("❌ Selected Messages Are Out of Range ❌");
                                        purgeError.setDescription("Only 1-100 messages can be deleted at a time.");
                                        purgeError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage(purgeError.build()).queue();
                                        purgeError.clear();
                                       } 
                                   else { 
                                        // Messages are too old error
                                        EmbedBuilder purgeError = new EmbedBuilder();
                                        purgeError.setColor(0x05055e);
                                        purgeError.setTitle("❌ Selected Messages Are Older Than 2 Weeks ❌");
                                        purgeError.setDescription("Messages older than 2 weeks cannot be deleted.");
                                        purgeError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage(purgeError.build()).queue();
                                        purgeError.clear();
                                   }                                   
                              }
                              
                         }
                    }
                    else if(args[0].equalsIgnoreCase(Monitor.prefix + "setPrefix")) {
                         if(args.length < 2) {           	  
                              EmbedBuilder prefixInfo = new EmbedBuilder();
                              prefixInfo.setColor(0x05055e);
                              prefixInfo.setTitle("Prefix Command Usage");
                              prefixInfo.setDescription("Usage: " + Monitor.prefix + "setPrefix [prefix]");
                              prefixInfo.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(prefixInfo.build()).queue();
                              prefixInfo.clear();
                         }
                         else {        		 
                              Monitor.prefix = args[1];
                              EmbedBuilder prefixSet = new EmbedBuilder();
                              prefixSet.setColor(0x05055e);
                              prefixSet.setTitle("✅ Success! ✅");
                              prefixSet.setDescription("The prefix has now been set to " + Monitor.prefix);
                              prefixSet.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(prefixSet.build()).queue();
                              prefixSet.clear();                          
                         }                         
                    }
               }
               else {
                    EmbedBuilder denied = new EmbedBuilder();
                    denied.setColor(0x05055e);
                    denied.setTitle("❌ Access Denied! ❌");
                    denied.setDescription("Sorry, you don't have the permissions required to use this command.");
                    denied.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(denied.build()).queue();
                    denied.clear();         
               }
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "createInvite") && args.length == 1) {
               if(event.getMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Invite created! Copy the link below and send it to someone!").queue();
                    event.getChannel().sendMessage(event.getChannel().createInvite().setMaxAge(0).complete().getUrl()).queue();
               }
               else {
                    EmbedBuilder denied = new EmbedBuilder();
                    denied.setColor(0x05055e);
                    denied.setTitle("❌ Access Denied! ❌");
                    denied.setDescription("Sorry, you don't have the permissions required to use this command.");
                    denied.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(denied.build()).queue();
                    denied.clear();                             
               }
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "terminate") && args.length == 1) {
               if(event.getAuthor().getId().equals("398215411998654466") || event.getAuthor().getId().equals("658118412098076682")) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Terminating...").queue();
                    event.getChannel().sendMessage("Goodbye!").complete();
                    System.exit(0);
               }
               else {
                    event.getChannel().sendMessage("Sorry, you are not the bot owner. You can't end me nerd!").queue();
               }
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "link")) {
               if(event.getAuthor().getId().equals("398215411998654466")) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(Monitor.myBot.getInviteUrl(Permission.ADMINISTRATOR)).queue();
               }
               else {
                    event.getChannel().sendMessage("Access Denied.").queue();
               }
          }          
    } 
}