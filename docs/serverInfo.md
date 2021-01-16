---
theme: jekyll-theme-cayman
permalink: /serverinfo
---
# Server Info Command

## About
This command is used to display general information about a Discord server.

##Source Code
```java
package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class ServerInfo implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            /*
             * Code for the server info command. Consumer is used to create the embed with the retrieved owner inside.
             */
            Consumer<Member> owner = (o) -> {
                EmbedBuilder server = new EmbedBuilder();
                server.setColor(0x05055e);
                server.setTitle("**Server Information**");
                server.setDescription("Some basic information about the current server: " + c.getGuild().getName());
                server.setThumbnail(c.getGuild().getIconUrl());
                server.addField("**Server Name**", c.getGuild().getName(), true);
                server.addField("**Server Owner**", o.getAsMention(), true);
                server.addField("**Server Creation**", c.getGuild().getTimeCreated().format(DateTimeFormatter.ofPattern("'Date:' MMMM dd yyyy \n'Time:' h:mm a O")), true);
                server.addField("**Server Location**", c.getGuild().getRegionRaw().toUpperCase().replace("-", " ") , true);
                server.addField("**Member Count**", Integer.toString(c.getGuild().getMemberCount()), true);
                server.addField("**Role Count**", Integer.toString(c.getGuild().getRoles().size()), true);
                server.addField("**Emote Count**", Integer.toString(c.getGuild().getEmotes().size()), true);
                server.addField("**Boost Tier**", c.getGuild().getBoostTier().toString().replace("TIER_", "Level "), true);
                server.addField("**Boost Count**", Integer.toString(c.getGuild().getBoostCount()) , true);
                server.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(server.build()).queue();
                server.clear();
            };
            /*
             * This line of code runs all the code inside the success consumer, thus creating the embed.
             */
            c.getGuild().retrieveOwner().queue(owner);
        }
    }

    @Override
    public String getName() {
        return "serverInfo";
    }
}
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)