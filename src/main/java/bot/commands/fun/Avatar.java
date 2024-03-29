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

import java.time.Year;

import net.dv8tion.jda.api.EmbedBuilder;

public class Avatar implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if (c.getCommandParameters().size() == 1 && !c.getEvent().getMessage().getMentions().getUsers().isEmpty()) {
            EmbedBuilder avatar = new EmbedBuilder()
            .setColor(0x05055e)
            .setTitle("Avatar")
            .setDescription(c.getEvent().getMessage().getMentions().getUsers().get(0).getName())
            .setImage(c.getEvent().getMessage().getMentions().getUsers().get(0).getEffectiveAvatarUrl() + "?size=4096")
            .setFooter("The Monitor ™ | © " + Year.now().getValue(), c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
            c.getEvent().getChannel().sendTyping().queue();
            c.getEvent().getChannel().sendMessageEmbeds(avatar.build()).setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
            avatar.clear();
        }
        else {
            c.getEvent().getChannel().sendTyping().queue();
            c.getEvent().getChannel().sendMessage("Type in " + Constants.getCurrentPrefix(c) + "avatar and mention a user to view their avatar!").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "avatar";
    }
}
