package unsw.dungeon;

public class Treasure extends Entity{

	private int level;
	public Treasure(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		level = 1;
		this.carryBehavior = new CanBePicked();
		// TODO Auto-generated constructor stub
	}
	public int get_treasure_level () {
		return this.level;
	}
	public void add_treasure() {
		this.level++;
	}
}
