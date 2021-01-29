package bot.driver;

import bot.handlers.database.DataSource;
import bot.handlers.event.EventListener;

import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Monitor {

    public static JDA myBot;

    public static void main(String[] args) throws LoginException, InterruptedException, SQLException, URISyntaxException {

        DataSource.getConnection();

        myBot = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .enableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
        .addEventListeners(new EventListener())
        .build()
        .awaitReady();

        myBot.getPresence().setActivity(Activity.playing("@The Monitor ™#7579"));
    }
}