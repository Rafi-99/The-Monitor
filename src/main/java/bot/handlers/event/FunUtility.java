package bot.handlers.event;

import bot.driver.Monitor;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class FunUtility {

    public static String [] roasts = {
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
}