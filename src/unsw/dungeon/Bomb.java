package unsw.dungeon;

public class Bomb extends Entity{
	
	BombState lit;
	BombState unlit;
	BombState bombstate;
	int fuse = 4;
	
	public Bomb(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		lit = new LitState(this);
		unlit = new UnlitState(this);
		bombstate = unlit;
		this.carryBehavior = new CanBePicked();
		// TODO Auto-generated constructor stub
	}

	public BombState getLit() {
		// TODO Auto-generated method stub
		return lit;
	}
	
	public BombState getUnlit() {
		// TODO Auto-generated method stub
		return unlit;
	}
	
	public void becomeUnlit() {
		bombstate = bombstate.becomeUnlit();
	}
	
	public void becomeLit() {
		bombstate = bombstate.becomeLit();
	}
	
	public BombState getBombState() {
		return this.bombstate;
	}
	
	public int get_fuse() {
		return this.fuse;
	}
	
	public void burn_fuse() {
		this.fuse --;
	}
	
}
