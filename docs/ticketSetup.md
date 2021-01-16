---
theme: jekyll-theme-cayman
permalink: /ticketsetup
---
# Ticket Setup Command

## About
This command allows Discord server members to create a support ticket and converse with server staff in a private channel.

## Source Code
```java
package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

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
            ticket.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
            c.getChannel().sendMessage(ticket.build()).queue(t -> t.addReaction("📩").queue());
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "ticketSetup";
    }
}
```

```java
    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(event.getChannel().getId().equals("709557615708864522") && event.getReactionEmote().getAsReactionCode().equals("📩") && !(event.getUser().isBot())) {
            event.getReaction().removeReaction(event.getUser()).queue();
            event.getGuild().createTextChannel("Support Ticket")
            .addPermissionOverride(Objects.requireNonNull(event.getGuild().getRoleById("709259200651591742")), null, EnumSet.of(Permission.VIEW_CHANNEL))
            .addPermissionOverride(Objects.requireNonNull(event.getGuild().getRoleById("710398399085805599")), EnumSet.of(Permission.VIEW_CHANNEL), null)
            .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_ATTACH_FILES), null)
            .queue(support -> {
                support.sendMessage("Welcome to support! " + event.getMember().getAsMention()).queue();
                support.sendMessage("A staff member will arrive to assist you shortly. " + Objects.requireNonNull(event.getGuild().getRoleById("710398399085805599")).getAsMention()).queue();
            });
        }
    }
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)
