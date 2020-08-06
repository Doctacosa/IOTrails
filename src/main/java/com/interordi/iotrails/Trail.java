package com.interordi.iotrails;

import org.bukkit.Particle;

public class Trail {

	public String id;
	public String name;
	Particle particle;
	public int low;
	public int high;


	public Trail(String id, String name, Particle particle, int low, int high) {
		this.id = id;
		this.name = (!name.isEmpty()) ? name : id;
		this.particle = particle;
		this.low = low;
		this.high = high;
	}
	
}