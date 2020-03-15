package unsw.dungeon;

public class UnlitState implements BombState {

	Bomb b;
	public UnlitState(Bomb b) {
		this.b = b;
	}

	
	@Override
	public BombState becomeLit() {
		// TODO Auto-generated method stub
		return b.getLit();
	}

	@Override
	public BombState becomeUnlit() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isLit() {
		// TODO Auto-generated method stub
		return false;
	}

}
