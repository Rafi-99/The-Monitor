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

package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class ping implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.getJDA().getRestPing().queue((ping) -> event.reply("Bot Latency: "+ ping +" ms \nDiscord API Latency: "+ event.getJDA().getGatewayPing() +" ms \nAverage Shard Ping: "+ Objects.requireNonNull(event.getJDA().getShardManager()).getAverageGatewayPing() + " ms").setEphemeral(true).queue());
    }

    @Override
    public String name() {
        return "ping";
    }

    @Override
    public String description() {
        return "displays ping";
    }
}