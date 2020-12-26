package bot.driver;

import bot.commands.*;
import bot.music.MusicCommands;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Monitor {

    public static JDA myBot;
    public static String prefix = "m!";
    
    public static void main(String[] args) throws LoginException, InterruptedException {

        myBot = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .enableCache(CacheFlag.VOICE_STATE)
        .addEventListeners(new Admin(), new Fun(), new General(), new Moderation(), new MusicCommands())
        .build()
        .awaitReady();

        myBot.getPresence().setActivity(Activity.playing(Monitor.prefix + "botInfo"));
    }
}