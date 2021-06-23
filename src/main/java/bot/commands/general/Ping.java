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

package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

public class Ping implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            c.getJDA().getRestPing().queue((ping) -> Constants.setEmbed(c.getEvent(), "Ping 📶", "Bot Latency: "+ ping +" ms \nDiscord API Latency: "+ c.getJDA().getGatewayPing() +" ms \nAverage Shard Ping: "+ c.getShardManager().getAverageGatewayPing() + " ms"));
        }
    }

    @Override
    public String getName() {
        return "ping";
    }
}