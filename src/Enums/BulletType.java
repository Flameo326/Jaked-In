package Enums;

public enum BulletType {
	NORMAL (5),
	EXPLOSIVE (3),
	BOUNCE (8),
	SENSITIVE_NORMAL (5),
	SENSITIVE_EXPLOSIVE (3);
	
	int speed;
	
	private BulletType(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}

}
