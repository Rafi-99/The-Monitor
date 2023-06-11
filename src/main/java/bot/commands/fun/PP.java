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

public class PP implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        int length = (int) (Math.random() * 13);
        StringBuilder growth = new StringBuilder();

        if (c.getCommandParameters().size() == 1 && !c.getEvent().getMessage().getMentions().getMembers().isEmpty()) {
            for (int i = 0; i < length; i++) {
                String inches = "=";
                growth.append(inches);
            }
            String maleObject = "8" + growth + "D";
            Constants.setEmbed(c.getEvent(), "Penis Generator", c.getEvent().getMessage().getMentions().getMembers().get(0).getEffectiveName() + "'s penis \n" + maleObject);
        }
        else {
            c.getEvent().getChannel().sendTyping().queue();
            c.getEvent().getChannel().sendMessage("Type in " + Constants.getCurrentPrefix(c) + "pp [user mention] to use this command!").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "pp";
    }
}