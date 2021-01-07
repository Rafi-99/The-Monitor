---
theme: jekyll-theme-cayman
permalink: /botinfo
---
# Bot Info Command

## About
This command serves as a help command to help users know which bot commands are available to use. 

## Code
{% highlight java %}
String [] general = event.getMessage().getContentRaw().split("\\s+");

if (general[0].equalsIgnoreCase(Monitor.prefix + "botInfo") && general.length == 1) {
    Monitor.myBot.retrieveApplicationInfo().queue(botOwner -> {
        EmbedBuilder info = new EmbedBuilder();
        info.setColor(0x05055e);
        info.setTitle("**The Monitor ™ Bot Information**");
        info.setDescription("A multi-purpose Discord server bot in development.");
        info.setThumbnail(Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
        info.addField("**Default prefix**", "m!", true);
        info.addField("**Command Usage Example**", Monitor.prefix + "botInfo", false);
        info.addField("**Moderation**", "setPrefix, ticketSetup, createInvite, mute, unmute, purge, kick, ban, unban", true);
        info.addField("**General**", "botInfo, serverInfo, ping", true);
        info.addField("**Fun**", "roast, wholesome, simp, avatar, pp, rps, meme", true);
        info.addField("**Music**", "join, leave, np, play, loopTrack, pause, skip, queue, clear", true);
        info.setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(info.build()).queue();
        info.clear();
    });
}
{% endhighlight %}

## More From This Site
* [Home](https://rafi-99.github.io/The-Monitor/)
* [Bot Commands](https://rafi-99.github.io/The-Monitor/commands)