package bot.commands;

import bot.driver.Monitor;

import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
     
     String Roasts[] = { "Your birth certificate is an apology letter from the abortion clinic.",
               "I fucking hate you LOL!", "Don't play hard to get when you are hard to want.",
               "At least my dad didn't leave me.",
               "You should put a condom on your head, because if you're going to act like a dick you better dress like one too.",
               "Who cares if girls look different without makeup? Your dick looks hella different when it's soft.",
               "Maybe if you eat all that makeup you will be beautiful on the inside.",
               "Your forehead is so big that I could use it to play Tic-Tac-Toe.",
               "I wonder if you'd be able to speak more clearly if your parents were second cousins instead of first.",
               "You're objectively unattractive.", "I'm not a nerd, I'm just smarter than you.",
               "If you're going to be two-faced, at least make one of them pretty.",
               "You just might be why the middle finger was invented in the first place.",
               "I'm not insulting you, I'm describing you.",
               "You must have been born on a highway since that's where most accidents happen.",
               "If laughter is the best medicine, your face must be curing the world!",
               "Two wrongs don't make a right, and your parents have once again proven that.",
               "My phone battery lasts longer than your relationships.",
               "It's better to be a smartass than to be a dumbass.", "Your face makes onions cry." };
     
     boolean stop = false;

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
          
          String[] args = event.getMessage().getContentRaw().split("\\s+");

          // Code for the info command
          if (args[0].equalsIgnoreCase(Monitor.prefix + "botInfo")) {
               // lambda to create the embed and have the retrieved owner name and avatar to be set as the footer
               Monitor.myBot.retrieveApplicationInfo().queue(botOwner -> {
                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x05055e);
                    info.setTitle("**The Monitor ™ Bot Information**");
                    info.setDescription("A multi-purpose Discord server bot in development.");
                    info.setThumbnail(Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    info.addField("**Default prefix**", "m!", true);
                    info.addField("**Command Usage Example**", Monitor.prefix + "botInfo", false);
                    info.addField("**Moderation Commands**", "setPrefix, createInvite, mute, unmute, purge, kick, ban, unban", true);
                    info.addField("**Helpful Commands**", "botInfo, serverInfo", true);
                    info.addField("**Music Commands**", "join, leave, play, pause, skip, queue, clearQueue", true);
                    info.addField("**Fun Commands**", "roast, wholesome, simp, avatar", true);
                    info.setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(info.build()).queue();
                    info.clear();
               });
          }
          // Code for the server info command 
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "serverInfo") && args.length == 1) {
               // Using a lambda expression to have the queue requests executed in order. 
               // This is the only way I was able to get the owner successfully every time from any server.
               Consumer <Member> owner = (o) -> { 
                    EmbedBuilder server = new EmbedBuilder();
                    server.setColor(0x05055e);
                    server.setTitle("**Server Information**");
                    server.setDescription("Some basic information about the current server: " + event.getGuild().getName());
                    server.setThumbnail(event.getGuild().getIconUrl());
                    server.addField("**Server Name**", event.getGuild().getName(), true);     
                    server.addField("**Server Owner**", o.getAsMention(), true);
                    server.addField("**Server Creation**", event.getGuild().getTimeCreated().getMonthValue() +"/"+ event.getGuild().getTimeCreated().getDayOfMonth() +"/"+ event.getGuild().getTimeCreated().getYear(), true);
                    server.addField("**Server Location**", event.getGuild().getRegionRaw().toUpperCase().replace("-", " ") , true);
                    server.addField("**Member Count**", Integer.toString(event.getGuild().getMemberCount()), true);
                    server.addField("**Role Count**", Integer.toString(event.getGuild().getRoles().size()), true);
                    server.addField("**Emote Count**", Integer.toString(event.getGuild().getEmotes().size()), true);
                    server.addField("**Boost Tier**", event.getGuild().getBoostTier().toString().replace("TIER_", "Level "), true);
                    server.addField("**Boost Count**", Integer.toString(event.getGuild().getBoostCount()) , true);
                    server.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(server.build()).queue();
                    server.clear();   
               };
               // This line of code runs all the code in the success consumer, thus creating the embed.
               event.getGuild().retrieveOwner().queue(owner);     
          }          
          // Code for the roast command
          else if (args[0].equalsIgnoreCase(Monitor.prefix + "roast")) {
               event.getChannel().sendTyping().queue();
               event.getChannel().sendMessage(Roasts[(int) (Math.random() * 20)]).queue();
          }
          // Code for the wholesome command
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "Wholesome")) {
               if(args.length < 2) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in m!wholesome and mention a user to use this command!").queue();
               }
               else {
                    EmbedBuilder wholesome = new EmbedBuilder();
                    wholesome.setColor(0x05055e);
                    wholesome.setTitle("Such Wholesome");
                    wholesome.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    wholesome.setDescription(args[1] + " is "+ (int) (Math.random()*101) +"% wholesome.");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(wholesome.build()).queue();
                    wholesome.clear();
               }
          }
          // Code for the simp command
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "Simp")) {
               if(args.length < 2) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in m!simp and mention a user to use this command!").queue();
               }
               else {
                    EmbedBuilder simp = new EmbedBuilder();
                    simp.setColor(0x05055e);
                    simp.setTitle("Simps be Simping");
                    simp.setDescription(args[1] + " is "+ (int) (Math.random()*101) +"% Simp.");
                    simp.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(simp.build()).queue();
                    simp.clear();
               }
          }  
          //find out how to mask if the account is a bot maybe so I can bump
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "test")) {
               ScheduledExecutorService test = Executors.newSingleThreadScheduledExecutor();
               test.scheduleAtFixedRate(new Runnable(){
                    @Override
                    public void run() {
                         event.getChannel().sendMessage("This is test spam.").queue();
                         if(stop == true) {
                              event.getChannel().sendTyping().complete();
                              event.getChannel().sendMessage("Ending spam now...").complete();
                              event.getChannel().sendMessage("Ended.").complete();
                              test.shutdownNow();
                              stop = false;
                         }
                    }     
               }, 0, 1, TimeUnit.SECONDS);
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "stopTest") && args.length == 1) {
               stop = true;
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "avatar")) {
               if(args.length == 2) {
                    EmbedBuilder avatar = new EmbedBuilder();
                    avatar.setColor(0x05055e);
                    avatar.setTitle("Avatar");
                    avatar.setDescription(event.getMessage().getMentionedUsers().get(0).getName());
                    avatar.setImage(event.getMessage().getMentionedUsers().get(0).getEffectiveAvatarUrl());
                    avatar.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendMessage(avatar.build()).queue();
               }
               else {
                    event.getChannel().sendMessage("Type m!avatar and mention a user to view their avatar!").queue();
               }
          }
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "testing")) { 
               event.getChannel().sendMessage("Sent to DM!").queue();
               event.getAuthor().openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("Hello there!").queue();
                    privateChannel.close().queue();
               });
          }
          else if (args[0].equalsIgnoreCase(Monitor.prefix + "ticketSetup") && event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
               EmbedBuilder ticket = new EmbedBuilder();
               ticket.setColor(0x05055e);
               ticket.setTitle("**Create a Support Ticket**");
               ticket.setDescription("React with 📩 to create a new ticket.");
               ticket.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
               event.getChannel().sendMessage(ticket.build()).queue(t -> t.addReaction("📩").queue());
               //Testing..do I have to use a Hash Map? 
               // event.getChannel.sendMessage("Please copy and paste in the id of the public role").queue();
               // Role event.getGuild.getMessage("")
               // event.getChannel.sendMessage("Please copy and paste in the id of the staff role").queue();
               //Maybe send a message asking for the id of the public @everyone role and staff role 
               //retrive the id from the string message and set it equal to a variable 

               //event.getGuild().getRoleById(event.getMessage().getContentRaw());
          }

          

          // // Mute command
          // else if (args[0].equalsIgnoreCase(Monitor.prefix + "mute")) {
          //      // For no time limit muting
          //      if (args.length > 1 && args.length < 3) {
          //           Role mutedRole = event.getGuild().getRoleById("709998343841120288");
          //           event.getGuild().addRoleToMember(event.getMessage().getMentionedMembers().get(0), mutedRole)
          //                     .queue();

          //           EmbedBuilder muteSuccess = new EmbedBuilder();
          //           muteSuccess.setColor(0x05055e);
          //           muteSuccess.setTitle("✅ Success! ✅");
          //           muteSuccess.setDescription(args[1] + " has been successfully muted!");
          //           event.getChannel().sendTyping().queue();
          //           event.getChannel().sendMessage(muteSuccess.build()).queue();
          //           muteSuccess.clear();
          //      }
          //      // With time limit
          //      else if (args.length == 3) {

          //      } else {
          //           // How to use command
          //           EmbedBuilder mute = new EmbedBuilder();
          //           mute.setColor(0x05055e);
          //           mute.setTitle("Mute Command Usage");
          //           mute.setDescription("Usage: " + Monitor.prefix + "mute [user mention] [time {optional}]");
          //           event.getChannel().sendTyping().queue();
          //           event.getChannel().sendMessage(mute.build()).queue();
          //           mute.clear();
          //      }
          //   } 
     }
     // reaction to delete the channel after support done?
     //lookup how to do undo reactions one more time?
     // maybe have a user prompt for setup of the staff role and where to have the support channel then the code gets it by id?
     @Override
     public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
          if(event.getChannel().getId().equals("709557615708864522") && event.getReactionEmote().getAsReactionCode().equals("📩") && !(event.getUser().isBot()) ) {
               event.getReaction().removeReaction(event.getUser()).queue(); 
               event.getGuild().createTextChannel("Support Ticket")
               .addPermissionOverride(event.getGuild().getRoleById("709259200651591742"), null, EnumSet.of(Permission.VIEW_CHANNEL))
               .addPermissionOverride(event.getGuild().getRoleById("710398399085805599"), EnumSet.of(Permission.VIEW_CHANNEL), null)
               .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_ATTACH_FILES), null)
               .queue(support -> {
                    support.sendMessage("Welcome to support! " + event.getMember().getAsMention()).queue();
                    support.sendMessage("A staff member will arrive to assist you shortly. " + event.getGuild().getRoleById("710398399085805599").getAsMention()).queue();
               });  
          }
     }
     @Override
     public void onGuildMemberJoin(GuildMemberJoinEvent event) {
          if(event.getGuild().getName().equals("The Goddess's Parthenon")) {
               // Wanderer
               event.getGuild().addRoleToMember(event.getMember().getId(), event.getGuild().getRoleById("709505726640291877")).queue();
               // Agreed to Rules 
               event.getGuild().addRoleToMember(event.getMember().getId(), event.getGuild().getRoleById("709505763583852565")).queue();
               // Welcome message that gets sent in #general with @user and @Welcomer mentions
               event.getGuild().getTextChannelById("709259200651591747").sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server, **The Goddess's Parthenon**! " + event.getGuild().getRoleById("727010870403530760").getAsMention() + " please make our new friend feel welcome!!! :)").queue();
          }
          else if(event.getGuild().getName().equals("moi server.")) {
               // Members
               event.getGuild().addRoleToMember(event.getMember().getId(), event.getGuild().getRoleById("754614035529597038")).queue();
               // Welcome message that gets sent in #general with @user 
               event.getGuild().getTextChannelById("753717833937977388").sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server, **moi server.**! Enjoy your stay :)").queue();
          }
     
     }

}    
// Windows Key + . lets you access emoticons. Numpad doesn't work.             
// 1. Find out how to delete more than 100 messages        
// 2. Find out how to auto-delete success and failure message embeds