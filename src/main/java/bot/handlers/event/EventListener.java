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

package bot.handlers.event;

import bot.handlers.command.CommandManager;
import bot.handlers.database.DataSource;
import bot.handlers.utilities.Constants;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Objects;
import javax.annotation.Nonnull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener extends ListenerAdapter {

    private static final Logger BOT_LOGGER = LoggerFactory.getLogger(EventListener.class);
    private final CommandManager botCommandManager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        BOT_LOGGER.info("Bot is now online!");
        BOT_LOGGER.info("Loaded {} commands!", botCommandManager.getAllCommands().size());
        for(int i =0; i< botCommandManager.getAllCommands().size(); i++) {
            BOT_LOGGER.info(i + 1 +". "+ botCommandManager.getAllCommands().get(i).getName());
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        final long guildId = event.getGuild().getIdLong();
        String prefix = Constants.PREFIXES.computeIfAbsent(guildId, this::loadPrefix);

        String message = event.getMessage().getContentRaw();
        String [] botMention = event.getMessage().getContentRaw().split("\\s+");
        
        if(event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }

        if(message.startsWith(prefix)) {
            botCommandManager.handle(event, prefix);
        }

        if(message.contains("https://") || message.contains("http://")) {
            Role staff = event.getGuild().getRoleById("710398399085805599");

            if(event.getChannel().getId().equals("709259200651591747") && !Objects.requireNonNull(event.getMember()).getRoles().contains(staff)) {
                event.getMessage().delete().queue();
            }

            if(message.contains("https://discord.gg/") && event.getGuild().getId().equals("709259200651591742") && !event.getChannel().getId().equals("717870479272312934")) {
                event.getMessage().delete().queue();
            }
        }

        if(botMention.length == 1 && (botMention[0].equals("<@711703852977487903>") || botMention[0].equals("<@!711703852977487903>"))) {
            event.getJDA().retrieveApplicationInfo().queue(botOwner -> {
                EmbedBuilder info = new EmbedBuilder()
                .setColor(0x05055e)
                .setTitle("**The Monitor ™ Bot Information**")
                .setDescription("A multi-purpose Discord server bot in development.")
                .setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .addField("**Current Prefix**", Constants.PREFIXES.get(event.getGuild().getIdLong()), true)
                .addField("**Command Usage Example**", Constants.PREFIXES.get(event.getGuild().getIdLong()) + "botInfo", false)
                .addField("**Moderation**", "setPrefix, ticketSetup, invite, mute, unmute, purge, kick, ban, unban", true)
                .addField("**General**", "botInfo, serverInfo, ping", true)
                .addField("**Fun**", "roast, wholesome, simp, avatar, pp, rps, meme, emotes", true)
                .addField("**Music**", "join, leave, np, play, loopTrack, volume, pause, skip, queue, clear", true)
                .setFooter(botOwner.getOwner().getName() + " | Bot Developer", botOwner.getOwner().getEffectiveAvatarUrl());
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).reference(event.getMessage()).mentionRepliedUser(false).queue();
                info.clear();
            });
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
            Objects.requireNonNull(event.getGuild().getTextChannelById("709259200651591747")).sendMessage("Hello "+ event.getMember().getAsMention() +"! Welcome to our server, **The Goddess's Parthenon**! Get yourself some roles in "+ Objects.requireNonNull(event.getGuild().getTextChannelById("709327942035177482")).getAsMention() +". Everyone please make our new friend feel welcome!!! :) "+ Objects.requireNonNull(event.getGuild().getRoleById("727010870403530760")).getAsMention()).queue();
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
        else {
            Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage("Hello " + event.getMember().getAsMention() + "! Welcome to " + event.getGuild().getName() + "!").queue();
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        final long guildId = event.getGuild().getIdLong();
        
        if(event.getJDA().getGuildById(guildId) == null) {
            try (final PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement("DELETE FROM guild_settings WHERE guild_id = ?")) {
                preparedStatement.setString(1, String.valueOf(guildId));
                preparedStatement.execute();
                BOT_LOGGER.info("Guild with guild_id: {} has been deleted successfully from the database!", guildId);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String loadPrefix(long guildId) {
        try (final PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {
            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("prefix");
                }
            }

            try (final PreparedStatement insertStatement = DataSource.getConnection().prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {
                insertStatement.setString(1, String.valueOf(guildId));
                insertStatement.execute();
            }
        } 
        catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return System.getenv("DEFAULT_PREFIX");
    }
}