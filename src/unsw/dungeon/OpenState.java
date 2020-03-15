package unsw.dungeon;

public class OpenState implements DoorState{
	
	Door d;
	public OpenState(Door d) {
		this.d = d;
	}

	@Override
	public DoorState becomeOpen() {
		// TODO Auto-generated method stub
		// Do Nothing
		return null;
	}

	@Override
	public DoorState becomeClose() {
		// TODO Auto-generated method stub
		return d.getClose();
		
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return true;
	}

}
 