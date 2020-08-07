package com.interordi.iotrails;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;


public class Players {

	Map< UUID, Trail > activeTrails;
	IOTrails plugin;


	public Players(IOTrails plugin) {
		this.plugin = plugin;

		activeTrails = new HashMap< UUID, Trail >();
	}


	public void add(Player player) {

		if (!player.hasPermission("iotrails.enable"))
			return;

		final UUID uuid = player.getUniqueId();

		Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				String trailName = plugin.db.getPlayerTrail(uuid);
				Trail trail = Trails.get(trailName);
				if (trail != null)
					activeTrails.put(uuid, trail);
			}
		});
	}


	public void remove(Player player) {
		activeTrails.remove(player.getUniqueId());
	}


	public void move(Player player) {
		Trail trail = activeTrails.get(player.getUniqueId());

		if (trail == null ||
			player.hasPotionEffect(PotionEffectType.INVISIBILITY) ||
			player.getGameMode() == GameMode.SPECTATOR)
			return;
		
		if (trail.high < trail.low)
			return;

		Random random = new Random();
		//TODO: Fix math
		player.getWorld().spawnParticle(
			trail.particle,
			player.getLocation(),
			random.nextInt((trail.high - trail.low) + trail.low) + 1,
			random.nextFloat(),
			random.nextFloat(),
			random.nextFloat(),
			0
		);
	}

	
}