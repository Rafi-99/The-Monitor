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

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class ServerInfo implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            /*
             * Code for the server info command. Consumer is used to create the embed with the retrieved owner inside.
             */
            Consumer<Member> owner = (o) -> {
                EmbedBuilder server = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("**Server Information**")
                .setDescription("Some basic information about the current server: " + c.getGuild().getName())
                .setThumbnail(c.getGuild().getIconUrl())
                .addField("**Server Name**", c.getGuild().getName(), true)
                .addField("**Server Owner**", o.getAsMention(), true)
                .addField("**Server Creation**", c.getGuild().getTimeCreated().format(DateTimeFormatter.ofPattern("'Date:' MMMM dd yyyy \n'Time:' h:mm a O")), true)
                .addField("**Server Location**", Locale.forLanguageTag(c.getGuild().getLocale().getLocale()).getDisplayCountry(), true)
                .addField("**Member Count**", Integer.toString(c.getGuild().getMemberCount()), true)
                .addField("**Role Count**", Integer.toString(c.getGuild().getRoles().size()), true)
                .addField("**Emote Count**", Integer.toString(c.getGuild().getEmojiCache().asList().size()), true)
                .addField("**Boost Tier**", c.getGuild().getBoostTier().toString().replace("TIER_", "Level "), true)
                .addField("**Boost Count**", Integer.toString(c.getGuild().getBoostCount()) , true)
                .setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                c.getEvent().getChannel().sendTyping().queue();
                c.getEvent().getChannel().sendMessageEmbeds(server.build()).setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                server.clear();
            };
            /*
             * This line of code runs all the code inside the success consumer, thus creating the embed.
             */
            c.getGuild().retrieveOwner().queue(owner);
        }
    }

    @Override
    public String getName() {
        return "serverInfo";
    }
}
