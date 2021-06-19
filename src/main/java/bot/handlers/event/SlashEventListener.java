package bot.handlers.event;

import bot.commands.SlashCommandInterface;
import bot.handlers.command.SlashCommandManager;

import java.util.List;
import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashEventListener extends ListenerAdapter {

    private static final Logger botLogger = LoggerFactory.getLogger(SlashEventListener.class);
    private final List<SlashCommandInterface> slashCommandList = new SlashCommandManager().getAllSlashCommands();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        botLogger.info("Slash commands setup successfully!");
        botLogger.info("Loaded {} slash commands!", slashCommandList.size());

        for(int i =0; i< slashCommandList.size(); i++) {
            botLogger.info(i + 1 +". "+ slashCommandList.get(i).name());
        }

        for (SlashCommandInterface slashCommandInterface : slashCommandList) {
            event.getJDA().upsertCommand(slashCommandInterface.name(), slashCommandInterface.description()).queue();
        }
    }

    @Override
    public void onSlashCommand(@Nonnull SlashCommandEvent event) {
        for (SlashCommandInterface slashCommand : slashCommandList) {
            if (slashCommand.name().equals(event.getName())) {
                slashCommand.execute(event);
            }
        }
    }
}