package unsw.dungeon;

public class LitState implements BombState {
	
	Bomb b;
	public LitState(Bomb b) {
		this.b = b;
	}

	@Override
	public BombState becomeLit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BombState becomeUnlit() {
		// TODO Auto-generated method stub
		return b.getLit();
	}

	@Override
	public boolean isLit() {
		// TODO Auto-generated method stub
		return true;
	}

}
