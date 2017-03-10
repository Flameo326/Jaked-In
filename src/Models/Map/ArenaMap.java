package Models.Map;

import java.util.ArrayList;

import Controller.CollisionSystem;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.Upgrade;

public class ArenaMap extends Map{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PlayableCharacter> baseEntities;

	public ArenaMap(int width, int height, ArrayList<PlayableCharacter> baseEntities) {
		super(width, height);
		this.baseEntities = baseEntities;
		generateMap();
	}
	
	@Override
	public void generateMap(){
		getMapObjects().clear();
		generateArenaWalls(getMapWidth(), getMapHeight());
		getMapObjects().add(createNewRoom(0, 0, getMapWidth(), getMapHeight()));
	}
	
	public void generateArenaWalls(int width, int height){
		Entity wall = createNewWall(-width/2, 0, getBorder(), height);
		getMapObjects().add(wall);
		
		wall = createNewWall(width/2, 0, getBorder(), height);
		getMapObjects().add(wall);
		
		wall = createNewWall(0, -height/2, width, getBorder());
		getMapObjects().add(wall);
		
		wall = createNewWall(0, height/2, width, getBorder());
		getMapObjects().add(wall);
		
		int roomAmo = rand.nextInt(10);
		for(int i = 0; i < roomAmo; i++){
			boolean notColliding = false;
			while(!notColliding){
				int xPos = rand.nextInt(width) - width/2;
				int yPos  = rand.nextInt(height) - height/2;
				int wallWidth = rand.nextInt(width/(roomAmo+1)) + 5;
				int wallHeight = rand.nextInt(height/(roomAmo+1)) + 5;
				wall = createNewWall(xPos, yPos, wallWidth, wallHeight);
				
				ArrayList<Collision> collisions = CollisionSystem.getCollision(wall, getMapObjects().toArray(new Entity[0]));
				notColliding = true;
				for(Collision c : collisions){
					if(Math.min(c.xPenDepth, c.yPenDepth) > -20){
						notColliding = false;
						break;
					}
				}
				if(!notColliding) { continue; }
				 collisions = CollisionSystem.getCollision(wall, baseEntities.toArray(new Entity[0]));
				for(Collision c : collisions){
					if(Math.min(c.xPenDepth, c.yPenDepth) > -20){
						notColliding = false;
						break;
					}
				}
				// check for collision against 
			}
			getMapObjects().add(wall);
		}
	}
	
	public Upgrade createNewUpgrade(){
		Upgrade u = upgradeChoice(-getMapWidth()/2, -getMapHeight()/2, getMapWidth(), getMapHeight());
		if( u == null) { return u; }
		
		boolean colliding = true;
		while(colliding){
			u.setXPos(rand.nextInt(getMapWidth()) - getMapWidth()/2);
			u.setYPos(rand.nextInt(getMapHeight()) - getMapHeight()/2);
			colliding = false;
			ArrayList<Collision> collisions = CollisionSystem.getCollision(u, getMapObjects().toArray(new Entity[0]));
			for(Collision c : collisions){
				if(!c.collidedEntity.getTag().equals("Room") && c.hasCollided){
					colliding = true;
					break;
				}
			}
		}
		return u;
	}

}
