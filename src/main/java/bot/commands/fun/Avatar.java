package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;

public class Avatar implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1 && !c.getMessage().getMentionedUsers().isEmpty()) {
            EmbedBuilder avatar = new EmbedBuilder();
            avatar.setColor(0x05055e);
            avatar.setTitle("Avatar");
            avatar.setDescription(c.getMessage().getMentionedUsers().get(0).getName());
            avatar.setImage(c.getMessage().getMentionedUsers().get(0).getEffectiveAvatarUrl() + "?size=4096");
            avatar.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(avatar.build()).queue();
            avatar.clear();
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"avatar and mention a user to view their avatar!").queue();
        }
    }

    @Override
    public String getName() {
        return "avatar";
    }
}