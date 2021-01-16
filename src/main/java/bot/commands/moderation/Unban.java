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

public class Unban implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.BAN_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                EmbedBuilder unban = new EmbedBuilder();
                unban.setColor(0x05055e);
                unban.setTitle("Remove Ban Command Usage");
                unban.setDescription("Usage: "+ Monitor.prefix +"unban [user mention or ID]");
                unban.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                c.getChannel().sendTyping().queue();
                c.getChannel().sendMessage(unban.build()).queue();
                unban.clear();
            }
            else {

                try {
                    RestAction<User> removeBanEvent = c.getJDA().retrieveUserById(c.getCommandParameters().get(0).replace("<@!", "").replace("<@", "").replace(">", ""));
                    removeBanEvent.queue((user) -> {

                        if(user != null) {
                            Consumer<? super List<Ban>> banListConsumer = (b) -> {

                                List<User> banIDList = new ArrayList<>(b.size());
                                for(int i=0; i < b.size(); i++) {
                                    banIDList.add(i, b.get(i).getUser());
                                }

                                if(banIDList.contains(user)) {
                                    c.getGuild().unban(user).queue();
                                    EmbedBuilder unbanSuccess = new EmbedBuilder();
                                    unbanSuccess.setColor(0x05055e);
                                    unbanSuccess.setTitle("✅ Success! ✅");
                                    unbanSuccess.setDescription(user.getAsMention() + " has been unbanned successfully!");
                                    unbanSuccess.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                    c.getChannel().sendTyping().queue();
                                    c.getChannel().sendMessage(unbanSuccess.build()).queue();
                                    unbanSuccess.clear();
                                }
                                else {
                                    EmbedBuilder unbanError = new EmbedBuilder();
                                    unbanError.setColor(0x05055e);
                                    unbanError.setTitle("❌ Failed to Unban ❌");
                                    unbanError.setDescription("Cannot unban users that aren't on the banlist.");
                                    unbanError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                                    c.getChannel().sendTyping().queue();
                                    c.getChannel().sendMessage(unbanError.build()).queue();
                                    unbanError.clear();
                                }
                            };
                            //Retrieves the ban list then runs the code inside the consumer to unban the user.
                            c.getGuild().retrieveBanList().queue(banListConsumer);
                        }
                    }, (error) -> {
                        EmbedBuilder unbanError = new EmbedBuilder();
                        unbanError.setColor(0x05055e);
                        unbanError.setTitle("❌ Failed to Unban ❌");
                        unbanError.setDescription("Invalid user. Please try executing the command again with a valid user mention or user ID.");
                        unbanError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage(unbanError.build()).queue();
                        unbanError.clear();
                    });
                } 
                catch (Exception e) {
                    EmbedBuilder unbanError = new EmbedBuilder();
                    unbanError.setColor(0x05055e);
                    unbanError.setTitle("❌ Failed to Unban ❌");
                    unbanError.setDescription("Bad format. Please try executing the command again with a valid user mention or user ID.");
                    unbanError.setFooter("The Monitor ™ | Powered by Java", Monitor.myBot.getSelfUser().getEffectiveAvatarUrl());
                    c.getChannel().sendTyping().queue();
                    c.getChannel().sendMessage(unbanError.build()).queue();
                    unbanError.clear();
                }
            }
        }
        else {
            ModerationUtility.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "unban";
    }
}