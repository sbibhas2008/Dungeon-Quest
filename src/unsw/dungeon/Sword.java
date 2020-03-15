package unsw.dungeon;

public class Sword extends Entity{
	
	private int charges;
	public Sword(int x, int y, Dungeon dungeon) {
		super(x,y, dungeon);
		this.charges = 6;
		this.carryBehavior = new CanBePicked();
	}
	
		
	public void reduce_charge() {
		this.charges--;
	}
	
	public int get_charge() {
		return this.charges;
	}
	
}
