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

import net.dv8tion.jda.api.Permission;

public class Link implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals(System.getenv("BOT_OWNER"))) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(c.getEvent().getJDA().getInviteUrl(Permission.ADMINISTRATOR)).reference(c.getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "link";
    }
}