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

    private final List<CommandInterface> commandList = new ArrayList<>();

    public CommandManager() {
        //Admin Commands
        addCommand(new Admin());
        addCommand(new Guilds());
        addCommand(new Link());
        addCommand(new Restart());
        addCommand(new Test());
        //Fun Commands (7)
        addCommand(new Avatar());
        addCommand(new Emotes());
        addCommand(new Meme());
        addCommand(new Penis());
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
        //Music Commands (9)
        addCommand(new Clear());
        addCommand(new Join());
        addCommand(new Leave());
        addCommand(new LoopTrack());
        addCommand(new NowPlaying());
        addCommand(new Pause());
        addCommand(new Play());
        addCommand(new Queue());
        addCommand(new Skip());
    }

    private void addCommand(CommandInterface command) {
        boolean commandFound = this.commandList.stream().anyMatch((c) -> c.getName().equalsIgnoreCase(command.getName()));

        if(commandFound) {
            throw new IllegalArgumentException("A command with this name already exists.");
        }
        commandList.add(command);
    }

    public List<CommandInterface> getAllCommands() {
        return commandList;
    }

    @Nullable
    public CommandInterface getCommand(String search) {
        for (CommandInterface command:this.commandList) {
            if(command.getName().equalsIgnoreCase(search)) {
                return command;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix) {
        //Gets rid of prefix and splits the event.getMessage.getContentRaw() from EventListener so the command name is stored at index 0
        String [] split = event.getMessage().getContentRaw() .replaceFirst("(?i)" + Pattern.quote(prefix), "").split("\\s+");
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