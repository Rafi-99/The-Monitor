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

package bot.handlers.utilities;

import bot.commands.CommandContext;
import bot.handlers.music.GuildMusicManager;
import bot.handlers.music.PlayerManager;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Constants {

    // Global Variables
    public static final Map<Long, String> PREFIXES = new HashMap<>();

    // Fun Variables
    public static final String [] ROASTS = {
            "Your birth certificate is an apology letter from the abortion clinic.",
            "I fucking hate you LOL!", "Don't play hard to get when you are hard to want.",
            "At least my dad didn't leave me.",
            "You should put a condom on your head, because if you're going to act like a dick you better dress like one too.",
            "Who cares if girls look different without makeup? Your dick looks hella different when it's soft.",
            "Maybe if you eat all that makeup you will be beautiful on the inside.",
            "Your forehead is so big that I could use it to play Tic-Tac-Toe.",
            "I wonder if you'd be able to speak more clearly if your parents were second cousins instead of first.",
            "You're objectively unattractive.", "I'm not a nerd, I'm just smarter than you.",
            "If you're going to be two-faced, at least make one of them pretty.",
            "You just might be why the middle finger was invented in the first place.",
            "I'm not insulting you, I'm describing you.",
            "You must have been born on a highway since that's where most accidents happen.",
            "If laughter is the best medicine, your face must be curing the world!",
            "Two wrongs don't make a right, and your parents have once again proven that.",
            "My phone battery lasts longer than your relationships.",
            "It's better to be a smartass than to be a dumbass.", "Your face makes onions cry."
    };

    // Global Methods
    public static String getCurrentPrefix(CommandContext c) {
        return Constants.PREFIXES.get(c.getGuild().getIdLong());
    }

    public static void setEmbed(MessageReceivedEvent event, String title, String description) {
        EmbedBuilder embed = new EmbedBuilder()
        .setColor(0x05055e)
        .setTitle(title)
        .setDescription(description)
        .setFooter("The Monitor ™ | © " + Year.now().getValue(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(embed.build()).setMessageReference(event.getMessage()).mentionRepliedUser(false).queue();
        embed.clear();
    }

    // Music Methods
    public static AudioManager getAudioManager(CommandContext c) {
        return c.getGuild().getAudioManager();
    }

    public static GuildMusicManager getMusicManager(CommandContext c) {
        return PlayerManager.getInstance().getMusicManager(c.getGuild());
    }

    public static BlockingQueue<AudioTrack> getQueue(CommandContext c) {
        return PlayerManager.getInstance().getMusicManager(c.getGuild()).scheduler.getQueue();
    }

    // Moderation Methods
    public static void accessDenied(MessageReceivedEvent event) {
        Constants.setEmbed(event, "❌ Access Denied! ❌", "Sorry, you don't have the required permissions to use this command.");
    }

    // Fun Methods
    public static void rpsGame(MessageReceivedEvent event, String[] fun, int rps) {
        if (rps == 1) {

            if (fun[0].equalsIgnoreCase("Rock")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Rock :moyai: \nIt's a tie!");
            }
            else if (fun[0].equalsIgnoreCase("Paper")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Rock :moyai: \nYou won!");
            }
            else if (fun[0].equalsIgnoreCase("Scissors")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Rock :moyai: \nYou lost!");
            }
            else {
                setEmbed(event, "Rock Paper Scissors", "Invalid input.");
            }
        }
        else if (rps == 2) {

            if (fun[0].equalsIgnoreCase("Paper")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Paper :newspaper: \nIt's a tie!");
            }
            else if (fun[0].equalsIgnoreCase("Scissors")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Paper :newspaper: \nYou won!");
            }
            else if (fun[0].equalsIgnoreCase("Rock")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Paper :newspaper: \nYou lost!");
            }
            else {
                setEmbed(event, "Rock Paper Scissors", "Invalid input.");
            }
        }
        else if (rps == 3) {

            if (fun[0].equalsIgnoreCase("Scissors")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Scissors :scissors: \nIt's a tie!");
            }
            else if (fun[0].equalsIgnoreCase("Rock")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Scissors :scissors: \nYou won!");
            }
            else if (fun[0].equalsIgnoreCase("Paper")) {
                setEmbed(event, "Rock Paper Scissors", "The computer was: Scissors :scissors: \nYou lost!");
            }
            else {
                setEmbed(event, "Rock Paper Scissors", "Invalid input.");
            }
        }
    }
}
