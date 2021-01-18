package bot.handlers.event;

import bot.handlers.command.CommandManager;
import bot.driver.Monitor;

import java.util.EnumSet;
import java.util.Objects;
import javax.annotation.Nonnull;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener extends ListenerAdapter {

    private static final Logger botLogger = LoggerFactory.getLogger(EventListener.class);
    private final CommandManager botCommandManager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        botLogger.info("Bot is now online!");
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }

        String message = event.getMessage().getContentRaw();

        if(message.startsWith(Monitor.prefix)) {
            botCommandManager.handle(event, Monitor.prefix);
        }

        if(message.contains("https://") || message.contains("http://")) {
            Role staff = event.getGuild().getRoleById("710398399085805599");

            if(event.getChannel().getId().equals("709259200651591747") && !Objects.requireNonNull(event.getMember()).getRoles().contains(staff)) {
                event.getMessage().delete().complete();
            }

            if(message.contains("https://discord.gg/") && event.getGuild().getId().equals("709259200651591742") && !event.getChannel().getId().equals("717870479272312934")) {
                event.getMessage().delete().complete();
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(event.getChannel().getId().equals("709557615708864522") && event.getReactionEmote().getAsReactionCode().equals("📩") && !(event.getUser().isBot())) {
            event.getReaction().removeReaction(event.getUser()).queue();
            event.getGuild().createTextChannel("Support Ticket")
            .addPermissionOverride(Objects.requireNonNull(event.getGuild().getRoleById("709259200651591742")), null, EnumSet.of(Permission.VIEW_CHANNEL))
            .addPermissionOverride(Objects.requireNonNull(event.getGuild().getRoleById("710398399085805599")), EnumSet.of(Permission.VIEW_CHANNEL), null)
            .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_ATTACH_FILES), null)
            .queue(support -> {
                support.sendMessage("Welcome to support! " + event.getMember().getAsMention()).queue();
                support.sendMessage("A staff member will arrive to assist you shortly. " + Objects.requireNonNull(event.getGuild().getRoleById("710398399085805599")).getAsMention()).queue();
            });
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        if(event.getGuild().getName().equals("The Goddess's Parthenon")) {
            /*
             * Adds Wanderer and Agreed to Rules roles. Welcome message is sent in general and @Welcomer is pinged.
             */
            event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("709505726640291877"))).queue();
            event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("709505763583852565"))).queue();
            Objects.requireNonNull(event.getGuild().getTextChannelById("709259200651591747")).sendMessage("Hello "+ event.getMember().getAsMention() +"! Welcome to our server, **The Goddess's Parthenon**! Get yourself some roles in "+ Objects.requireNonNull(event.getGuild().getTextChannelById("709327942035177482")).getAsMention() +" Everyone please make our new friend feel welcome!!! :) "+ Objects.requireNonNull(event.getGuild().getRoleById("727010870403530760")).getAsMention()).queue();
        }
        else if(event.getGuild().getName().equals("Friends :)")) {
            event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("754614035529597038"))).queue();
            Objects.requireNonNull(event.getGuild().getTextChannelById("753717833937977388")).sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server, **Friends :)**! Enjoy your stay :)").queue();
        }
        else if(event.getGuild().getName().equals("Wholesome Study Boys")) {
            /*
             * No Catfishing role is added and welcome message is sent in general chat.
             */
            event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("694032141058834432"))).queue();
            Objects.requireNonNull(event.getGuild().getTextChannelById("693237215404359715")).sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to our server >:)").queue();
        }
        else if(event.getGuild().getName().equals("Playground")) {
            event.getGuild().addRoleToMember(event.getMember().getId(), Objects.requireNonNull(event.getGuild().getRoleById("756889036026675290"))).queue();
            Objects.requireNonNull(event.getGuild().getTextChannelById("710434525611688009")).sendMessage("Welcome to Playground! " + event.getMember().getAsMention()).queue();
        }
    }
}