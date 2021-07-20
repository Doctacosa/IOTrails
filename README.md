# IOTrails

![Logo](https://www.interordi.com/images/plugins/iotrails-96.png)

Players, dazzle yourself and others by having fancy particle trails whenever you go!

Server owners, offer a cosmetic perk to your most valuable players, or let everyone express themselves how they wish!

This plugin allows Minecraft players to have fancy particle trails wherever they go. The activated trails are stored in a database so that they can be active across multiple servers on a network.

There are currently 24 different trails available, including new particles introduced in 1.16 - the Nether Update!


![Trail preview](https://gallery.creeperslab.net/screenshots/plugins/trail.png)


## Installation

1. Download the plugin and place it in your plugins/ directory.
2. Start and stop the server to generate the configuration file.
3. Edit config.yml with your MySQL database information.
4. Start your version, set permissions and try out /trail!



## Configuration

`database.host`: Database host  
`database.port`: Database port  
`database.base`: Database name  
`database.username`: Database username  
`database.password`: Database password  
`mysql.database`: Database name  


## Commands

`trail`: Used by authorized players to view the available trails (if no parameter specified), activate one (with the correct name) or turn them off (with the 'off' value).


## Permissions

`iotrails.enable`: Access to the `/trail` command.
