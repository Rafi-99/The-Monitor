package bot.handlers.music;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

/* 
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
     
     private final AudioPlayer player;
     private final BlockingQueue<AudioTrack> queue;
     public boolean trackLoop = false;

     /* 
      * @param player: The audio player this scheduler uses.
      */
     public TrackScheduler(AudioPlayer player) {
          this.player = player;
          this.queue = new LinkedBlockingQueue<>();
     }

     /*
      * Adds the next track to the queue or plays right away if nothing is in the queue.
      * @param track: The track to play or add to queue.
      */
     public void queue(AudioTrack track) {
          /* 
           * Calling startTrack with noInterrupt set to true will start the track only if nothing is currently playing. If
           * something is playing, it returns false and does nothing. In this case the player is already playing so this
           * track goes to the queue instead.
           */
          if(!this.player.startTrack(track, true)) {
               this.queue.offer(track);
          }
     }

     public BlockingQueue<AudioTrack> getQueue() {
          return this.queue;
     }

     /* 
      * Starts the next track, stopping the current one if it is playing.
      */
     public void nextTrack() {
          /*
           * Starts the next track, regardless of if something is already playing or not. In case the queue is empty, we are
           * giving null to startTrack, which is a valid argument and will simply stop the player.
           */
          this.player.startTrack(this.queue.poll(), false);
     }

     @Override
     public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
          /*
           * Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
           */
          if(endReason.mayStartNext) {
               if(this.trackLoop) {
                    this.player.startTrack(track.makeClone(), false);
                    return;
               }
               nextTrack();
          }
     }
}