package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;

public class Simp implements CommandInterface {
    
    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1) {
            EmbedBuilder simp = new EmbedBuilder();
            simp.setColor(0x05055e);
            simp.setTitle("Such Wholesome");
            simp.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
            simp.setDescription(c.getCommandParameters().get(0) + " is " + (int) (Math.random() * 101) + "% simp.");
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage(simp.build()).queue();
            simp.clear();
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"simp and mention someone to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "simp";
    }
}