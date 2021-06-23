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