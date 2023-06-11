/*
 * Copyright 2020 Md Rafi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bot.commands.slash.general;

import bot.commands.SlashCommandInterface;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class help implements SlashCommandInterface {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if(event.getChannelType().equals(ChannelType.TEXT)) {
            event.getJDA().retrieveApplicationInfo().queue(botOwner -> {
                EmbedBuilder info = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("**The Monitor ™ Bot Information**")
                .setDescription("A multi-purpose Discord server bot in development. See below for available commands. Mention the bot ["+ event.getJDA().getSelfUser().getAsMention() +"] if you wish to manually run commands with the bot prefix.")
                .setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .addField("**Moderation**", "setPrefix, ticketSetup, invite, mute, unmute, purge, kick, ban, unban", true)
                .addField("**General**", "botInfo, serverInfo, ping, help", true)
                .addField("**Fun**", "roast, wholesome, simp, avatar, pp, rps, meme, emotes", true)
                .addField("**Music**", "join, leave, np, play, loopTrack, volume, pause, skip, queue, clear", true)
                .setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                event.replyEmbeds(info.build()).setEphemeral(true).queue();
                info.clear();
            });
        }
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "basic bot information and help";
    }
}