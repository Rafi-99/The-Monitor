---
theme: jekyll-theme-cayman
permalink: /meme
---
# Meme Command

## About
This command gets a random meme from [Reddit.](https://www.reddit.com/r/memes) I use my own [API](https://meme-api-node-js.herokuapp.com/memes) to get the memes.

## Source Code
```java
package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

import java.time.Instant;

import me.duncte123.botcommons.web.WebUtils;

import net.dv8tion.jda.api.EmbedBuilder;

public class Meme implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            WebUtils.ins.getJSONObject("https://meme-api-node-js.herokuapp.com/memes").async((json) -> {
                String title = json.get("data").get("title").asText();
                String url = json.get("data").get("url").asText();
                String image = json.get("data").get("image").asText();

                EmbedBuilder meme = new EmbedBuilder();
                meme.setColor(0x05055e);
                meme.setTitle(title, url);
                meme.setImage(image);
                meme.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                meme.setTimestamp(Instant.now());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(meme.build()).queue();
                meme.clear();
            });
        }
    }

    @Override
    public String getName() {
        return "meme";
    }
}
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)
