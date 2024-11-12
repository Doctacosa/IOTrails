package com.interordi.iotrails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.interordi.iotrails.utilities.Database;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Trails {


	private static Map< String, Trail > types = new HashMap< String, Trail >();
	private static Database db = null;


	public static void init(Database db) {

		Trails.db = db;

		types.put("big_smoke", new Trail("big_smoke", "", Particle.LARGE_SMOKE, 8, 14));
		types.put("flame", new Trail("flame", "", Particle.FLAME, 2, 6));
		types.put("ender", new Trail("ender", "", Particle.PORTAL, 90, 160));
		types.put("crit", new Trail("crit", "", Particle.CRIT, 4, 7));
		types.put("sweat", new Trail("sweat", "", Particle.SPLASH, 4, 8));
		types.put("swirls", new Trail("swirls", "", Particle.EFFECT, 2, 8));
		types.put("sparks", new Trail("sparks", "", Particle.FIREWORK, 2, 4));
		types.put("breadcrumb", new Trail("breadcrumb", "", Particle.DRIPPING_LAVA, 4, 8));
		types.put("magma", new Trail("magma", "", Particle.LAVA, 4, 8));
		types.put("letters", new Trail("letters", "", Particle.ENCHANT, 9, 16));
		types.put("happy", new Trail("happy", "", Particle.HAPPY_VILLAGER, 4, 12));
		types.put("magic", new Trail("magic", "", Particle.WITCH, 7, 10));
		types.put("music", new Trail("music", "", Particle.NOTE, 1, 2));
		types.put("anger", new Trail("anger", "", Particle.ANGRY_VILLAGER, 1, 2));
		types.put("clouds", new Trail("clouds", "", Particle.CLOUD, 1, 2));

		types.put("slime", new Trail("slime", "", Particle.ITEM_SLIME, 5, 10));
		types.put("snow", new Trail("snow", "", Particle.SNOWFLAKE, 4, 8));
		types.put("flash", new Trail("flash", "", Particle.END_ROD, 1, 3));
		types.put("ash", new Trail("ash", "", Particle.ASH, 20, 30));
		types.put("smoke", new Trail("smoke", "", Particle.CAMPFIRE_COSY_SMOKE, 1, 2));
		types.put("soul", new Trail("soul", "", Particle.SOUL, 1, 3));
		types.put("soul_flame", new Trail("soul_flame", "", Particle.SOUL_FIRE_FLAME, 2, 4));
		
		types.put("cherry", new Trail("cherry", "", Particle.CHERRY_LEAVES, 3, 6));
		types.put("no_love", new Trail("no_love", "", Particle.DAMAGE_INDICATOR, 2, 4));
		types.put("love", new Trail("love", "", Particle.HEART, 2, 4));
		types.put("dust", new Trail("dust", "", Particle.DUST_PLUME, 2, 4));
		types.put("spots", new Trail("spots", "", Particle.OMINOUS_SPAWNING, 10, 20));
		types.put("death", new Trail("death", "", Particle.RAID_OMEN, 2, 4));

		//Mixes
		types.put("glow", new Trail("glow", "", List.of(Particle.GLOW, Particle.WITCH), 3, 6));
		types.put("omen", new Trail("omen", "", List.of(Particle.RAID_OMEN, Particle.TRIAL_OMEN), 2, 4));
		types.put("stars", new Trail("stars", "", List.of(Particle.WAX_ON, Particle.WAX_OFF, Particle.SCRAPE), 3, 6));
		types.put("ups", new Trail("ups", "", List.of(Particle.TRIAL_SPAWNER_DETECTION, Particle.TRIAL_SPAWNER_DETECTION_OMINOUS), 3, 6));
		types.put("flames", new Trail("flames", "", List.of(Particle.FLAME, Particle.SOUL_FIRE_FLAME), 2, 6));
		types.put("spores", new Trail("spores", "", List.of(Particle.WARPED_SPORE, Particle.CRIMSON_SPORE), 2, 6));

	}


	//Let a player change his trail settings
	public static void select(Player player, String option) {

		if (option == null)
			option = "";

		if (option.equalsIgnoreCase("off")) {
			Players.remove(player);
			db.savePlayerTrail(player.getUniqueId(), null);
			player.sendMessage(ChatColor.GREEN + "Your trail has been removed.");
		} else if (option.isEmpty()) {
			player.sendMessage(ChatColor.GREEN + "This is the list of trails that you can use. Type /trail NAME to enable one, /trail OFF to disable it.");
			List< String > trails = new ArrayList< String >();
			for (Trail trail : types.values())
				trails.add(trail.name);
			Collections.sort(trails);
			player.sendMessage(ChatColor.WHITE + String.join(", ", trails));
		} else {
			if (types.containsKey(option)) {
				db.savePlayerTrail(player.getUniqueId(), option);
				Players.setTrail(player, get(option));
				player.sendMessage(ChatColor.GREEN + "Trail enabled!");
			} else {
				player.sendMessage(ChatColor.RED + "This trail doesn't exist.");
			}

		}
	}


	//Add the definition of a custom trail
	//NOTE: Shared amongst all trail users
	public static void createCustom(String particle, int min, int max) {
		try {
			types.put("custom", new Trail("custom", "", Particle.valueOf(particle.toUpperCase()), min, max));
		} catch (IllegalArgumentException e) {
			System.out.println("Particle " + particle + " doesn't exist!");
		}
	}


	//Get a trail
	public static Trail get(String trail) {
		return types.get(trail);
	}
	
}