package unsw.dungeon;

public class ActivePotion implements PotionState{

	Potion p;
	
	public ActivePotion(Potion p) {
		this.p = p;
	}
	@Override
	public PotionState becomeActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PotionState becomeInactive() {
		// TODO Auto-generated method stub
		return p.getInactive();
	}
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}

}
