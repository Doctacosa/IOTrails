package com.interordi.iotrails;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;


public class Players {

	private static Map< UUID, Trail > activeTrails;
	private static IOTrails plugin;


	public static void init(IOTrails plugin) {
		Players.plugin = plugin;

		activeTrails = new HashMap< UUID, Trail >();
	}


	public static void add(Player player) {

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


	public static void remove(Player player) {
		activeTrails.remove(player.getUniqueId());
	}


	public static void move(Player player) {
		Trail trail = activeTrails.get(player.getUniqueId());

		if (trail == null ||
			player.hasPotionEffect(PotionEffectType.INVISIBILITY) ||
			player.getGameMode() == GameMode.SPECTATOR)
			return;
		
		if (trail.high < trail.low)
			return;

		Random random = new Random();
		Particle particle;
		if (trail.particle != null) {
			//Fixed particle
			particle = trail.particle;
		} else {
			//Pick a random one from the set
			int pos = random.nextInt(trail.mix.size());
			particle = trail.mix.get(pos);
		}

		player.getWorld().spawnParticle(
			particle,
			player.getLocation(),
			random.nextInt(trail.high - trail.low) + trail.low,
			random.nextFloat(),
			random.nextFloat(),
			random.nextFloat(),
			0
		);
	}


	//Set the trail of a player
	public static void setTrail(Player player, Trail trail) {
		activeTrails.put(player.getUniqueId(), trail);
	}

	
}