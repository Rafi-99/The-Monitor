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

package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Join implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(!Constants.getAudioManager(c).isConnected()) {

                if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null) {
                    Constants.getAudioManager(c).openAudioConnection(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel());
                    String name = Objects.requireNonNull(c.getMember().getVoiceState().getChannel()).toString().replace("VC:", "");
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Successfully connected to: " + name.substring(name.indexOf(""), name.indexOf("("))).reference(c.getMessage()).mentionRepliedUser(false).queue();
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("You have to be in a voice channel first!").reference(c.getMessage()).mentionRepliedUser(false).queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("I am already connected to a voice channel!").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "join";
    }
}