package bot.music;

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