package bot.handlers.command;

import bot.commands.SlashCommandInterface;
import bot.commands.slash.general.*;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandManager {

    private final List<SlashCommandInterface> slashCommandList = new ArrayList<>();

    public SlashCommandManager() {

        // General Commands
        addSlashCommand(new ping());
        addSlashCommand(new help());
    }

    private void addSlashCommand(SlashCommandInterface command) {
        boolean commandFound = this.slashCommandList.stream().anyMatch((slash) -> slash.name().equals(command.name()));

        if(commandFound) {
            throw new IllegalArgumentException("A command with this name already exists.");
        }

        slashCommandList.add(command);
    }

    public List<SlashCommandInterface> getAllSlashCommands() {
        return slashCommandList;
    }   
}