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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.RestAction;

public class Kick implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            
            if(c.getCommandParameters().size() < 1) {
                Constants.setEmbed(c.getEvent(), "Kick Command Usage", "Usage: "+ Constants.getCurrentPrefix(c) +"kick [user mention or ID]");
            }
            else {
                try {
                    RestAction<Member> kickEvent = c.getGuild().retrieveMemberById(c.getCommandParameters().get(0).replace("<@!", "").replace("<@", "").replace(">", ""));
                    kickEvent.queue((user) -> {
                        if(c.getEvent().getGuild().getMemberById(user.getId()) != null) {
                            c.getEvent().getGuild().kick(user).queue();
                            Constants.setEmbed(c.getEvent(), "✅ Success! ✅", user.getAsMention() +" has been kicked successfully!");
                        }
                    }, (error) -> Constants.setEmbed(c.getEvent(), "❌ Failed to Kick ❌", "Invalid user. Users not in the guild can't be kicked. Please try executing the command again with a valid user mention or user ID."));
                } 
                catch (Exception e) {
                    Constants.setEmbed(c.getEvent(), "❌ Failed to Kick ❌", "Bad format. Please try executing the command again with a valid user mention or user ID.");
                }
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "kick";
    }
}