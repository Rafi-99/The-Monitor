package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
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
                    queue.setThumbnail(Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    queue.setFooter("The Monitor ™ | © 2021", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(queue.build()).reference(c.getMessage()).mentionRepliedUser(false).queue();
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