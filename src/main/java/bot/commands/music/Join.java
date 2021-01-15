package bot.commands.music;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

import java.util.Objects;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        AudioManager manager = c.getGuild().getAudioManager();

        if(c.getCommandParameters().isEmpty() && c.getMember().hasPermission(Permission.VOICE_CONNECT)) {

            if(!manager.isConnected()) {

                if(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel() != null) {
                    manager.openAudioConnection(Objects.requireNonNull(c.getMember().getVoiceState()).getChannel());
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