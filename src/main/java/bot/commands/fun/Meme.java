package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;

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
                meme.setFooter("The Monitor ™ | © 2021", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
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