package unsw.dungeon;

public class InactivePotion implements PotionState {
	
	Potion p;
	
	public InactivePotion(Potion p) {
		this.p = p;
	}

	@Override
	public PotionState becomeActive() {
		// TODO Auto-generated method stub
		return p.getActive();
	}

	@Override
	public PotionState becomeInactive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
