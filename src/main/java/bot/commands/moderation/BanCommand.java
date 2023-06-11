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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.requests.RestAction;

public class BanCommand implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getEvent().getMember().hasPermission(Permission.BAN_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                Constants.setEmbed(c.getEvent(), "Ban Command Usage", "Usage: "+ Constants.getCurrentPrefix(c) +"ban [user mention or ID]");
            }
            else {

                try {
                    RestAction<User> banEvent = c.getEvent().getJDA().retrieveUserById(c.getCommandParameters().get(0).replace("<@!", "").replace("<@", "").replace(">", ""));
                    banEvent.queue((user) -> {

                        if (user != null) {
                            Consumer<? super List<Ban>> banListConsumer = (b) -> {

                                List<User> banIDList = new ArrayList<>(b.size());
                                for(int i=0; i < b.size(); i++) {
                                    banIDList.add(i, b.get(i).getUser());
                                }

                                if (banIDList.contains(user)) {
                                    Constants.setEmbed(c.getEvent(), "❌ User Already Banned ❌", "Cannot ban users that already have a ban.");
                                }
                                else {
                                    c.getGuild().ban(user, 7, TimeUnit.DAYS).queue();
                                    Constants.setEmbed(c.getEvent(), "✅ Success! ✅", user.getAsMention() + " has been banned successfully!");
                                }
                            };
                            //Retrieves the ban list then runs the code inside the consumer to ban the user.
                            c.getGuild().retrieveBanList().queue(banListConsumer);
                        }
                    }, (error) -> Constants.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Users that are no longer in a guild cannot be mentioned. Please try executing the command again with a valid user mention or user ID."));
                }
                catch (Exception e) {
                    Constants.setEmbed(c.getEvent(), "❌ Invalid Argument ❌", "Users that are no longer in a guild cannot be mentioned. Please try executing the command again with a valid user mention or user ID.");
                }
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "ban";
    }
}
