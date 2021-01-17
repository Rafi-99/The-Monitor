package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import bot.handlers.event.FunUtility;
import net.dv8tion.jda.api.EmbedBuilder;

public class Penis implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        int length = (int) (Math.random()*13);
        StringBuilder growth = new StringBuilder();

        if(c.getCommandParameters().size() == 1 && !c.getMessage().getMentionedUsers().isEmpty()) {
            for (int i = 0; i < length; i++) {
                String inches = "=";
                growth.append(inches);
            }
            String maleObject = "8" + growth + "D";
            FunUtility.setEmbed(c.getEvent(), "Penis Generator", c.getMessage().getMentionedMembers().get(0).getEffectiveName() + "'s penis \n"+ maleObject);
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"pp [user mention] to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "pp";
    }
}