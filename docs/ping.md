---
theme: jekyll-theme-cayman
permalink: /ping
---
# Ping Command

## About
Displays the ping!

##Source Code
```java
package bot.commands.general;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

public class Ping implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().isEmpty()) {
            c.getChannel().sendTyping().queue();
            c.getJDA().getRestPing().queue((ping) -> c.getChannel().sendMessage("Bot Latency: "+ ping +" ms | Discord API Latency: "+ c.getJDA().getGatewayPing() +" ms").queue());
        }
    }

    @Override
    public String getName() {
        return "ping";
    }
}
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)