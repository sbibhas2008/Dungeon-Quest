package unsw.dungeon;

public class MoveUp implements MoveBehavior{

	Entity e;	
	
	public MoveUp(Entity e) {
		this.e = e;
	}
	@Override
	//move up
	public void move() {
		// TODO Auto-generated method stub
		e.y().set(e.getY() + 1);
	}
}
