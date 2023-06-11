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

package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test implements CommandInterface {
    boolean stop = false;

    @Override
    public void handle(CommandContext c) {
        if (c.getCommandParameters().size() == 1 && c.getEvent().getMember().getId().equals(System.getenv("BOT_OWNER"))) {

            if (c.getCommandParameters().get(0).equals("-s")) {
                ScheduledExecutorService test = Executors.newSingleThreadScheduledExecutor();
                test.scheduleAtFixedRate(() -> {
                    c.getEvent().getChannel().sendMessage("Poggers!").queue();
                    if (stop) {
                        c.getEvent().getChannel().sendTyping().queue();
                        c.getEvent().getChannel().sendMessage("Ending spam now..." + "\n" + "Ended.").setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();
                        test.shutdownNow();
                        stop = false;
                    }
                }, 0, 1, TimeUnit.SECONDS);

            }
            else if (c.getCommandParameters().get(0).equals("-t")) {
                stop = true;
            }
        }
    }

    @Override
    public String getName() {
        return "test";
    }
}
