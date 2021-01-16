---
theme: jekyll-theme-cayman
permalink: /rps
---
# Rock Paper Scissors Command

## About
A game of Rock Paper Scissors! Test your luck against the bot.

##Code
```java
package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.FunUtility;

public class RockPaperScissors implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        int rps = (int) (Math.random() * 3) + 1;

        if(c.getCommandParameters().size() == 1) {
            FunUtility.rpsGame(c.getEvent(), c.getCommandParameters().toArray(new String[0]), rps);
        }
        else {
            c.getChannel().sendTyping().queue();
            c.getChannel().sendMessage("Type in "+ Monitor.prefix +"rps [rock/paper/scissors] to use this command!").queue();
        }
    }

    @Override
    public String getName() {
        return "rps";
    }
}
```

```java
    public static void rpsGame(GuildMessageReceivedEvent event, String [] fun, int rps) {
        if(rps == 1) {

            if(fun[0].equalsIgnoreCase("Rock")) {
                rpsEmbed(event, "The computer was: Rock :moyai: \nIt's a tie!");
            }
            else if(fun[0].equalsIgnoreCase("Paper")) {
                rpsEmbed(event, "The computer was: Rock :moyai: \nYou won!");
            }
            else if (fun[0].equalsIgnoreCase("Scissors")) {
                rpsEmbed(event, "The computer was: Rock :moyai: \nYou lost!");
            }
            else {
                rpsError(event);
            }
        }
        else if(rps == 2) {

            if(fun[0].equalsIgnoreCase("Paper")) {
                rpsEmbed(event, "The computer was: Paper :newspaper: \nIt's a tie!");
            }
            else if(fun[0].equalsIgnoreCase("Scissors")) {
                rpsEmbed(event, "The computer was: Paper :newspaper: \nYou won!");
            }
            else if(fun[0].equalsIgnoreCase("Rock")) {
                rpsEmbed(event, "The computer was: Paper :newspaper: \nYou lost!");
            }
            else {
                rpsError(event);
            }
        }
        else if (rps == 3) {

            if(fun[0].equalsIgnoreCase("Scissors")) {
                rpsEmbed(event, "The computer was: Scissors :scissors: \nIt's a tie!");
            }
            else if(fun[0].equalsIgnoreCase("Rock")) {
                rpsEmbed(event, "The computer was: Scissors :scissors: \nYou won!");
            }
            else if(fun[0].equalsIgnoreCase("Paper")) {
                rpsEmbed(event, "The computer was: Scissors :scissors: \nYou lost!");
            }
            else {
                rpsError(event);
            }
        }
    }

    public static void rpsError(GuildMessageReceivedEvent event) {
        EmbedBuilder rpsErrorBuilder = new EmbedBuilder();
        rpsErrorBuilder.setColor(0x05055e);
        rpsErrorBuilder.setTitle("Rock Paper Scissors");
        rpsErrorBuilder.setDescription("Invalid input.");
        rpsErrorBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(rpsErrorBuilder.build()).queue();
        rpsErrorBuilder.clear();
    }

    public static void rpsEmbed(GuildMessageReceivedEvent event, String description) {
        EmbedBuilder rpsEmbedBuilder = new EmbedBuilder();
        rpsEmbedBuilder.setColor(0x05055e);
        rpsEmbedBuilder.setTitle("Rock Paper Scissors");
        rpsEmbedBuilder.setDescription(description);
        rpsEmbedBuilder.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(rpsEmbedBuilder.build()).queue();
        rpsEmbedBuilder.clear();
    }
```

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)