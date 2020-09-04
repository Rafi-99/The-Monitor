package bot.driver;

import bot.commands.*;
import bot.music.MusicCommands;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Monitor {	
    public static JDA myBot;
    public static String prefix = "m!"; 

    public static void Sender() {
         Thread sent = new Thread(() -> {
          //     TextChannel channel = myBot.getTextChannelById("710434525611688009"); #general for Playground
              TextChannel channel = myBot.getTextChannelById("709259200651591747");
              while (true) {
                   Scanner s = new Scanner(System.in);
                   String message = s.nextLine();
                   if ((message != null) && (message != "") && (message != "\n")) {
                        channel.sendMessage(message).complete();
                   }  
              } 
         });
         sent.start(); 
    }
	public static void main (String [] args) throws LoginException, InterruptedException {	
         myBot = JDABuilder.createDefault("NzExNzAzODUyOTc3NDg3OTAz.XsG33Q.sO36j1H_2pcuMNDfdYRINK6Zm0g")
         .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
         .setMemberCachePolicy(MemberCachePolicy.ALL)
         .enableCache(CacheFlag.VOICE_STATE)
         .addEventListeners(new Commands(), new Moderation(), new MusicCommands())
         .build()
         .awaitReady();

         myBot.getPresence().setActivity(Activity.listening(Monitor.prefix + "botInfo"));
         Sender();
     }
}