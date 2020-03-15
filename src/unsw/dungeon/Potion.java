package unsw.dungeon;

public class Potion extends Entity{
	
	PotionState inactivePotion;
	PotionState activePotion;
	PotionState potionState;
	private int start;
	private int end;
	

	public Potion(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		inactivePotion = new InactivePotion(this);
		activePotion = new ActivePotion(this);
		potionState = inactivePotion;
		this.carryBehavior = new CanBePicked();
		// TODO Auto-generated constructor stub
	}

	public PotionState getInactive() {
		// TODO Auto-generated method stub
		return inactivePotion;
	}
	
	public PotionState getActive() {
		// TODO Auto-generated method stub
		return activePotion;
	}
	
	public void becomeActive() {
		potionState = activePotion;
	}
	
	public void becomeInactive() {
		potionState = inactivePotion;
	}
	
	public int get_start() {
		return this.start;
	}
	
	public void setStart(int start) {
		this.start = start;
		this.end = start + 5;
	}
	
	public void update(int moves, Player player) {
		if (moves == this.end) {
			potionState = potionState.becomeInactive();
			player.Stash.remove(this);
		}
	}
	
	public PotionState getPotionState() {
		return this.potionState;
	}
}
