---
theme: jekyll-theme-cayman
permalink: /botinfo
---
# Bot Info Command

## About
This command serves as a help command to help users know which bot commands are available to use. 

## Source Code
```java
package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;

public class BotInfo implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        /*
         * Code for the bot info command. Lambda is used to create the embed and
         * get the retrieved owner name and avatar inside the footer of the embed.
         */
        if(c.getCommandParameters().isEmpty()) {
            Monitor.myBot.retrieveApplicationInfo().queue(botOwner -> {
                EmbedBuilder info = new EmbedBuilder();
                info.setColor(0x05055e);
                info.setTitle("**The Monitor ™ Bot Information**");
                info.setDescription("A multi-purpose Discord server bot in development.");
                info.setThumbnail(Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                info.addField("**Default prefix**", "m!", true);
                info.addField("**Command Usage Example**", Monitor.prefix + "botInfo", false);
                info.addField("**Moderation**", "setPrefix, ticketSetup, invite, mute, unmute, purge, kick, ban, unban", true);
                info.addField("**General**", "botInfo, serverInfo, ping", true);
                info.addField("**Fun**", "roast, wholesome, simp, avatar, pp, rps, meme", true);
                info.addField("**Music**", "join, leave, np, play, loopTrack, pause, skip, queue, clear", true);
                info.setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(info.build()).queue();
                info.clear();
            });
        }
    }

    @Override
    public String getName() {
        return "botInfo";
    }
}
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)