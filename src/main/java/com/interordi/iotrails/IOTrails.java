package com.interordi.iotrails;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import com.interordi.iotrails.listeners.PlayerListener;
import com.interordi.iotrails.utilities.Commands;
import com.interordi.iotrails.utilities.Database;
import com.interordi.iotrails.utilities.CommandTargets;


public class IOTrails extends JavaPlugin {

	public Database db = null;
	public boolean init = false;

	@SuppressWarnings("unused")
	private PlayerListener thisPlayerListener;


	public void onEnable() {

		//Always ensure we've got a copy of the config in place (does not overwrite existing)
		this.saveDefaultConfig();

		//Get the MySQL database information
		String dbHost = this.getConfig().getString("database.host", null);
		int dbPort = this.getConfig().getInt("database.port", 0);
		String dbUsername = this.getConfig().getString("database.username", null);
		String dbPassword = this.getConfig().getString("database.password", null);
		String dbBase = this.getConfig().getString("database.base", null);

		//Old config format
		if (dbHost == null)
			dbHost = this.getConfig().getString("mysql.host");
		if (dbPort == 0)
			dbPort = this.getConfig().getInt("mysql.port");
		if (dbUsername == null)
			dbUsername = this.getConfig().getString("mysql.user");
		if (dbPassword == null)
			dbPassword = this.getConfig().getString("mysql.pass");
		if (dbBase == null)
			dbBase = this.getConfig().getString("mysql.database");

		db = new Database(dbHost, dbPort, dbUsername, dbPassword, dbBase);
		if (!db.init()) {
			Bukkit.getLogger().severe("---------------------------------");
			Bukkit.getLogger().severe("Failed to initialize the database");
			Bukkit.getLogger().severe("Make sure to configure config.yml");
			Bukkit.getLogger().severe("---------------------------------");
			return;
		}

		thisPlayerListener = new PlayerListener(this);
		Trails.init(db);
		Players.init(this);

		init = true;

		getLogger().info("IOTrails enabled");
	}

	public void onDisable() {
		getLogger().info("IOTrails disabled");
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!init)
			return false;
		
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
		
		if (!init)
			return false;
		
		if (cmd.getName().equalsIgnoreCase("trail") || cmd.getName().equalsIgnoreCase("trails")) {

			if (!sender.hasPermission("iotrails.enable")) {
				sender.sendMessage(ChatColor.RED + "You are not allowed to use this command.");
				return true;
			}

			//Only players can run the rest of the commands
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
				return true;
			}
			
			Player player = (Player)sender;

			String option = null;
			if (args.length > 0)
				option = args[0];

			//Define a custom trail
			if (sender.hasPermission("iotrails.custom") &&
				args.length > 2 &&
				args[0].equalsIgnoreCase("custom")) {

				int min = 2;
				int max = 4;
				String particle = args[1];
				if (args.length > 3) {
					min = Integer.parseInt(args[2]);
					max = Integer.parseInt(args[3]);
				}

				Trails.createCustom(particle, min, max);
			}
			
			Trails.select(player, option);

			return true;
		}

		return false;
	}
		
}