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