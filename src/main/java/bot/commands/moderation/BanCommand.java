package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.event.ModerationUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.requests.RestAction;

public class BanCommand implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.BAN_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                EmbedBuilder ban = new EmbedBuilder();
                ban.setColor(0x05055e);
                ban.setTitle("Ban Command Usage");
                ban.setDescription("Usage: "+ Monitor.prefix +"ban [user mention or ID]");
                ban.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(ban.build()).queue();
                ban.clear();
            }
            else {

                try {
                    RestAction<User> banEvent = c.getJDA().retrieveUserById(c.getCommandParameters().get(0).replace("<@!", "").replace("<@", "").replace(">", ""));
                    banEvent.queue((user) -> {

                        if (user != null) {
                            Consumer<? super List<Ban>> banListConsumer = (b) -> {

                                List<User> banIDList = new ArrayList<>(b.size());
                                for(int i=0; i < b.size(); i++) {
                                    banIDList.add(i, b.get(i).getUser());
                                }

                                if (banIDList.contains(user)) {
                                    EmbedBuilder banError = new EmbedBuilder();
                                    banError.setColor(0x05055e);
                                    banError.setTitle("❌ User Already Banned ❌");
                                    banError.setDescription("Cannot ban users that already have a ban.");
                                    banError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                    c.getChannel().sendTyping().queue();
                                    c.getChannel().sendMessage(banError.build()).queue();
                                    banError.clear();
                                }
                                else {
                                    c.getGuild().ban(user, 7).queue();
                                    EmbedBuilder banSuccess = new EmbedBuilder();
                                    banSuccess.setColor(0x05055e);
                                    banSuccess.setTitle("✅ Success! ✅");
                                    banSuccess.setDescription(user.getAsMention() + " has been banned successfully!");
                                    banSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                    c.getChannel().sendTyping().queue();
                                    c.getChannel().sendMessage(banSuccess.build()).queue();
                                    banSuccess.clear();
                                }
                            };
                            //Retrieves the ban list then runs the code inside the consumer to ban the user.
                            c.getGuild().retrieveBanList().queue(banListConsumer);
                        }
                    }, (error) -> {
                        EmbedBuilder banError = new EmbedBuilder();
                        banError.setColor(0x05055e);
                        banError.setTitle("❌ Invalid Argument ❌");
                        banError.setDescription("Users that are no longer in a guild cannot be mentioned. Please try executing the command again with a valid user mention or user ID.");
                        banError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(banError.build()).queue();
                        banError.clear();
                    });
                }
                catch (Exception e) {
                    EmbedBuilder banError = new EmbedBuilder();
                    banError.setColor(0x05055e);
                    banError.setTitle("❌ Invalid Argument ❌");
                    banError.setDescription("Users that are no longer in a guild cannot be mentioned. Please try executing the command again with a valid user mention or user ID.");
                    banError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(banError.build()).queue();
                    banError.clear();
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "ban";
    }
}