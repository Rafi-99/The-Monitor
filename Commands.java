package bot;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

     String  Roasts [] = {"Your birth certificate is an apology letter from the abortion clinic.", 
                          "I fucking hate you LOL!", 
                          "Don't play hard to get when you are hard to want.", 
                          "At least my dad didn't leave me.",
                          "You should put a condom on your head, because if you're going to act like a dick you better dress like one too.",
                          "Who cares if girls look different without makeup? Your dick looks hella different when it's soft.", 
                          "Maybe if you eat all that makeup you will be beautiful on the inside.", 
                          "Your forehead is so big that I could use it to play Tic-Tac-Toe.", 
                          "I wonder if you'd be able to speak more clearly if your parents were second cousins instead of first.", 
                          "You're objectively unattractive.", 
                          "I'm not a nerd, I'm just smarter than you.", 
                          "If you're going to be two-faced, at least make one of them pretty.",
                          "You just might be why the middle finger was invented in the first place.", 
                          "I'm not insulting you, I'm describing you.",
                          "You must have been born on a highway since that's where most accidents happen.",
                          "If laughter is the best medicine, your face must be curing the world!",
                          "Two wrongs don't make a right, and your parents have once again proven that.",
                          "My phone battery lasts longer than your relationships.",
                          "It's better to be a smartass than to be a dumbass.", 
                          "Your face makes onions cry."};
	
     public void onMessageReceived(MessageReceivedEvent event)
     {
         String [] args = event.getMessage().getContentRaw().split("\\s+");
         
         //code for the info command
         if(args[0].equalsIgnoreCase(Monitor.prefix + "info")) {
              EmbedBuilder info = new EmbedBuilder();
              info.setColor(0x05055e);
              info.setTitle("The Monitor ™ Bot Information");
              info.setDescription("A multi-purpose Discord server bot in development.");
              info.addField("Default prefix", "m!", false);
              info.addField("Commands","info, setPrefix, roast, mute/unmute, kick, ban/unban", false);
              info.addField("Bot Creator", "Rafi ™", false);              
          //  info.setFooter("Created by Rafi", event.getMember().getUser().getAvatarUrl());
          //  ^Trying to figure out how to get only MY id so the footer works to have only MY picture              
              event.getChannel().sendTyping().queue();
              event.getChannel().sendMessage(info.build()).queue();
              info.clear();
         }
         
         //code for the roast command
         else if(args[0].equalsIgnoreCase(Monitor.prefix + "roast")) {
              event.getChannel().sendTyping().queue();
              event.getChannel().sendMessage(Roasts[(int) (Math.random()*20)]).queue();
         }         
         //code for the purge command (message deletion)
         else if(args[0].equalsIgnoreCase(Monitor.prefix + "purge")) {
        	 
              if(args.length < 2) {
            	  
            	  EmbedBuilder usage = new EmbedBuilder();
            	  usage.setColor(0x05055e);
            	  usage.setTitle("Message Deletion Usage");
            	  usage.setDescription("Usage: " + Monitor.prefix + "purge [# of messages]");
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
              event.getChannel().sendTyping().queue();
              event.getChannel().sendMessage(prefixSet.build()).queue();
              prefixSet.clear();
              
        	 }
         }                  
         //Mute command
         else if(args[0].equalsIgnoreCase(Monitor.prefix + "mute")) {
        	//For no time limit muting 
        	if(args.length > 1 && args.length <3) {        		       
            	Role mutedRole = event.getGuild().getRoleById("709998343841120288");		
        		event.getGuild().addRoleToMember(event.getMessage().getMentionedMembers().get(0), mutedRole).queue();
        		
        		EmbedBuilder muteSuccess = new EmbedBuilder();
        		muteSuccess.setColor(0x05055e);
        		muteSuccess.setTitle("✅ Success! ✅");
        		muteSuccess.setDescription(args[1] +" has been successfully muted!");
        		event.getChannel().sendTyping().queue();
        		event.getChannel().sendMessage(muteSuccess.build()).queue();
        		muteSuccess.clear();
        	}
        	//With time limit
        	else if(args.length == 3) {
        		
        	}
        	else {
        		//How to use command 
        		EmbedBuilder mute = new EmbedBuilder();
        		mute.setColor(0x05055e);
        		mute.setTitle("Mute Command Usage");
        		mute.setDescription("Usage: "+ Monitor.prefix +"mute [user mention] [time {optional}]");
        		event.getChannel().sendTyping().queue();
        		event.getChannel().sendMessage(mute.build()).queue();
        		mute.clear();
        	}
         }
         //Kick Command (suggestion: maybe ask for user input for reason to kick/ban?)
         else if(args[0].equalsIgnoreCase(Monitor.prefix + "kick")) {
        	 
        	 if(args.length < 2) {
         		EmbedBuilder kick = new EmbedBuilder();
         		kick.setColor(0x05055e);
         		kick.setTitle("Kick Command Usage");
         		kick.setDescription("Usage: "+ Monitor.prefix +"kick [user mention]");
         		event.getChannel().sendTyping().queue();
         		event.getChannel().sendMessage(kick.build()).queue();
         		kick.clear();        		 
        	 }
        	 else {
        		event.getGuild().kick(event.getMessage().getMentionedMembers().get(0)).queue();        	 
        	 	EmbedBuilder kicked = new EmbedBuilder();
        	 	kicked.setColor(0x05055e);
        	 	kicked.setTitle("✅ Success! ✅");
        	 	kicked.setDescription(args[1] +" has been kicked successfully!");
        	 	event.getChannel().sendTyping().queue();
        	 	event.getChannel().sendMessage(kicked.build()).queue();
        	 	kicked.clear();
        	 }
         }
         //Ban Command (deletes messages of 7 days prior to getting banned)
         else if(args[0].equalsIgnoreCase(Monitor.prefix + "ban")) {
        	 if(args.length < 2) {
         		EmbedBuilder ban = new EmbedBuilder();
         		ban.setColor(0x05055e);
         		ban.setTitle("Ban Command Usage");
         		ban.setDescription("Usage: "+ Monitor.prefix +"ban [user mention]");
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
        		 event.getChannel().sendTyping().queue();
        		 event.getChannel().sendMessage(banSuccess.build()).queue();
        		 banSuccess.clear();
        	 }
         }
         //unban command 
         else if(args[0].equalsIgnoreCase(Monitor.prefix + "unban")) {
        	 if(args.length < 2) {
         		EmbedBuilder unban = new EmbedBuilder();
         		unban.setColor(0x05055e);
         		unban.setTitle("Remove Ban Command Usage");
         		unban.setDescription("Usage: "+ Monitor.prefix +"unban [user mention]");
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
        		 event.getChannel().sendTyping().queue();
        		 event.getChannel().sendMessage(unbanSuccess.build()).queue();
        		 unbanSuccess.clear();
        	 }
         }
// Find out how to limit command permissions to only staff          
//  Windows Key + . lets you access emoticons. Numpad doesn't work.             
// 1. Find out how to delete more than 100 messages        
// 2. Find out how to auto delete success and failure message embeds            
     }
}