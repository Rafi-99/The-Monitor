package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.Permission;

public class Invite implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {

            if(c.getCommandParameters().isEmpty()) {
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage("Invite created! Copy the link below and send it to someone!").queue();
                c.getChannel().sendMessage(c.getChannel().createInvite().setMaxAge(0).complete().getUrl()).queue();
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "invite";
    }
}