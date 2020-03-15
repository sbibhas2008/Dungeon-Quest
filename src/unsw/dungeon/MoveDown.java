package unsw.dungeon;

public class MoveDown implements MoveBehavior {

	Entity e;	
		
	public MoveDown(Entity e) {
		this.e = e;
	}
	@Override
	//move up
	public void move() {
		// TODO Auto-generated method stub
		e.y().set(e.getY() - 1);
	}

}
