package bot.commands;

import bot.driver.Monitor;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Moderation extends ListenerAdapter {

     private void accessDenied(GuildMessageReceivedEvent event) {
          EmbedBuilder denied = new EmbedBuilder();
          denied.setColor(0x05055e);
          denied.setTitle("❌ Access Denied! ❌");
          denied.setDescription("Sorry, you don't have the required permissions to use this command.");
          denied.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
          event.getChannel().sendTyping().queue();
          event.getChannel().sendMessage(denied.build()).queue();
          denied.clear();
     }

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          String [] mod = event.getMessage().getContentRaw().split("\\s+");

          if(mod[0].equalsIgnoreCase(Monitor.prefix + "ban") || mod[0].equalsIgnoreCase(Monitor.prefix + "unban")) {
               if(Objects.requireNonNull(event.getMember()).hasPermission(Permission.BAN_MEMBERS)) {
                    if(mod[0].equalsIgnoreCase(Monitor.prefix + "ban")) {
                         if(mod.length < 2) {
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
                              event.getGuild().ban(mod[1].replace("<@!", "").replace("<@", "").replace(">", ""), 7).queue();                              
                              EmbedBuilder banSuccess = new EmbedBuilder();
                              banSuccess.setColor(0x05055e);
                              banSuccess.setTitle("✅ Success! ✅");
                              banSuccess.setDescription(mod[1] +" has been banned successfully!");
                              banSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(banSuccess.build()).queue();
                              banSuccess.clear();
                         }
                    }
                    else if(mod[0].equalsIgnoreCase(Monitor.prefix + "unban")) {
                         if(mod.length < 2) {
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
                              event.getGuild().unban(mod[1].replace("<@!", "").replace("<@", "").replace(">", "")).queue();                              
                              EmbedBuilder unbanSuccess = new EmbedBuilder();
                              unbanSuccess.setColor(0x05055e);
                              unbanSuccess.setTitle("✅ Success! ✅");
                              unbanSuccess.setDescription(mod[1] +" has been unbanned successfully!");
                              unbanSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                              event.getChannel().sendTyping().queue();
                              event.getChannel().sendMessage(unbanSuccess.build()).queue();
                              unbanSuccess.clear();
                         }
                    }
               }
               else {
                    accessDenied(event);
               }
          }
          else if(mod[0].equalsIgnoreCase(Monitor.prefix + "kick")) {
               if(Objects.requireNonNull(event.getMember()).hasPermission(Permission.KICK_MEMBERS)) {
                    if(mod.length < 2) {
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
                         event.getGuild().kick(mod[1].replace("<@!", "").replace("<@", "").replace(">", "")).queue();        	 
                         EmbedBuilder kicked = new EmbedBuilder();
                         kicked.setColor(0x05055e);
                         kicked.setTitle("✅ Success! ✅");
                         kicked.setDescription(mod[1] +" has been kicked successfully!");
                         kicked.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                         event.getChannel().sendTyping().queue();
                         event.getChannel().sendMessage(kicked.build()).queue();
                         kicked.clear();
                    }
               }
               else {
                    accessDenied(event);
               }
          }
          else if(mod[0].equalsIgnoreCase(Monitor.prefix + "purge") || mod[0].equalsIgnoreCase(Monitor.prefix + "setPrefix") ) {
               if(Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
                    if(mod[0].equalsIgnoreCase(Monitor.prefix + "purge")) {
                         if(mod.length < 2) {            	  
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
                                   List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(mod[1])).complete();
                                   event.getChannel().purgeMessages(messages);
                                   //Notifies user if messages have been successfully deleted 
                                   EmbedBuilder purgeSuccess = new EmbedBuilder();
                                   purgeSuccess.setColor(0x05055e);
                                   purgeSuccess.setTitle("✅ Success! ✅");
                                   purgeSuccess.setDescription("You have successfully deleted " + mod[1] + " messages.");
                                   purgeSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                   event.getChannel().sendTyping().queue();
                                   event.getChannel().sendMessage(purgeSuccess.build()).queue();
                                   purgeSuccess.clear();
                                   //Logic of how to delete the embed is below
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
                    else if(mod[0].equalsIgnoreCase(Monitor.prefix + "setPrefix")) {
                         if(mod.length < 2) {           	  
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
                              Monitor.prefix = mod[1];
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
                    accessDenied(event);
               }
          }
          else if(mod[0].equalsIgnoreCase(Monitor.prefix + "createInvite") && mod.length == 1) {
               if(Objects.requireNonNull(event.getMember()).hasPermission(Permission.CREATE_INSTANT_INVITE)) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Invite created! Copy the link below and send it to someone!").queue();
                    event.getChannel().sendMessage(event.getChannel().createInvite().setMaxAge(0).complete().getUrl()).queue();
               }
               else {
                    accessDenied(event);
               }
          }
          else if (mod[0].equalsIgnoreCase(Monitor.prefix + "ticketSetup") && Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
               EmbedBuilder ticket = new EmbedBuilder();
               ticket.setColor(0x05055e);
               ticket.setTitle("**Create a Support Ticket**");
               ticket.setDescription("React with 📩 to create a new ticket.");
               ticket.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
               event.getChannel().sendMessage(ticket.build()).queue(t -> t.addReaction("📩").queue());
          }
     }

     @Override
     public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
          if(event.getChannel().getId().equals("709557615708864522") && event.getReactionEmote().getAsReactionCode().equals("📩") && !(event.getUser().isBot())) {
               event.getReaction().removeReaction(event.getUser()).queue(); 
               event.getGuild().createTextChannel("Support Ticket")
               .addPermissionOverride(Objects.requireNonNull(event.getGuild().getRoleById("709259200651591742")), null, EnumSet.of(Permission.VIEW_CHANNEL))
               .addPermissionOverride(Objects.requireNonNull(event.getGuild().getRoleById("710398399085805599")), EnumSet.of(Permission.VIEW_CHANNEL), null)
               .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_ATTACH_FILES), null)
               .queue(support -> {
                    support.sendMessage("Welcome to support! " + event.getMember().getAsMention()).queue();
                    support.sendMessage("A staff member will arrive to assist you shortly. " + Objects.requireNonNull(event.getGuild().getRoleById("710398399085805599")).getAsMention()).queue();
               });  
          }
     }
     @Override
     public void onGuildMemberJoin(GuildMemberJoinEvent event) {
          if(event.getGuild().getName().equals("The Goddess's Parthenon")) {
               // Wanderer
               event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("709505726640291877"))).queue();
               // Agreed to Rules 
               event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("709505763583852565"))).queue();
               // Welcome message that gets sent in #general with @user and @Welcomer mentions
               Objects.requireNonNull(event.getGuild().getTextChannelById("709259200651591747")).sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server, **The Goddess's Parthenon**! " + Objects.requireNonNull(event.getGuild().getRoleById("727010870403530760")).getAsMention() + " please make our new friend feel welcome!!! :)").queue();
          }
          else if(event.getGuild().getName().equals("Friends :)")) {
               // Members
               event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("754614035529597038"))).queue();
               // Welcome message that gets sent in #general with @user 
               Objects.requireNonNull(event.getGuild().getTextChannelById("753717833937977388")).sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server, **Friends :)**! Enjoy your stay :)").queue();
          }
          else if(event.getGuild().getName().equals("Wholesome Study Boys")) {
               // No Catfishing >:) 
               event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("694032141058834432"))).queue();
               // Welcome message in #general with @user
               Objects.requireNonNull(event.getGuild().getTextChannelById("693237215404359715")).sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server >:)").queue();
          }
          else if(event.getGuild().getName().equals("Playground")) {
               event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("756889036026675290"))).queue();
               Objects.requireNonNull(event.getGuild().getTextChannelById("710434525611688009")).sendMessage("Welcome to Playground! " + event.getMember().getAsMention()).queue();
          }
     } 
}    