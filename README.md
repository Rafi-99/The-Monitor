# The Monitor ™

## About
A multipurpose Discord server bot created using the official Java Discord API wrapper. Music and video playback is provided through LavaPlayer and the official YouTube API. Hosted on Heroku with a PostgreSQL database.

## Features

### Server Moderation Commands

* setPrefix: changes the prefix of the bot
* ticketSetup: creates a ticket system where server members can create private support tickets to interact with server staff
* invite: creates an invite link to the current Discord server
* mute: mutes the mentioned member and prevents them from typing in channels
* unmute: unmutes the mentioned member and enables them to type again
* purge: deletes messages
* kick: kicks a member through mentioning or user id
* ban: bans a member through mentioning or user id
* unban: unbans a member through mentioning or user id
  
### General Commands

* botInfo: displays general bot information and commands 
* serverInfo: displays general server information
* ping: displays ping

### Fun Commands

* roast: roasts the mentioned member 
* wholesome: rates how wholesome the mentioned member is (0-100%)
* simp: rates how much of a simp the mentioned member is (0-100%)
* avatar: displays profile picture of the mentioned member 
* pp: creates a randomly generated pp length 
* rps: play a game of rock paper scissors against the bot
* meme: displays a random meme from [Reddit](https://www.reddit.com/r/memes)
* emotes: displays all the emotes from the current server
  
### Music Commands

* join: makes the bot join a voice channel
* leave: makes the bot leave the voice channel and clears the queue
* np: displays what's currently playing
* play: plays a song or video 
* loopTrack: loops the current track 
* volume: sets the player volume
* pause: pauses the player 
* skip: skips the current song or video 
* queue: displays the queue 
* clear: clears the queue

### Slash Commands
* Run commands without the bot prefix!
* Type in '/', and you will get a list of available commands 
* Less typing, more playing with commands!
* Currently converting existing non-slash commands into slash commands

## Want to Add the Bot to Your Server? 
[Click here for the link!](https://discord.com/oauth2/authorize?client_id=711703852977487903&scope=bot&permissions=8)

## Self-Hosting 
Self-hosting your own copy of this bot will not be supported. The purpose of making the source code in this repository public is to help users and developers alike understand how the bot works. No assistance will be given for editing, compiling, and building any code from this repository. Any changes must be documented as per the license.

## Legal
It's okay to get inspiration from this repository as the bot's code is open-source for a reason. **Please** do not blatantly copy 
or refrain from crediting the author of this work. Give credit where credit is due.

## Contributing
Suggestions and feature requests are more than welcome! I am trying to make my bot more scalable so I appreciate all the help I can get. Feel free to reach out to me on Discord (Rafi ™#6927) or join my bot development server: https://discord.gg/FHV9yCNvfN

## Links to Dependencies

* [Java Discord API Wrapper](https://github.com/DV8FromTheWorld/JDA) 
* [JDA Utilities](https://github.com/JDA-Applications/JDA-Utilities)
* [LavaPlayer](https://github.com/sedmelluq/lavaplayer)
* [BotCommons](https://github.com/duncte123/botCommons)
* [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc)