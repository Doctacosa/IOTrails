package com.interordi.iotrails.listeners;

import com.interordi.iotrails.IOTrails;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class LoginListener implements Listener {

	
	public LoginListener(IOTrails plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		//TODO: Load player's information
	}
}