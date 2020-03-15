package unsw.dungeon;

public class CannotBePicked implements CarryBehavior{

	@Override
	public boolean pickUp(Entity e, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
