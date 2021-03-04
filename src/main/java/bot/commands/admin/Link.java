package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

import net.dv8tion.jda.api.Permission;

public class Link implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals("398215411998654466")) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(c.getEvent().getJDA().getInviteUrl(Permission.ADMINISTRATOR)).reference(c.getMessage()).mentionRepliedUser(false).queue();
        }
    }

    @Override
    public String getName() {
        return "link";
    }
}