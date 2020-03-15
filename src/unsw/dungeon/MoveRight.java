package unsw.dungeon;

public class MoveRight implements MoveBehavior{

	Entity e;	
	
	public MoveRight(Entity e) {
		this.e = e;
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stud
        e.x().set(e.getX() + 1);
		
	}

}
