package unsw.dungeon;

public class ClosedState implements DoorState {
	
	Door d;
	public ClosedState(Door d) {
		this.d = d;
	}


	@Override
	public DoorState becomeOpen() {
		// TODO Auto-generated method stub
		return d.getOpen();
	}

	@Override
	public DoorState becomeClose() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

}
