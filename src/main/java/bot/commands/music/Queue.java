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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class Queue implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {

                if (Constants.getQueue(c).isEmpty()) {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("The queue is empty.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                }
                else {
                    int minQueueView = Math.min(Constants.getQueue(c).size(), 30);
                    List<AudioTrack> tracks = new ArrayList<>(Constants.getQueue(c));

                    EmbedBuilder queue = new EmbedBuilder();
                    queue.setColor(0x05055e);
                    queue.setTitle("**Current Queue: **" + Constants.getQueue(c).size() + " **Tracks**");

                    for (int i = 0; i < minQueueView; i++) {
                        AudioTrackInfo trackInfo = tracks.get(i).getInfo();
                        queue.appendDescription(String.format("%s - %s\n", trackInfo.title, trackInfo.author));
                    }
                    queue.setThumbnail(c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                    queue.setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessageEmbeds(queue.build()).reference(c.getMessage()).mentionRepliedUser(false).queue();
                    queue.clear();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to view the queue.").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "queue";
    }
}