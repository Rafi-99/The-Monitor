package bot.commands.moderation;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.driver.Monitor;
import bot.handlers.utilities.Constants;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.RestAction;

public class Kick implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if(c.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            if(c.getCommandParameters().size() < 1) {
                Constants.setEmbed(c.getEvent(), "Kick Command Usage", "Usage: "+ Monitor.prefix +"kick [user mention or ID]");
            }
            else {
                try {
                    RestAction<Member> kickEvent = c.getGuild().retrieveMemberById(c.getCommandParameters().get(0).replace("<@!", "").replace("<@", "").replace(">", ""));
                    kickEvent.queue((user) -> {
                        if(c.getEvent().getGuild().getMemberById(user.getId()) != null) {
                            c.getEvent().getGuild().kick(user).queue();
                            Constants.setEmbed(c.getEvent(), "✅ Success! ✅", user.getAsMention() +" has been kicked successfully!");
                        }
                    }, (error) -> Constants.setEmbed(c.getEvent(), "❌ Failed to Kick ❌", "Invalid user. Users not in the guild can't be kicked. Please try executing the command again with a valid user mention or user ID."));
                } 
                catch (Exception e) {
                    Constants.setEmbed(c.getEvent(), "❌ Failed to Kick ❌", "Bad format. Please try executing the command again with a valid user mention or user ID.");
                }
            }
        }
        else {
            Constants.accessDenied(c.getEvent());
        }
    }

    @Override
    public String getName() {
        return "kick";
    }
}