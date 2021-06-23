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

import me.duncte123.botcommons.web.WebUtils;

import net.dv8tion.jda.api.EmbedBuilder;

public class Meme implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            WebUtils.ins.getJSONObject("https://meme-api-node-js.herokuapp.com/api/memes").async((json) -> {
                String title = json.get("data").get("title").asText();
                String url = json.get("data").get("url").asText();
                String image = json.get("data").get("image").asText();

                EmbedBuilder meme = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle(title, url)
                .setImage(image)
                .setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessageEmbeds(meme.build()).reference(c.getMessage()).mentionRepliedUser(false).queue();
                meme.clear();
            });
        }
    }

    @Override
    public String getName() {
        return "meme";
    }
}