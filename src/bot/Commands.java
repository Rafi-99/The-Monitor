package bot;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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
         
         //code for the bot info command
         if(args[0].equalsIgnoreCase(Sniper.prefix + "info")) {
              EmbedBuilder info = new EmbedBuilder();
              info.setTitle("Sniper Bot Information");
              info.setDescription("Multi-purpose Discord server bot in development.");
              info.addField("Bot Creator", "Rafi", false);
              info.setColor(0x05055e);
          //  info.setFooter("Created by Rafi", event.getMember().getUser().getAvatarUrl());
          //  Find out how to get only MY id so the footer works to have only MY picture
              
              event.getChannel().sendTyping().queue();
              event.getChannel().sendMessage(info.build()).queue();
              info.clear();
         }
         
         //code for the bot roast command
         else if(args[0].equalsIgnoreCase(Sniper.prefix + "roast")) {
              event.getChannel().sendTyping().queue();
              event.getChannel().sendMessage(Roasts[(int) (Math.random()*20)]).queue();
         }
         
         //code for the purge command (message deletion)
         else if(args[0].equalsIgnoreCase(Sniper.prefix + "purge")) {
        	 
              if(args.length < 2) {
            	  
            	  EmbedBuilder usage = new EmbedBuilder();
            	  usage.setColor(0x05055e);
            	  usage.setTitle("How do I Delete Messages?");
            	  usage.setDescription("Usage: " + Sniper.prefix + "purge [# of messages]");
            	  event.getChannel().sendTyping().queue();
            	  event.getChannel().sendMessage(usage.build()).queue();

              }
              
              else {
            	  
            	  try {
            		  
            		  List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
            		  event.getChannel().purgeMessages(messages);
            		  
//            		  Thinking how I could delete more than 100 messages 
//            		  for(int i = 0; i < messages.size(); i++) {
//            			  
//            			  event.getChannel().purgeMessages(messages.get(i));
//            			  
//            		  }
            		  

            		  
            		  //Notifies user if messages have been successfully deleted 
            		  EmbedBuilder purgeSuccess = new EmbedBuilder();
            		  purgeSuccess.setColor(0x05055e);
            		  purgeSuccess.setTitle("✅ Success! ✅");
            		  purgeSuccess.setDescription("You have successfully deleted " + args[1] + " messages.");
            		  event.getChannel().sendMessage(purgeSuccess.build()).queue();
            		  
            		  //event.getChannel().purgeMessagesById(purgeSuccess.build()); thinking of how to auto delete the embed so it doesn't clog the channel
            	  }
            	  
            	  catch(NumberFormatException n) {
            		
            		  EmbedBuilder purgeError = new EmbedBuilder();
            		  purgeError.setColor(0x05055e);
            		  purgeError.setTitle("❌ Invalid Argument ❌");
            		  purgeError.setDescription("Enter a number.");
            		  event.getChannel().sendMessage(purgeError.build()).queue();
            	  }
            	  
            	  catch (IllegalArgumentException e) {
            		  
            		  if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
            			  
  						// Messages >100 error
  						EmbedBuilder purgeError = new EmbedBuilder();
  						purgeError.setColor(0x05055e);
  						purgeError.setTitle("❌ Selected Messages Are Out of Range ❌");
  						purgeError.setDescription("Only 1-100 messages can be deleted at a time.");
  						event.getChannel().sendMessage(purgeError.build()).queue();
  					 }
            		  
  					 else {
  						 
  						// Messages are too old error
  						EmbedBuilder purgeError = new EmbedBuilder();
  						purgeError.setColor(0x05055e);
  						purgeError.setTitle("❌ Selected Messages Are Older Than 2 Weeks ❌");
  						purgeError.setDescription("Messages older than 2 weeks cannot be deleted.");
  						event.getChannel().sendMessage(purgeError.build()).queue();
  					}
            		  
            	  }
                   
               }
              
         }
         
         
         
         
         
         
// Find out how to limit bot command perms to only staff maybe use an if statement to check if a user has the role honoring the goddess          
//  Windows Key + . lets you access emoticons. Numpad doesn't work.        
// 1. Find out how to delete more than 100 messages        
// 2. Find out how to auto delete success and failure message embeds
// 3. get prefix to work         
         
     //     else if(args[0].equalsIgnoreCase(Sniper.prefix + "setPrefix")) {
     //           Sniper.prefix = args[1];
     //     }
     }
}
