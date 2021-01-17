package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.event.FunUtility;

public class Admin implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals("398215411998654466")) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("The information has been sent to your DM!").queue();
            c.getAuthor().openPrivateChannel().queue(privateChannel -> FunUtility.setEmbed(c.getEvent(), "Admin Tools", "Commands available for your usage: \n```test -s \ntest -t \nadmin \nrestart \nlink \nguilds```"));
        }
    }

    @Override
    public String getName() {
        return "admin";
    }
}