package unsw.dungeon;

public class MoveLeft implements MoveBehavior{

	Entity e;	
		
	public MoveLeft(Entity e) {
		this.e = e;
	}	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		 e.x().set(e.getX() - 1);
	}

}
