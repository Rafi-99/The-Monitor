package bot.driver;

import bot.commands.SlashCommandInterface;
import bot.handlers.command.SlashCommandManager;
import bot.handlers.database.DataSource;
import bot.handlers.event.*;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Monitor {

    public static JDA bot;
    public static final List<SlashCommandInterface> slashCommandList = new SlashCommandManager().getAllSlashCommands();

    public static void main(String[] args) throws LoginException, SQLException, URISyntaxException {
        DataSource.getConnection();

        DefaultShardManagerBuilder.createDefault(System.getenv("BOT_TOKEN"))
        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
        .setMemberCachePolicy(MemberCachePolicy.ALL.and(MemberCachePolicy.VOICE))
        .enableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
        .setBulkDeleteSplittingEnabled(false)
        .addEventListeners(new EventListener(), new SlashEventListener())
        .setActivityProvider((shardID) -> Activity.listening("/help [Shard "+ (shardID + 1) +"]"))
        .build(); 

        //bot.updateCommands().queue();

        for (int i = 0; i < slashCommandList.size(); i++) {
            bot.upsertCommand(slashCommandList.get(i).name(), slashCommandList.get(i).description()).queue();
        }
    }
}