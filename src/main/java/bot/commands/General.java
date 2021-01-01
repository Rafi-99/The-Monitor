package bot.commands;

import bot.driver.Monitor;

import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class General extends ListenerAdapter {

     @Override
     public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

          String [] general = event.getMessage().getContentRaw().split("\\s+");
          /* 
           * Code for the bot info command. Lambda is used to create the embed and 
           * get the retrieved owner name and avatar inside the footer of the embed.
           */
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
          /* 
           * Code for the server info command. Consumer is used to create the embed with the retrieved owner inside.
           */ 
          else if(general[0].equalsIgnoreCase(Monitor.prefix + "serverInfo") && general.length == 1) {
               Consumer <Member> owner = (o) -> { 
                    EmbedBuilder server = new EmbedBuilder();
                    server.setColor(0x05055e);
                    server.setTitle("**Server Information**");
                    server.setDescription("Some basic information about the current server: " + event.getGuild().getName());
                    server.setThumbnail(event.getGuild().getIconUrl());
                    server.addField("**Server Name**", event.getGuild().getName(), true);     
                    server.addField("**Server Owner**", o.getAsMention(), true);
                    server.addField("**Server Creation**", event.getGuild().getTimeCreated().getMonthValue() +"/"+ event.getGuild().getTimeCreated().getDayOfMonth() +"/"+ event.getGuild().getTimeCreated().getYear(), true);
                    server.addField("**Server Location**", event.getGuild().getRegionRaw().toUpperCase().replace("-", " ") , true);
                    server.addField("**Member Count**", Integer.toString(event.getGuild().getMemberCount()), true);
                    server.addField("**Role Count**", Integer.toString(event.getGuild().getRoles().size()), true);
                    server.addField("**Emote Count**", Integer.toString(event.getGuild().getEmotes().size()), true);
                    server.addField("**Boost Tier**", event.getGuild().getBoostTier().toString().replace("TIER_", "Level "), true);
                    server.addField("**Boost Count**", Integer.toString(event.getGuild().getBoostCount()) , true);
                    server.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(server.build()).queue();
                    server.clear();   
               };
               /* 
                * This line of code runs all the code inside the success consumer, thus creating the embed.
                */
               event.getGuild().retrieveOwner().queue(owner);     
          }     
          else if(general[0].equalsIgnoreCase(Monitor.prefix + "ping") && general.length == 1) {
               event.getChannel().sendTyping().queue();
               event.getChannel().sendMessage("Pinging...").queue(p -> 
               { 
                    long ping = event.getMessage().getTimeCreated().until(p.getTimeCreated(), ChronoUnit.MILLIS);
                    p.editMessage("Bot Latency: "+ ping + " ms | Discord API Latency: "+ event.getJDA().getGatewayPing() + " ms").queue();
               });
          }
     }
}