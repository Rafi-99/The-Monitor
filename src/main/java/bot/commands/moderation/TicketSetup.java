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

package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.time.Year;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class TicketSetup implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if (c.getEvent().getMember().hasPermission(Permission.MANAGE_SERVER)) {
            EmbedBuilder ticket = new EmbedBuilder()
            .setColor(0x05055e)
            .setTitle("**Create a Support Ticket**")
            .setDescription("React with 📩 to create a new ticket.")
            .setFooter("The Monitor ™ | © " + Year.now().getValue(), c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
            c.getEvent().getChannel().sendMessageEmbeds(ticket.build()).queue(t -> t.addReaction(Emoji.fromUnicode("📩")).queue());
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "ticketSetup";
    }
}
