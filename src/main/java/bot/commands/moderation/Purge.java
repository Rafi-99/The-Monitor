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

import net.dv8tion.jda.api.Permission;

public class Purge implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if (c.getEvent().getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

            if (c.getCommandParameters().isEmpty()) {
                Constants.setEmbed(c.getEvent(), "Message Deletion Usage", "Usage: " + Constants.getCurrentPrefix(c) + "purge [# of messages]");
            }
            else {

                try {

                    int num = Integer.parseInt(c.getCommandParameters().get(0));

                    if (num > 0 && num <= 1000) {
                        c.getEvent().getChannel().getIterableHistory().takeAsync(num).thenAccept(messages -> {
                            c.getEvent().getChannel().purgeMessages(messages);
                            Constants.setEmbed(c.getEvent(), "✅ Success! ✅", "You have successfully deleted " + messages.size() + " messages.");
                        });
                    }
                    else {
                        Constants.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter a number between 1 and 1000.");
                    }
                }
                catch (Exception e) {
                    Constants.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Please enter a number between 1 and 1000.");
                }
            }
        } else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "purge";
    }
}
