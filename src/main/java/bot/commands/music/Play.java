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
import bot.handlers.music.PlayerManager;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import net.dv8tion.jda.api.Permission;

public class Play implements CommandInterface {

    private final YouTube youTube;

    public Play() {
        YouTube temp = null;

        try {
            temp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName("Monitor Discord Bot").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        youTube = temp;
    }

    private String youtubeSearch(String searchInput) {
        try {
            List<SearchResult> result = youTube.search()
            .list("id,snippet")
            .setQ(searchInput)
            .setMaxResults(1L)
            .setFields("items(id/kind,id/videoId,id/playlistId,snippet/title,snippet/thumbnails/default/url)")
            .setKey(System.getenv("YouTube_KEY"))
            .execute()
            .getItems();

            if (!result.isEmpty()) {
                String videoID = result.get(0).getId().getVideoId();
                String playlistID = result.get(0).getId().getPlaylistId();

                if (result.get(0).getId().getKind().equals("youtube#video")) {
                    return "https://www.youtube.com/watch?v=" + videoID;
                }
                else if (result.get(0).getId().getKind().equals("youtube#playlist")) {
                    return "https://www.youtube.com/playlist?list=" + playlistID;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void handle(CommandContext c) {
        if (c.getEvent().getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if (c.getCommandParameters().isEmpty()) {
                Constants.setEmbed(c.getEvent(), "Play Command Usage :musical_note:", Constants.getCurrentPrefix(c) + "play [insert link or search query here]");
            }
            else {

                if (Objects.requireNonNull(c.getEvent().getMember().getVoiceState()).getChannel() != null  && c.getEvent().getMember().getVoiceState().getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {
                    String link = String.join(" ", c.getCommandParameters());

                    if (!isUrl(link)) {
                        String ytSearch = youtubeSearch(link);

                        if (ytSearch == null) {
                            c.getEvent().getChannel().sendTyping().queue();
                            c.getEvent().getChannel().sendMessage("Sorry, YouTube returned no results for your query.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                            return;
                        }
                        link = ytSearch;
                        PlayerManager.getInstance().loadAndPlay(c.getEvent().getChannel().asTextChannel(), link);
                    }
                    else {
                        String formatLink = link.substring(link.indexOf("h"));
                        PlayerManager.getInstance().loadAndPlay(c.getEvent().getChannel().asTextChannel(), formatLink);
                    }
                }
                else {
                    c.getEvent().getChannel().sendTyping().queue();
                    c.getEvent().getChannel().sendMessage("You have to be in the same voice channel as me to play anything. Please use the join command to summon me.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "play";
    }
}
