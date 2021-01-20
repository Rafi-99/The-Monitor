package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.music.MusicUtility;

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

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == MusicUtility.getAudioManager(c).getConnectedChannel() && MusicUtility.getAudioManager(c).isConnected()) {

                if (MusicUtility.getQueue(c).isEmpty()) {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("The queue is empty.").queue();
                }
                else {
                    int minQueueView = Math.min(MusicUtility.getQueue(c).size(), 30);
                    List<AudioTrack> tracks = new ArrayList<>(MusicUtility.getQueue(c));

                    EmbedBuilder queue = new EmbedBuilder();
                    queue.setColor(0x05055e);
                    queue.setTitle("**Current Queue: **" + MusicUtility.getQueue(c).size() + " **Tracks**");

                    for (int i = 0; i < minQueueView; i++) {
                        AudioTrackInfo trackInfo = tracks.get(i).getInfo();
                        queue.appendDescription(String.format("%s - %s\n", trackInfo.title, trackInfo.author));
                    }
                    queue.setThumbnail(Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    queue.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(queue.build()).queue();
                    queue.clear();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to view the queue.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "queue";
    }
}