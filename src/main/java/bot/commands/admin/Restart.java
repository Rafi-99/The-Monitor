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

public class Restart implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {

            if(c.getMember().getId().equals(System.getenv("BOT_OWNER"))) {
                c.getChannel().sendTyping().complete();
                c.getChannel().sendMessage("Terminating..."+"\n"+"Bot is now going offline and restarting.").reference(c.getMessage()).mentionRepliedUser(false).complete();
                System.exit(0);
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Access denied.").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "restart";
    }
}