package unsw.dungeon;

public class Door extends Entity{
	
	DoorState openState;
	DoorState closedState;
	DoorState doorstate;
	
	private int id = 0;
	public Door(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		carryBehavior = new CannotBePicked();
		openState = new OpenState(this);
		closedState = new ClosedState(this);
		doorstate = closedState;
		// TODO Auto-generated constructor stub
	}
	
	public int get_id() {
		return this.id;
	}
	
	public void set_id(int id) {
		this.id = id;
	}

	public DoorState getClose() {
		// TODO Auto-generated method stub
		return closedState;
	}
	
	public DoorState getOpen() {
		// TODO Auto-generated method stub
		return openState;
	}
	
	public void becomeClosed() {
		doorstate = doorstate.becomeClose();
	}
	
	public void becomeOpen() {
		doorstate = doorstate.becomeOpen();
	}
	
	public DoorState get_State() {
		return this.doorstate;
	}
	


}
