package unsw.dungeon;

public class CanBePicked implements CarryBehavior{

	@Override
	public boolean pickUp(Entity e, Player player) {
		// TODO Auto-generated method stub
		boolean check_condition = false;
		if (e instanceof Key) {
			for (Entity item : player.Stash) {
				if (item instanceof Key) {
					check_condition = true;
					break;
				}
			}
		}
		else if (e instanceof Treasure) {
			for (Entity e1 : player.Stash) {
				if (e1 instanceof Treasure) {
					((Treasure) e1).add_treasure();
					check_condition= true;
				}
			}
			
		}
		else if (e instanceof Sword) {
			for (Entity e2 : player.Stash) {
				if (e2 instanceof Sword) {
					check_condition= true;
					break;
				}
			}	
		}
		if (! check_condition) {
			player.Stash.add(e);
			e.getDungeon().removeEntity(e);
		}
		return !check_condition;
	}
	
	
}
