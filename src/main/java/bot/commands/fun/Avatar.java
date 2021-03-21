package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.EmbedBuilder;

public class Avatar implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1 && !c.getMessage().getMentionedUsers().isEmpty()) {
            EmbedBuilder avatar = new EmbedBuilder()
            .setColor(0x05055e)
            .setTitle("Avatar")
            .setDescription(c.getMessage().getMentionedUsers().get(0).getName())
            .setImage(c.getMessage().getMentionedUsers().get(0).getEffectiveAvatarUrl() + "?size=4096")
            .setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(avatar.build()).reference(c.getMessage()).mentionRepliedUser(false).queue();
            avatar.clear();
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Constants.getCurrentPrefix(c) +"avatar and mention a user to view their avatar!").reference(c.getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "avatar";
    }
}