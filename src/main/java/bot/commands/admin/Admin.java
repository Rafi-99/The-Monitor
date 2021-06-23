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

package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

import net.dv8tion.jda.api.EmbedBuilder;

public class Admin implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals(System.getenv("BOT_OWNER"))) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("The information has been sent to your DM!").reference(c.getMessage()).mentionRepliedUser(false).queue();
            c.getAuthor().openPrivateChannel().queue(privateChannel -> {
                EmbedBuilder adminInfo = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("Admin Tools")
                .setDescription("Commands available for your usage: \n```test -s \ntest -t \nadmin \nrestart \nlink \nguilds```")
                .setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                privateChannel.sendTyping().queue();
                privateChannel.sendMessageEmbeds(adminInfo.build()).queue();
                adminInfo.clear();
                privateChannel.close().queue();
            });
        }
    }

    @Override
    public String getName() {
        return "admin";
    }
}