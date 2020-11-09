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
          
          String[] args = event.getMessage().getContentRaw().split("\\s+");

          // Code for the bot info command
          if (args[0].equalsIgnoreCase(Monitor.prefix + "botInfo") && args.length == 1) {
               // lambda to create the embed and have the retrieved owner name and avatar inside the footer
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
                    info.addField("**Fun**", "roast, wholesome, simp, avatar, pp", true);
                    info.addField("**Music**", "join, leave, np, play, pause, skip, queue, clear", true);
                    info.setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(info.build()).queue();
                    info.clear();
               });
          }
          // Code for the server info command 
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "serverInfo") && args.length == 1) { 
               // Consumer is used to create the embed with the retrieved owner
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
               // This line of code runs all the code in the success consumer, thus creating the embed
               event.getGuild().retrieveOwner().queue(owner);     
          }          
          else if(args[0].equalsIgnoreCase(Monitor.prefix + "ping")) {
               event.getChannel().sendMessage("Pinging...").queue(p -> 
               { 
                    long ping = event.getMessage().getTimeCreated().until(p.getTimeCreated(), ChronoUnit.MILLIS);
                    p.editMessage("Bot Latency: "+ ping + " ms | Discord API Latency: "+ event.getJDA().getGatewayPing() + " ms").queue();
               });
          }
     }
}