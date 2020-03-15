package unsw.dungeon;

public class Enemy extends Entity{

    MoveBehavior moveBehavior;
    private Entity player;
	
	public Enemy(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		player = dungeon.getPlayer();
		carryBehavior = new CannotBePicked();
		// TODO Auto-generated constructor stub
	}
	
	public void move() {
		int x1 = player.getX();
		int y1 = player.getY();
		Dungeon dungeon = this.getDungeon();
		if (getX() == x1 && getY() == y1) return;
		if (getX() > x1 && dungeon.isValidCoordinate(getX(), getY())){
			if (((Player) player).checkActivePotion()) {
				if ((dungeon.checkEntity(getX(), getY(), "r") == null) || (dungeon.checkEntity(getX(), getY(), "r") instanceof Door)) {
					if (dungeon.checkEntity(getX(), getY(), "r") instanceof Door) {
						Entity door = dungeon.checkEntity(getX(), getY(), "r");
						if (((Door) door).get_State().isOpen()) {
							MoveBehavior ml = new MoveRight(this);
							ml.move();
							return;
						}
					}
					else {
						MoveBehavior ml = new MoveRight(this);
						ml.move();
						return;
					}
				}
			}
			else if ((dungeon.checkEntity(getX(), getY(), "l") == null) || (dungeon.checkEntity(getX(), getY(), "l") instanceof Door)) {
				if (dungeon.checkEntity(getX(), getY(), "l") instanceof Door) {
					Entity door = dungeon.checkEntity(getX(), getY(), "l");
					if (((Door) door).get_State().isOpen()) {
						MoveBehavior ml = new MoveLeft(this);
						ml.move();
						return;
					}
				}
				else {
					MoveBehavior ml = new MoveLeft(this);
					ml.move();
					return;
				}
				
			}
					
		}
		if (getY() > y1 && dungeon.isValidCoordinate(getX(), getY())) {
			
			if (((Player) player).checkActivePotion()) {
				if ((dungeon.checkEntity(getX(), getY(), "u") == null) || (dungeon.checkEntity(getX(), getY(), "u") instanceof Door)) {
					if (dungeon.checkEntity(getX(), getY(), "u") instanceof Door) {
						Entity door = dungeon.checkEntity(getX(), getY(), "r");
						if (((Door) door).get_State().isOpen()) {
							MoveBehavior ml = new MoveUp(this);
							ml.move();
							return;
						}
					}
					else {
						MoveBehavior ml = new MoveUp(this);
						ml.move();
						return;
					}
				}
			}
			else if ((dungeon.checkEntity(getX(), getY(), "d") == null) || (dungeon.checkEntity(getX(), getY(), "d") instanceof Door)) {
				if (dungeon.checkEntity(getX(), getY(), "d") instanceof Door) {
					Entity door = dungeon.checkEntity(getX(), getY(), "d");
					if (((Door) door).get_State().isOpen()) {
						MoveBehavior ml = new MoveDown(this);
						ml.move();
						return;
					}
				}
				else {
					MoveBehavior ml = new MoveDown(this);
					ml.move();
					return;
				}
			}
		}
		if (x1 > getX() && dungeon.isValidCoordinate(getX(), getY())) {
			if (((Player) player).checkActivePotion()) {
				if ((dungeon.checkEntity(getX(), getY(), "l") == null) || (dungeon.checkEntity(getX(), getY(), "l") instanceof Door)) {
					if (dungeon.checkEntity(getX(), getY(), "l") instanceof Door) {
						Entity door = dungeon.checkEntity(getX(), getY(), "l");
						if (((Door) door).get_State() instanceof OpenState) {
							MoveBehavior ml = new MoveDown(this);
							ml.move();
							return;
						}
					}
					else {
						MoveBehavior ml = new MoveDown(this);
						ml.move();
						return;
					}
				}
			}
			else if ((dungeon.checkEntity(getX(), getY(), "r") == null) || (dungeon.checkEntity(getX(), getY(), "r") instanceof Door)) {
				if (dungeon.checkEntity(getX(), getY(), "r") instanceof Door) {
					Entity door = dungeon.checkEntity(getX(), getY(), "r");
					if (((Door) door).get_State().isOpen()) {
						MoveBehavior ml = new MoveRight(this);
						ml.move();
						return;
					}
				}
				else {
					MoveBehavior ml = new MoveRight(this);
					ml.move();
					return;
				}
			}
					
		}
		if (y1 > getY() && dungeon.isValidCoordinate(getX(), getY())){
			if (((Player) player).checkActivePotion()) {
				if ((dungeon.checkEntity(getX(), getY(), "d") == null) || (dungeon.checkEntity(getX(), getY(), "d") instanceof Door)) {
					if (dungeon.checkEntity(getX(), getY(), "d") instanceof Door) {
						Entity door = dungeon.checkEntity(getX(), getY(), "d");
						if (((Door) door).get_State() instanceof OpenState) {
							MoveBehavior ml = new MoveDown(this);
							ml.move();
							return;
						}
					}
					else {
					MoveBehavior ml = new MoveDown(this);
					ml.move();
					return;
					}
				}
			}
			else if ((dungeon.checkEntity(getX(), getY(), "u") == null) || (dungeon.checkEntity(getX(), getY(), "u") instanceof Door)) {
				if (dungeon.checkEntity(getX(), getY(), "u") instanceof Door) {
					Entity door = dungeon.checkEntity(getX(), getY(), "u");
					if (((Door) door).get_State().isOpen()) {
						MoveBehavior ml = new MoveUp(this);
						ml.move();
						return;
					}
				}
				else {
				MoveBehavior ml = new MoveUp(this);
				ml.move();
				return;
				}
			}
		}
		
	
	}
	public int MahattanDistance() {
		int x1 = player.getX();
		int y1 = player.getY();
		return Math.abs(this.getX() - x1) + Math.abs(this.getY() - y1);
	}
	
}
