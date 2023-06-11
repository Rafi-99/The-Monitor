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

package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.List;

import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;

public class Emotes implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        StringBuilder emotes = new StringBuilder();
        List<RichCustomEmoji> guildEmotes = c.getGuild().getEmojiCache().asList();

        if (guildEmotes.isEmpty()) {
            Constants.setEmbed(c.getEvent(), "Server Emotes", "There are no emotes in this server.");
            return;
        }

        for (RichCustomEmoji emote : guildEmotes) {
            emotes.append(emote.getAsMention());
        }
        Constants.setEmbed(c.getEvent(), "Server Emotes", String.valueOf(emotes));
    }

    @Override
    public String getName() {
        return "emotes";
    }
}
