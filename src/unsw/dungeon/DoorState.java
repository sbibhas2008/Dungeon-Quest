package unsw.dungeon;

public interface DoorState {
	public DoorState becomeOpen();
	public DoorState becomeClose();
	public boolean isOpen();
}
