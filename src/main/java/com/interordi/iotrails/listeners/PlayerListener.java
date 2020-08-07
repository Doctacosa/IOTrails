package com.interordi.iotrails.listeners;

import com.interordi.iotrails.IOTrails;
import com.interordi.iotrails.Players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerListener implements Listener {

	IOTrails plugin;


	public PlayerListener(IOTrails plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Players.add(event.getPlayer());
	}


	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Players.remove(event.getPlayer());
	}


	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Players.move(event.getPlayer());
	}
}