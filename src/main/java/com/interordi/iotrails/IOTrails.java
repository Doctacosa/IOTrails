package com.interordi.iotrails;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.interordi.iotrails.listeners.LoginListener;
import com.interordi.iotrails.utilities.Commands;
import com.interordi.iotrails.utilities.Database;
import com.interordi.iotrails.utilities.CommandTargets;


public class IOTrails extends JavaPlugin {

	LoginListener thisLoginListener;
	Database db = null;


	public void onEnable() {

		//Always ensure we've got a copy of the config in place (does not overwrite existing)
		this.saveDefaultConfig();

		//Get the MySQL database information
		String host = this.getConfig().getString("mysql.host", "localhost");
		int port = this.getConfig().getInt("mysql.port", 3306);
		String user = this.getConfig().getString("mysql.user", "root");
		String pass = this.getConfig().getString("mysql.pass", "");
		String database = this.getConfig().getString("mysql.database", "minecraft");

		db = new Database(host, port, user, pass, database);
		thisLoginListener = new LoginListener(this);

		getLogger().info("IOTrails enabled");
	}

	public void onDisable() {
		getLogger().info("IOTrails disabled");
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//Get the list of potential targets if a selector was used
		CommandTargets results = Commands.findTargets(getServer(), sender, cmd, label, args);
		
		boolean result = false;
		if (results.position != -1) {
			//Run the command for each target identified by the selector
			for (String target : results.targets) {
				args[results.position] = target;
				
				result = runCommand(sender, cmd, label, args);
			}
		} else {
			//Run the command as-is
			result = runCommand(sender, cmd, label, args);
		}
		
		return result;
	}
	
	
	//Actually run the entered command
	public boolean runCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("switch")) {

			if (!sender.hasPermission("iotrails.enable")) {
				sender.sendMessage("§cYou are not allowed to use this command.");
				return true;
			}

			//TODO: Do something

			return true;
		}

		return false;
	}
		
}