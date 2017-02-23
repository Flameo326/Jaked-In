package Interfaces;

import java.util.ArrayList;

import Models.Entity;

public interface Moveable {
	public void move(int x, int y);
	public void update(ArrayList<Entity> entities);
}
