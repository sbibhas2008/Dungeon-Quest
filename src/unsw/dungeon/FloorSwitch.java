package unsw.dungeon;

import java.util.Random;

public class FloorSwitch extends Entity{
	
	
	public FloorSwitch(int x, int y, Dungeon d) {
		super(x, y, d);
		carryBehavior = new CannotBePicked();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
