package com.interordi.iotrails;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;

public class Trail {

	public String id;
	public String name;
	Particle particle;
	List< Particle > mix;
	public int low;
	public int high;


	public Trail(String id, String name, Particle particle, int low, int high) {
		this.id = id;
		this.name = (!name.isEmpty()) ? name : id;
		this.particle = particle;
		this.low = low;
		this.high = high;
	}


	public Trail(String id, String name, List<Particle > particles, int low, int high) {
		this.mix = new ArrayList< Particle >();

		this.id = id;
		this.name = (!name.isEmpty()) ? name : id;
		this.mix.addAll(particles);
		this.low = low;
		this.high = high;
	}
	
}