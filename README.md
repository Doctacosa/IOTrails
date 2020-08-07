# IOTrails
Allow Minecraft players to have fancy particle trails wherever they go. The settings are stored in a database so that they can be reused across multiple servers.


## Configuration

`mysql.host`: Database host  
`mysql.port`: Database port  
`mysql.user`: Database username  
`mysql.pass`: Database password  
`mysql.database`: Database name  


## Commands

`trail`: Used by authorized players to view the available trails (if no parameter specified), activate one (with the correct name) or turn them off (with the 'off' value).


## Permissions

`iotrails.enable`: Access to the `/trail` command.
