package unsw.dungeon;

public class Key extends Entity{

	private int id = 0;
	public Key(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.carryBehavior = new CanBePicked();
		// TODO Auto-generated constructor stub
	}
	public void set_id(int id) {
		this.id = id;
	}
	public int get_id() {
		return this.id;
	}

}
