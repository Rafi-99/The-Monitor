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

public class Clear implements CommandInterface {

    @Override
    public void handle(CommandContext c) {

        if(c.getCommandParameters().isEmpty() && c.getEvent().getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if (Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                Constants.getMusicManager(c).scheduler.getQueue().clear();
                Constants.getMusicManager(c).player.stopTrack();
                Constants.getMusicManager(c).player.setPaused(false);
                c.getEvent().getChannel().sendTyping().queue();
                c.getEvent().getChannel().sendMessage("The queue has been cleared successfully!").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
            }
            else {
                c.getEvent().getChannel().sendTyping().queue();
                c.getEvent().getChannel().sendMessage("You have to be in the same voice channel as me to clear the queue.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "clear";
    }
}
