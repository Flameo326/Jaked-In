package Interfaces;

import Models.Bounds;

public interface Collideable {
	public boolean isColliding(Collideable c);
	public Bounds getBounds();
}
