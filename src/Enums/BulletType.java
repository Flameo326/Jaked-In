package Enums;

public enum BulletType {
	NORMAL (5),
	EXPLOSIVE (3),
	BOUNCE (8);
	
	int speed;
	
	private BulletType(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}

}
