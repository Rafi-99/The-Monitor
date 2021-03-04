package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class Skip implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null && Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() == Constants.getAudioManager(c).getConnectedChannel() && Constants.getAudioManager(c).isConnected()) {

                if(Constants.getQueue(c).size() > 0) {
                    Constants.getMusicManager(c).scheduler.nextTrack();
                    
                    String title = Constants.getMusicManager(c).player.getPlayingTrack().getInfo().title;
                    String videoID = Constants.getMusicManager(c).player.getPlayingTrack().getInfo().identifier;

                    EmbedBuilder skip = new EmbedBuilder();
                    skip.setColor(0x05055e);
                    skip.setTitle("Track Skipped!");
                    skip.setThumbnail("https://img.youtube.com/vi/"+ videoID +"/default.jpg");
                    skip.setDescription("Now Playing: "+ title);
                    skip.setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(skip.build()).reference(c.getMessage()).mentionRepliedUser(false).queue();
                    skip.clear();
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("There are no songs left in the queue to skip.").reference(c.getMessage()).mentionRepliedUser(false).queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("You have to be in the same voice channel as me to skip songs.").reference(c.getMessage()).mentionRepliedUser(false).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "skip";
    }
}