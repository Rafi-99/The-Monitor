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

package bot.handlers.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/* 
 * Holder for both the player and a track scheduler for one guild.
 */
public class GuildMusicManager {
     /* 
      * Audio player for the guild.
      */
     public final AudioPlayer player;
     /* 
      * Track scheduler for the player.
      */
     public final TrackScheduler scheduler;

     private final AudioPlayerSendHandler sendHandler;

     /*
      * Creates a player and track scheduler.
      * @param manager: Audio player manager to use for creating the player.
      */
     public GuildMusicManager(AudioPlayerManager manager) {
          this.player = manager.createPlayer();
          this.scheduler = new TrackScheduler(this.player);
          this.player.addListener(this.scheduler);
          this.sendHandler = new AudioPlayerSendHandler(this.player);
     }

     /* 
      * @return: Wrapper around AudioPlayer to use it as an AudioSendHandler.
      */ 
     public AudioPlayerSendHandler getSendHandler() {
          return sendHandler;
     }
}