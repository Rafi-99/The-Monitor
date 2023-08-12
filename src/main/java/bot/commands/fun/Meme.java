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

package bot.commands.fun;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;
import bot.handlers.utilities.Constants;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Year;
import java.util.Scanner;

import org.json.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;

public class Meme implements CommandInterface {

    @Override
    public void handle(CommandContext c) {
        if (c.getCommandParameters().isEmpty()) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://memes.rafi-codes.dev/api/reddit/memes").openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    StringBuilder response = new StringBuilder();

                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    String title = jsonResponse.getString("title");
                    String url = jsonResponse.getString("url");
                    String image = jsonResponse.getString("image");

                    EmbedBuilder memeEmbed = new EmbedBuilder()
                    .setColor(0x05055e)
                    .setTitle(title, url)
                    .setImage(image)
                    .setFooter("The Monitor ™ | © " + Year.now().getValue(), c.getEvent().getJDA().getSelfUser().getEffectiveAvatarUrl());

                    c.getEvent().getChannel().sendTyping().queue();
                    c.getEvent().getChannel().sendMessageEmbeds(memeEmbed.build()).setMessageReference(c.getEvent().getMessage()).mentionRepliedUser(false).queue();

                    memeEmbed.clear();
                }
                else {
                    Constants.setEmbed(c.getEvent(), "❌ Fetch Error ❌", "Unable to fetch meme. Please try executing the command again.");
                }
                connection.disconnect();
            }
            catch (IOException e) {
                Constants.setEmbed(c.getEvent(), "❌ Internal Error ❌", "Unable to retrieve meme. Please try executing the command again.");

            }
        }
    }

    @Override
    public String getName() {
        return "meme";
    }
}
