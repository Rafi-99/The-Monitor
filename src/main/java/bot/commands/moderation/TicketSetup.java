package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class TicketSetup implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            EmbedBuilder ticket = new EmbedBuilder();
            ticket.setColor(0x05055e);
            ticket.setTitle("**Create a Support Ticket**");
            ticket.setDescription("React with 📩 to create a new ticket.");
            ticket.setFooter("The Monitor ™ | © 2021", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
            c.getChannel().sendMessage(ticket.build()).queue(t -> t.addReaction("📩").queue());
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "ticketSetup";
    }
}