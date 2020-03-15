package unsw.dungeon;

public interface BombState {
	public BombState becomeLit();
	public BombState becomeUnlit();
	public boolean isLit();
}
