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

package bot.handlers.command;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.commands.admin.*;
import bot.commands.fun.*;
import bot.commands.general.*;
import bot.commands.moderation.*;
import bot.commands.music.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandManager {

    private final List<CommandInterface> COMMANDS = new ArrayList<>();

    public CommandManager() {
        //Admin Commands
        addCommand(new Admin());
        addCommand(new Guilds());
        addCommand(new Link());
        addCommand(new Restart());
        addCommand(new Test());
        //Fun Commands (8)
        addCommand(new Avatar());
        addCommand(new Emotes());
        addCommand(new Meme());
        addCommand(new PP());
        addCommand(new Roast());
        addCommand(new RockPaperScissors());
        addCommand(new Simp());
        addCommand(new Wholesome());
        //General Commands (3)
        addCommand(new BotInfo());
        addCommand(new Ping());
        addCommand(new ServerInfo());
        //Moderation Commands (7)
        addCommand(new BanCommand());
        addCommand(new Invite());
        addCommand(new Kick());
        addCommand(new Purge());
        addCommand(new SetPrefix());
        addCommand(new TicketSetup());
        addCommand(new Unban());
        //Music Commands (10)
        addCommand(new Clear());
        addCommand(new Join());
        addCommand(new Leave());
        addCommand(new LoopTrack());
        addCommand(new NowPlaying());
        addCommand(new Pause());
        addCommand(new Play());
        addCommand(new Queue());
        addCommand(new Skip());
        addCommand(new Volume());
    }

    private void addCommand(CommandInterface command) {
        boolean commandFound = this.COMMANDS.stream().anyMatch((c) -> c.getName().equalsIgnoreCase(command.getName()));

        if(commandFound) {
            throw new IllegalArgumentException("A command with this name already exists.");
        }
        COMMANDS.add(command);
    }

    public List<CommandInterface> getAllCommands() {
        return COMMANDS;
    }

    @Nullable
    public CommandInterface getCommand(String search) {
        for (CommandInterface command:this.COMMANDS) {
            if(command.getName().equalsIgnoreCase(search)) {
                return command;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix) {
        //Gets rid of prefix and splits the event.getMessage.getContentRaw() from EventListener so the command name is stored at index 0
        String [] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(prefix), "").split("\\s+");
        //Takes the command name and makes it lowercase
        String commandInvoke = split[0].toLowerCase();
        //Gets the command
        CommandInterface command = this.getCommand(commandInvoke);

        //If the command by name is found, run the command
        if(command != null) {
            List<String> args = Arrays.asList(split).subList(1, split.length);
            CommandContext c = new CommandContext(event, args);
            command.handle(c);
        }
    }
}