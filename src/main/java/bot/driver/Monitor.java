/*
 * Copyright 2020 Md Rafi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bot.driver;

import bot.handlers.database.DataSource;
import bot.handlers.event.*;

import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Monitor {

    public static void main(String[] args) throws LoginException, SQLException, URISyntaxException {
        DataSource.getConnection();

        DefaultShardManagerBuilder.createDefault(System.getenv("BOT_TOKEN"))
        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT)
        .setMemberCachePolicy(MemberCachePolicy.ALL.and(MemberCachePolicy.VOICE))
        .enableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI)
        .setBulkDeleteSplittingEnabled(false)
        .addEventListeners(new EventListener(), new SlashEventListener())
        .setActivityProvider((shardID) -> Activity.listening("/help | Shard "+ (shardID + 1)))
        .build();
    }
}
