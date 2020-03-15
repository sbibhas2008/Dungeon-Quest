package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity{
	
    MoveBehavior moveBehavior;

	public Boulder(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		carryBehavior = new CannotBePicked();
		// TODO Auto-generated constructor stub
	}
	
	 public boolean moveUp(Player p) {
		 	List<Entity> e = dungeon.get_curr_adjacent(this.getX(), this.getY(), "u");
		 	if (e.size() > 1) {
		 		return false;
		 	}
		 	if (p.getY() == this.getY()-1 && dungeon.checkEntity(this.getX(),this.getY(),"u") == null || 
		 			p.getY() == this.getY()-1 && dungeon.checkEntity(this.getX(),this.getY(),"u") instanceof FloorSwitch) {
		 		if (getY() < dungeon.getHeight() - 1) {
    				moveBehavior = new MoveUp(this);
    				moveBehavior.move();  
    				return true;
				}
		 	}
	    	return false;
	    }

	  public boolean moveDown(Player p) {
		  List<Entity> e = dungeon.get_curr_adjacent(this.getX(), this.getY(), "d");
		 	if (e.size() > 1) return false;
		  	if (p.getY() == this.getY()+1 && dungeon.checkEntity(this.getX(),this.getY(),"d") == null ||
		  			p.getY() == this.getY()+1 && dungeon.checkEntity(this.getX(),this.getY(),"d") instanceof FloorSwitch) {
		  		if (getY() < dungeon.getHeight() - 1) {
		  			if (getY() > 0) {
	    				moveBehavior = new MoveDown(this);
	    				moveBehavior.move();  
	    				return true;
    				}
				}
		  	}	
		  	return false;
	   }

	    public boolean moveLeft(Player p) {
	    	List<Entity> e = dungeon.get_curr_adjacent(this.getX(), this.getY(), "l");
		 	if (e.size() > 1) return false;
	    	if (p.getX() == this.getX()+1 && dungeon.checkEntity(this.getX(),this.getY(),"l") == null ||
	    			p.getX() == this.getX()+1 && dungeon.checkEntity(this.getX(),this.getY(),"l") instanceof FloorSwitch) {
	    		if (getX() > 0) {
    				moveBehavior = new MoveLeft(this);
    				moveBehavior.move();  
    				return true;
				}
	    	}	
	    	return false;
	    }

	    public boolean moveRight(Player p) {
	    	List<Entity> e = dungeon.get_curr_adjacent(this.getX(), this.getY(), "r");
		 	if (e.size() > 1) return false;
	    	if ((p.getX() == this.getX()-1) && (dungeon.checkEntity(this.getX(),this.getY(),"r") == null ||
	    			dungeon.checkEntity(this.getX(),this.getY(),"r") instanceof FloorSwitch)) {
	    		if (getX() < dungeon.getWidth() - 1) {
    				moveBehavior = new MoveRight(this);
    				moveBehavior.move();  
    				return true;
				}
	    	}	
	    	return false;
	    }
	
}
