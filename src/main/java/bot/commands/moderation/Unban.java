package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.requests.RestAction;

public class Unban implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.BAN_MEMBERS)) {

            if(c.getCommandParameters().size() < 1) {
                Constants.setEmbed(c.getEvent(), "Remove Ban Command Usage", "Usage: "+ Constants.getCurrentPrefix(c) +"unban [user mention or ID]");
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
                                    Constants.setEmbed(c.getEvent(), "✅ Success! ✅", user.getAsMention() + " has been unbanned successfully!");
                                }
                                else {
                                    Constants.setEmbed(c.getEvent(), "❌ Failed to Unban ❌", "Cannot unban users that aren't on the banlist.");
                                }
                            };
                            //Retrieves the ban list then runs the code inside the consumer to unban the user.
                            c.getGuild().retrieveBanList().queue(banListConsumer);
                        }
                    }, (error) -> Constants.setEmbed(c.getEvent(), "❌ Failed to Unban ❌", "Invalid user. Please try executing the command again with a valid user mention or user ID."));
                } 
                catch (Exception e) {
                    Constants.setEmbed(c.getEvent(), "❌ Failed to Unban ❌", "Bad format. Please try executing the command again with a valid user mention or user ID.");
                }
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "unban";
    }
}