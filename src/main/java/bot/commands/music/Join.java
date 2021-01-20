package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.music.MusicUtility;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;

public class Join implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(!MusicUtility.getAudioManager(c.getEvent()).isConnected()) {

                if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null) {
                    MusicUtility.getAudioManager(c.getEvent()).openAudioConnection(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel());
                    String name = Objects.requireNonNull(c.getMember().getVoiceState().getChannel()).toString().replace("VC:", "");
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("Successfully connected to: " + name.substring(name.indexOf(""), name.indexOf("("))).queue();
                }
                else {
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage("You have to be in a voice channel first!").queue();
                }
            }
            else {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("I am already connected to a voice channel!").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "join";
    }
}