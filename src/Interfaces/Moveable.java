package Interfaces;

import java.util.ArrayList;

import Models.Entity;

public interface Moveable {
	public void move(ArrayList<Entity> entities);
	public void update(ArrayList<Entity> entities);
}
