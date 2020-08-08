package com.interordi.iotrails;

import java.util.ArrayList;
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

		types.put("smoke", new Trail("smoke", "", Particle.SMOKE_LARGE, 8, 14));
		types.put("fire", new Trail("fire", "", Particle.FLAME, 2, 6));
		types.put("ender", new Trail("ender", "", Particle.PORTAL, 90, 160));
		//types.put("hearts", new Trail("hearts", "", Particle.HEART, 2, 8));
		types.put("crit", new Trail("crit", "", Particle.CRIT, 4, 7));
		types.put("sweat", new Trail("sweat", "", Particle.WATER_SPLASH, 4, 8));
		types.put("swirls", new Trail("swirls", "", Particle.SPELL_MOB, 2, 8));
		//types.put("blood", new Trail("blood", "", Particle.REDSTONE, 2, 8));
		types.put("sparks", new Trail("sparks", "", Particle.FIREWORKS_SPARK, 1, 2));
		types.put("breadcrumb", new Trail("breadcrumb", "", Particle.DRIP_LAVA, 4, 8));
		types.put("magma", new Trail("magma", "", Particle.LAVA, 4, 12));
		types.put("letters", new Trail("letters", "", Particle.ENCHANTMENT_TABLE, 9, 16));
		types.put("happy", new Trail("happy", "", Particle.VILLAGER_HAPPY, 4, 12));
		//types.put("magic", new Trail("magic", "", Particle.CRIT_MAGIC, 7, 10));
		types.put("magic", new Trail("magic", "", Particle.SPELL_WITCH, 7, 10));
		types.put("music", new Trail("music", "", Particle.NOTE, 1, 2));
		types.put("anger", new Trail("anger", "", Particle.VILLAGER_ANGRY, 1, 2));
		types.put("clouds", new Trail("clouds", "", Particle.CLOUD, 1, 2));
		
		types.put("slime", new Trail("slime", "", Particle.SLIME, 5, 10));
		types.put("snow", new Trail("snow", "", Particle.SNOWBALL, 3, 6));
		types.put("glow", new Trail("glow", "", Particle.END_ROD, 1, 3));
		types.put("ash", new Trail("ash", "", Particle.ASH, 20, 30));
		types.put("smoke", new Trail("smoke", "", Particle.CAMPFIRE_COSY_SMOKE, 1, 2));
		types.put("spores1", new Trail("spores1", "", Particle.CRIMSON_SPORE, 2, 6));
		types.put("spores2", new Trail("spores2", "", Particle.WARPED_SPORE, 1, 3));
		types.put("soul", new Trail("soul", "", Particle.SOUL, 1, 3));
		types.put("soul_flame", new Trail("soul_flame", "", Particle.SOUL_FIRE_FLAME, 2, 4));

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


	//Get a trail
	public static Trail get(String trail) {
		return types.get(trail);
	}
	
}