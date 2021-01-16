---
theme: jekyll-theme-cayman
permalink: /music
---
# Music Commands

## About
Of all the music commands, I'm showcasing the play command. My bot is able to play links from SoundCloud and YouTube.

## Source Code
```java
package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.music.PlayerManager;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play implements CommandInterface {

    private final YouTube youTube;

    public Play() {
        YouTube temp = null;

        try {
            temp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
            .setApplicationName("Monitor Discord Bot")
            .build();
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
            .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
            .setKey(System.getenv("YouTube_KEY"))
            .execute()
            .getItems();

            if(!result.isEmpty()) {
                String videoID = result.get(0).getId().getVideoId();
                String playlistID = result.get(0).getId().getPlaylistId();

                if(result.get(0).getId().getKind().equals("youtube#video")) {
                    return "https://www.youtube.com/watch?v=" + videoID;
                }
                else if(result.get(0).getId().getKind().equals("youtube#playlist")) {
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
        AudioManager manager = c.getGuild().getAudioManager();

        if(c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(c.getCommandParameters().isEmpty()) {
                EmbedBuilder play = new EmbedBuilder();
                play.setColor(0x05055e);
                play.setTitle("Play Command Usage");
                play.setDescription(Monitor.prefix +"play [insert link or search query here]");
                play.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(play.build()).queue();
                play.clear();
            }
            else {

                if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && c.getMember().getVoiceState().getChannel() == manager.getConnectedChannel() && manager.isConnected()) {
                    String link = String.join(" ", c.getCommandParameters());

                    if(!isUrl(link)) {
                        String ytSearch = youtubeSearch(link);

                        if(ytSearch == null) {
                            c.getChannel().sendTyping().queue();
                            c.getChannel().sendMessage("Sorry, YouTube returned no results for your query.").queue();
                            return;
                        }
                        link = ytSearch;
                        PlayerManager.getInstance().loadAndPlay(c.getChannel(), link);
                    }
                    else {
                        String formatLink = link.substring(link.indexOf("h"));
                        PlayerManager.getInstance().loadAndPlay(c.getChannel(), formatLink);
                    }
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("You have to be in the same voice channel as me to play anything. Please use the join command to summon me.").queue();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "play";
    }
}
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)