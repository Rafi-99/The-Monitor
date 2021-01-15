package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;

public class Wholesome implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            EmbedBuilder wholesome = new EmbedBuilder();
            wholesome.setColor(0x05055e);
            wholesome.setTitle("Such Wholesome");
            wholesome.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
            wholesome.setDescription(c.getCommandParameters().get(0) + " is " + (int) (Math.random() * 101) + "% wholesome.");
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(wholesome.build()).queue();
            wholesome.clear();
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"wholesome and mention someone to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "wholesome";
    }
}