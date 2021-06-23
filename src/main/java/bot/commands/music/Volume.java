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

public class Volume implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(c.getCommandParameters().isEmpty()) {
                Constants.setEmbed(c.getEvent(), "Volume Command Usage 🔊", Constants.getCurrentPrefix(c) +"volume [0-200]"+"\n"+"Current Volume: "+ Constants.getMusicManager(c).player.getVolume() +"%");
            }
            else {

                if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                    
                    try {
                        int playerVolume = Integer.parseInt(c.getCommandParameters().get(0));

                        if(playerVolume >= 0 && playerVolume <=200) {
                            Constants.getMusicManager(c).player.setVolume(playerVolume);
                            c.getChannel().sendTyping().queue();
                            c.getChannel().sendMessage("The player volume has been set to "+ Constants.getMusicManager(c).player.getVolume() +"%").reference(c.getMessage()).mentionRepliedUser(false).queue();
                            return;
                        }
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage("Please enter a number between 0 and 200 inclusive.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                    } 
                    catch (Exception e) {
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage("Please enter a number between 0 and 200 inclusive.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                    }
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("You have to be in the same voice channel as me to change the player volume.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                }
            } 
        }
    }

    @Override
    public String getName() {
        return "volume";
    }
}