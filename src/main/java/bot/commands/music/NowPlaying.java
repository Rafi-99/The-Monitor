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

import java.time.Year;
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class NowPlaying implements CommandInterface {

    @Override
    public void handle(CommandContext c) {

        if (c.getCommandParameters().isEmpty() && c.getEvent().getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if (Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {

                if (Constants.getMusicManager(c).player.getPlayingTrack() == null) {
                    c.getEvent().getChannel().sendTyping().queue();
                    c.getEvent().getChannel().sendMessage("Nothing is being played currently.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                    return;
                }

                long currentPositionMillis = Constants.getMusicManager(c).player.getPlayingTrack().getPosition();
                long totalDurationMillis = Constants.getMusicManager(c).player.getPlayingTrack().getDuration();
                long currentPositionMinutes = (currentPositionMillis / 1000) / 60;
                long currentPositionSeconds = (currentPositionMillis / 1000) % 60;
                long totalDurationMinutes = (totalDurationMillis / 1000) / 60;
                long totalDurationSeconds = (totalDurationMillis / 1000) % 60;
                String title = Constants.getMusicManager(c).player.getPlayingTrack().getInfo().title;
                String url = Constants.getMusicManager(c).player.getPlayingTrack().getInfo().uri;
                String videoID = Constants.getMusicManager(c).player.getPlayingTrack().getInfo().identifier;
                String message = "Track Progress: "+ currentPositionMinutes +"m "+ currentPositionSeconds +"s/"+ totalDurationMinutes +"m "+ totalDurationSeconds +"s";

                EmbedBuilder np = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("Now Playing: "+ title, url)
                .setThumbnail("https://img.youtube.com/vi/"+ videoID +"/default.jpg")
                .setDescription(message)
                .setFooter("The Monitor ™ | © " + Year.now().getValue(), c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                c.getEvent().getChannel().sendTyping().queue();
                c.getEvent().getChannel().sendMessageEmbeds(np.build()).setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                np.clear();
            }
            else {
                c.getEvent().getChannel().sendTyping().queue();
                c.getEvent().getChannel().sendMessage("You have to be in the same voice channel as me to see what's playing currently.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "np";
    }
}
