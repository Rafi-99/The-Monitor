package bot.commands;

import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Fun extends ListenerAdapter {

     String [] roasts = {
          "Your birth certificate is an apology letter from the abortion clinic.",
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
          "It's better to be a smartass than to be a dumbass.", "Your face makes onions cry." 
     };

     private void rpsGame(GuildMessageReceivedEvent event, String [] fun, int rps) {
          if(rps == 1) {

               if(fun[1].equalsIgnoreCase("Rock")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Rock :moyai: \nIt's a tie!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else if(fun[1].equalsIgnoreCase("Paper")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Rock :moyai: \nYou won!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else if (fun[1].equalsIgnoreCase("Scissors")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Rock :moyai: \nYou lost!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else {
                    rpsError(event);
               }
          }
          else if(rps == 2) {

               if(fun[1].equalsIgnoreCase("Paper")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Paper :newspaper: \nIt's a tie!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else if(fun[1].equalsIgnoreCase("Scissors")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Paper :newspaper: \nYou won!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else if(fun[1].equalsIgnoreCase("Rock")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Paper :newspaper: \nYou lost!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else {
                    rpsError(event);
               }
          }
          else if (rps == 3) {

               if(fun[1].equalsIgnoreCase("Scissors")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Scissors :scissors: \nIt's a tie!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else if(fun[1].equalsIgnoreCase("Rock")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Scissors :scissors: \nYou won!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else if(fun[1].equalsIgnoreCase("Paper")) {
                    EmbedBuilder rpsBuilder = new EmbedBuilder();
                    rpsBuilder.setColor(0x05055e);
                    rpsBuilder.setTitle("Rock Paper Scissors");
                    rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    rpsBuilder.setDescription("The computer was: Scissors :scissors: \nYou lost!");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(rpsBuilder.build()).queue();
                    rpsBuilder.clear();
               }
               else {
                    rpsError(event);
               }
          }
     }

     private void rpsError(GuildMessageReceivedEvent event) {
          EmbedBuilder rpsBuilder = new EmbedBuilder();
          rpsBuilder.setColor(0x05055e);
          rpsBuilder.setTitle("Rock Paper Scissors");
          rpsBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
          rpsBuilder.setDescription("Invalid input.");
          event.getChannel().sendTyping().queue();
          event.getChannel().sendMessage(rpsBuilder.build()).queue();
          rpsBuilder.clear();
     }

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

          String [] fun = event.getMessage().getContentRaw().split("\\s+"); 

          if(fun[0].equalsIgnoreCase(Monitor.prefix + "roast")) {

               if(fun.length == 2) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(fun[1] +" "+ roasts[(int) (Math.random() * 20)]).queue();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in "+ Monitor.prefix +"roast and mention the person you want to roast!").queue();
               }
          }
          else if(fun[0].equalsIgnoreCase(Monitor.prefix + "wholesome")) {

               if(fun.length == 2) {
                    EmbedBuilder wholesome = new EmbedBuilder();
                    wholesome.setColor(0x05055e);
                    wholesome.setTitle("Such Wholesome");
                    wholesome.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    wholesome.setDescription(fun[1] + " is " + (int) (Math.random() * 101) + "% wholesome.");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(wholesome.build()).queue();
                    wholesome.clear();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in "+ Monitor.prefix +"wholesome and mention someone to use this command!").queue();
               }
          }
          else if(fun[0].equalsIgnoreCase(Monitor.prefix + "simp")) {

               if(fun.length == 2) {
                    EmbedBuilder simp = new EmbedBuilder();
                    simp.setColor(0x05055e);
                    simp.setTitle("Such Wholesome");
                    simp.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    simp.setDescription(fun[1] + " is " + (int) (Math.random() * 101) + "% simp.");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(simp.build()).queue();
                    simp.clear();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in "+ Monitor.prefix +"simp and mention someone to use this command!").queue();
               }
          }
          else if(fun[0].equalsIgnoreCase(Monitor.prefix + "avatar")) {

               if (fun.length == 2) {
                    EmbedBuilder avatar = new EmbedBuilder();
                    avatar.setColor(0x05055e);
                    avatar.setTitle("Avatar");
                    avatar.setDescription(event.getMessage().getMentionedUsers().get(0).getName());
                    avatar.setImage(event.getMessage().getMentionedUsers().get(0).getEffectiveAvatarUrl());
                    avatar.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(avatar.build()).queue();
                    avatar.clear();
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in "+ Monitor.prefix +"avatar and mention a user to view their avatar!").queue();
               }
          }
          else if(fun[0].equalsIgnoreCase(Monitor.prefix + "pp")) {

               int length = (int) (Math.random()*13);
               StringBuilder growth = new StringBuilder();

               if(fun.length == 2) {
                    for (int i = 0; i < length; i++) {
                         String inches = "=";
                         growth.append(inches);
                    }
                    String maleObject = "8" + growth + "D";

                    EmbedBuilder schlong = new EmbedBuilder();
                    schlong.setColor(0x05055e);
                    schlong.setTitle("Penis Generator");
                    schlong.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    schlong.setDescription(event.getMessage().getMentionedMembers().get(0).getEffectiveName() + "'s penis \n"+ maleObject);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(schlong.build()).queue();
                    schlong.clear();
                    
               }
               else {
                    event.getChannel().sendTyping().queue(); 
                    event.getChannel().sendMessage("Type in "+ Monitor.prefix +"pp [user mention] to use this command!").queue();
                    
               }
          }
          else if(fun[0].equalsIgnoreCase(Monitor.prefix + "rps")) {

               int rps = (int) (Math.random() * 3) + 1;

               if(fun.length == 2) {
                    rpsGame(event, fun, rps);
               }
               else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Type in "+ Monitor.prefix +"rps [rock/paper/scissors] to use this command!").queue();
               }
          }
     }
}