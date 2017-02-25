package Enums;

public enum Direction {
	LEFTUP (-1, -1),
	UP (0, -1),
	RIGHTUP (1, -1),
	LEFT (-1, 0),
	NULL (0, 0),
	RIGHT (1, 0),
	LEFTDOWN (-1, 1),
	DOWN (0, 1),
	RIGHTDOWN (1, 1);
		
	private final int xMove;
	private final int yMove;
	
	private Direction(int xMove, int yMove) {
		this.xMove = xMove;
		this.yMove = yMove;
	}
	
	public int getX(){ return xMove; }
	public int getY(){ return yMove; }
	
	public static Direction getDir(int x, int y){
		return Direction.values()[x + ((y+1)*3+1)];
	}
}
