package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class TicketSetup implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            EmbedBuilder ticket = new EmbedBuilder()
            .setColor(0x05055e)
            .setTitle("**Create a Support Ticket**")
            .setDescription("React with 📩 to create a new ticket.")
            .setFooter("The Monitor ™ | © 2021", c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());
            c.getChannel().sendMessageEmbeds(ticket.build()).queue(t -> t.addReaction("📩").queue());
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