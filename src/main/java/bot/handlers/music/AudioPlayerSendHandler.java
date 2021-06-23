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

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

/*
 * This is a wrapper around AudioPlayer which makes it behave as an AudioSendHandler for JDA. As JDA calls canProvide
 * before every call to provide20MsAudio(), we pull the frame in canProvide() and use the frame we already pulled in
 * provide20MsAudio().
 */
public class AudioPlayerSendHandler implements AudioSendHandler {

     private final AudioPlayer audioPlayer;
     private final ByteBuffer buffer;
     private final MutableAudioFrame frame;

     /* 
      * @param audioPlayer: Audio player to wrap.
      */
     public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
          this.audioPlayer = audioPlayer;
          this.buffer = ByteBuffer.allocate(1024);
          this.frame = new MutableAudioFrame();
          this.frame.setBuffer(buffer);
     }

     /* 
      * Returns true if audio was provided.
      */
     @Override
     public boolean canProvide() {
          return this.audioPlayer.provide(this.frame);
     }

     @Override
     public ByteBuffer provide20MsAudio() {
          return this.buffer.flip();
     }
     
     @Override
     public boolean isOpus() {
          return true;
     }
}