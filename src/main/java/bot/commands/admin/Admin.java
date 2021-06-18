package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

import net.dv8tion.jda.api.EmbedBuilder;

public class Admin implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty() && c.getMember().getId().equals("398215411998654466")) {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("The information has been sent to your DM!").reference(c.getMessage()).mentionRepliedUser(false).queue();
            c.getAuthor().openPrivateChannel().queue(privateChannel -> {
                EmbedBuilder adminInfo = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("Admin Tools")
                .setDescription("Commands available for your usage: \n```test -s \ntest -t \nadmin \nrestart \nlink \nguilds```")
                .setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
                privateChannel.sendTyping().queue();
                privateChannel.sendMessageEmbeds(adminInfo.build()).queue();
                adminInfo.clear();
                privateChannel.close().queue();
            });
        }
    }

    @Override
    public String getName() {
        return "admin";
    }
}