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

public class LoopTrack implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if (c.getCommandParameters().isEmpty() && c.getEvent().getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if (Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                final boolean trackRepeat = !Constants.getMusicManager(c).scheduler.trackLoop;
                Constants.getMusicManager(c).scheduler.trackLoop = trackRepeat;
                c.getEvent().getChannel().sendTyping().queue();

                if (trackRepeat) {
                    c.getEvent().getChannel().sendMessage("Looping the current song.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                }
                else {
                    c.getEvent().getChannel().sendMessage("Track looping has been disabled").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                }
            }
            else {
                c.getEvent().getChannel().sendTyping().queue();
                c.getEvent().getChannel().sendMessage("You have to be in the same voice channel as me to loop a song.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "loopTrack";
    }
}
