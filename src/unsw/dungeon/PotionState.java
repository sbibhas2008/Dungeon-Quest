package unsw.dungeon;

public interface PotionState {
	
	public PotionState becomeActive();
	public PotionState becomeInactive(); 
	public boolean isActive();

}
