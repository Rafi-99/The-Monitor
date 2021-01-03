# The Monitor ™
## About
A multipurpose Discord server bot created using the official Java Discord API wrapper. Music and video playback is provided through LavaPlayer and the official YouTube API. Hosted on Heroku.
## Features

* ### Server Moderation Commands

  * setPrefix: changes the prefix of the bot
  * ticketSetup: creates a ticket system where server members can create private support tickets to interact with server staff
  * createInvite: creates an invite link to the current Discord server
  * mute: mutes the mentioned member and prevents them from typing in channels
  * unmute: unmutes the mentioned member and enables them to type again
  * purge: deletes messages 
  * kick: kicks a member through mention or user id
  * ban: bans a member through mention or user id
  * unban: unbans a member through mention or user id
  
* ### General Commands

  * botInfo: displays general bot information and commands 
  * serverInfo: displays general server information
  * ping: displays ping 

* ### Fun Commands

  * roast: roasts the mentioned member 
  * wholesome: rates how wholesome the mentioned member is (0-100%)
  * simp: rates how much of a simp the mentioned member is (0-100%)
  * avatar: displays profile picture of the mentioned member 
  * pp: creates a randomly generated pp length 
  * rps: play a game of rock paper scissors against the bot
  * meme: displays a random meme from https://www.reddit.com/r/memes
  
* ### Music Commands

  * join: makes the bot join a voice channel
  * leave: makes the bot leave the voice channel and clear the queue
  * np: displays what's currently playing
  * play: plays a song or video 
  * loopTrack: loops the current track 
  * pause: pauses the player 
  * skip: skips the current song or video 
  * queue: displays the queue 
  * clear: clears the queue
  
## Links to Dependancies

* Java Discord API Wrapper: https://github.com/DV8FromTheWorld/JDA 
* LavaPlayer: https://github.com/sedmelluq/lavaplayer
* BotCommons: https://github.com/duncte123/botCommons
