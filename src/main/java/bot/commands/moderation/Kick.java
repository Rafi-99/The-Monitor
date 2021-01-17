package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.RestAction;

public class Kick implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.KICK_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                EmbedBuilder kick = new EmbedBuilder();
                kick.setColor(0x05055e);
                kick.setTitle("Kick Command Usage");
                kick.setDescription("Usage: "+ Monitor.prefix +"kick [user mention or ID]");
                kick.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(kick.build()).queue();
                kick.clear();
            }
            else {
                try {
                    RestAction<Member> kickEvent = c.getGuild().retrieveMemberById(c.getCommandParameters().get(0).replace("<@!", "").replace("<@", "").replace(">", ""));
                    kickEvent.queue((user) -> {
                        if(c.getEvent().getGuild().getMemberById(user.getId()) != null) {
                            c.getEvent().getGuild().kick(user).queue();
                            EmbedBuilder kicked = new EmbedBuilder();
                            kicked.setColor(0x05055e);
                            kicked.setTitle("✅ Success! ✅");
                            kicked.setDescription(user.getAsMention() +" has been kicked successfully!");
                            kicked.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                            c.getChannel().sendTyping().queue();
                            c.getChannel().sendMessage(kicked.build()).queue();
                            kicked.clear();
                        }
                    }, (error) -> {
                        EmbedBuilder kickError = new EmbedBuilder();
                        kickError.setColor(0x05055e);
                        kickError.setTitle("❌ Failed to Kick ❌");
                        kickError.setDescription("Invalid user. Users not in the guild can't be kicked. Please try executing the command again with a valid user mention or user ID.");
                        kickError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(kickError.build()).queue();
                        kickError.clear();
                    });
                } 
                catch (Exception e) {
                    EmbedBuilder kickError = new EmbedBuilder();
                    kickError.setColor(0x05055e);
                    kickError.setTitle("❌ Failed to Kick ❌");
                    kickError.setDescription("Bad format. Please try executing the command again with a valid user mention or user ID.");
                    kickError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(kickError.build()).queue();
                    kickError.clear();
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }

    }

    @Override
    public String getName() {
        return "kick";
    }
}