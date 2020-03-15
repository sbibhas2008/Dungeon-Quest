package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity{

    MoveBehavior moveBehavior;
    public List<Entity> Stash = new ArrayList<Entity>();
    private int moves;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.moves = 0;
    }
    
    public void notifyPotion() {
    	for (Entity e : Stash) {
    		if (e instanceof Potion) {
    			((Potion)e).update(this.moves, this);
    			break;
    		}
    	}
    }
    
    // returns 1 if enemy dies, 2 if the player dies 0 otherwise
    public int notifyEnemies() {
    	boolean kill_enemy = false;
    	boolean remove_sword = false;
    	Entity enemyInstance = null;
    	Sword sword = null;
    	for (Entity e: dungeon.getEntities()) {
    		if (e instanceof Enemy) {
				((Enemy)e).move();
    				List<Entity> curr = dungeon.get_curr_adjacent(e.getX(), e.getY(), "c");
    				for (Entity curr_e : curr) {
    					if (curr_e instanceof Player) {
    						for(Entity p_e : Stash) {
    							remove_sword = false;
    							if (p_e instanceof Potion) {
    								if (((Potion) p_e).getPotionState().isActive()) {
    									// enemy dies
    									kill_enemy = true;
    									enemyInstance = e;
    								}	
    							}
    							else if (p_e instanceof Sword) {
    								// enemy dies
    								kill_enemy = true;
									enemyInstance = e;
    								((Sword) p_e).reduce_charge();
    		    					if (((Sword) p_e).get_charge() == 0) {
    		    						remove_sword = true;
    		    						sword = (Sword) p_e;
    		    	    			}
    							}
    						}
    						if (remove_sword) {
	    	    				Stash.remove(sword);
	    	    				return 1;

    						}
    						if (kill_enemy) {
								dungeon.getEntities().remove(enemyInstance);
								return 1;
    						}
    						else {
    							dungeon.killPlayer();
    							return 2;
    						}
    					}
    				}
    		}
    	}
    	return 0;
    }
    
  
    // returns null if no entities die
    public HashMap<List<Integer>, String> kill_on_explosion() {
    	HashMap <List<Integer>, String> deaths = new HashMap<List<Integer>, String>();
    	Bomb bomb = null;
    	for (Entity e : dungeon.getEntities()) {
    		if (e instanceof Bomb) {
    			if (((Bomb) e).getBombState().isLit() && ((Bomb) e).get_fuse() == 1) {
    				bomb = (Bomb) e;
    				break;
    			}
    		}
    	}
    	if (bomb == null) {
    		return null;
    	}
       	Entity e_up = dungeon.checkEntity(bomb.getX(), bomb.getY(), "u");
    	Entity e_down = dungeon.checkEntity(bomb.getX(), bomb.getY(), "d");
    	Entity e_left = dungeon.checkEntity(bomb.getX(), bomb.getY(), "l");
    	Entity e_right = dungeon.checkEntity(bomb.getX(), bomb.getY(), "r");
    	if (e_up != null) {
    		List<Integer> coordinates = new ArrayList<Integer>();
			coordinates.add(bomb.getX());
			coordinates.add(bomb.getY()+1);
    		if (e_up instanceof Boulder || e_up instanceof Enemy || e_up instanceof Player) {
    	    	if (e_up instanceof Player) {
    	    		if (!((Player) e_up).checkActivePotion()) {
    	    			dungeon.killPlayer();
    	    			deaths.put(coordinates, "player");
    	    		}	
    	    }
    	    	else {
    	    		if (e_up instanceof Boulder) {
    	    			dungeon.getEntities().remove(e_up);
    	    			deaths.put(coordinates, "boulder");
    	    		}
    	    		else {
    	    			dungeon.getEntities().remove(e_up);
    	    			deaths.put(coordinates,"enemy");
    	    		}
    	    	}
    		}
    	}
    	if (e_down != null) {
    		List<Integer> coordinates = new ArrayList<Integer>();
			coordinates.add(bomb.getX());
			coordinates.add(bomb.getY()-1);
    		if (e_down instanceof Boulder || e_down instanceof Enemy || e_down instanceof Player) {
    	    	if (e_down instanceof Player) {
    	    		if (!((Player) e_down).checkActivePotion()) {
    	    			dungeon.killPlayer();
    	    			deaths.put(coordinates, "player");
    	    		}	
    	    }
    	    	else {
    	    		if (e_down instanceof Boulder) {
    	    			dungeon.getEntities().remove(e_down);
    	    			deaths.put(coordinates, "boulder");
    	    		}
    	    		else {
    	    			dungeon.getEntities().remove(e_down);
    	    			deaths.put(coordinates, "enemy");
    	    		}
    	    	}
    		}
    	}
    	if (e_left != null) {
    		List<Integer> coordinates = new ArrayList<Integer>();
			coordinates.add(bomb.getX()-1);
			coordinates.add(bomb.getY());
    		if (e_left instanceof Boulder || e_left instanceof Enemy || e_left instanceof Player) {
    	    	if (e_left instanceof Player) {
    	    		if (!((Player) e_left).checkActivePotion()) {
    	    			dungeon.killPlayer();
    	    			deaths.put(coordinates, "player");
    	    		}	
    	    }
    	    	else {
    	    		if (e_left instanceof Boulder) {
    	    			dungeon.getEntities().remove(e_left);
    	    			deaths.put(coordinates, "boulder");
    	    		}
    	    		else {
    	    			dungeon.getEntities().remove(e_left);
    	    			deaths.put(coordinates, "enemy");
    	    		}
    	    	}
    		}
    	}
    	if (e_right != null) {
    		List<Integer> coordinates = new ArrayList<Integer>();
			coordinates.add(bomb.getX()+1);
			coordinates.add(bomb.getY());
    		if (e_right instanceof Boulder || e_right instanceof Enemy || e_right instanceof Player) {
    	    	if (e_right instanceof Player) {
    	    		if (!((Player) e_right).checkActivePotion()) {
    	    			dungeon.killPlayer();
    	    			deaths.put(coordinates, "player");
    	    		}	
    	    }
    	    	else {
    	    		if (e_right instanceof Boulder) {
    	    			dungeon.getEntities().remove(e_right);
    	    			deaths.put(coordinates, "boulder");
    	    		}
    	    		else {
    	    			dungeon.getEntities().remove(e_right);
    	    			deaths.put(coordinates, "enemy");
    	    		}
    	    	}
    		}
    	}
    	return deaths;
    }
    
    public void notifyBomb() {
    	for (Entity e :dungeon.getEntities()) {
    		if (e instanceof Bomb) {
    			if (((Bomb) e).getBombState().isLit()) {
    				if (((Bomb) e).get_fuse() == 0) {
    					dungeon.getEntities().remove(e);
    					break;
    				}
    				else {
    					((Bomb) e).burn_fuse();
    				}
    			}
    		}
    	}
    }

    public boolean moveUp() {
    	List<Entity> entities = dungeon.get_curr_adjacent(getX(), getY(), "u");
    	Entity e = null;
    	if (entities.size() == 0) {
    		moveBehavior = new MoveUp(this);
			moveBehavior.move(); 
			moves++;
			notifyPotion();
			notifyBomb();
			return true;
    	}
    	else if (entities.size() == 2) {
    		for (Entity et: entities) {
    			if (et instanceof Boulder) {
    				e = et;
    			}	
    		}
    	}
    	else {
    		e = entities.get(0);
    	}
   		if (!(e instanceof Wall)) {
       		if (e instanceof Boulder) {
       			boolean check = ((Boulder) e).moveUp(this);
       			if (check) {
       				if (getY() < dungeon.getHeight() - 1) {
   	    				moveBehavior = new MoveUp(this);
   	    				moveBehavior.move(); 
   	    				moves++;
   	    				notifyPotion();
   	    				notifyBomb();
   	    				return true;
       				}
       			}
       		}
       		else if (e instanceof Door) {
       			if (!(((Door) e).get_State().isOpen())) {
       				if (Stash.size() > 0) {
       					for (Entity item : Stash) {
           					if (item instanceof Key) {
           						int id = ((Key) item).get_id();
           						if (id == ((Door) e).get_id()) {
           							// once the key is used, it is popped off the Stash
           		    				Stash.remove(item);
           		    				((Door) e).becomeOpen();
           		    				if (getY() < dungeon.getHeight() - 1) {
           			    				moveBehavior = new MoveUp(this);
           			    				moveBehavior.move();  
           			    				moves++;
           			    				notifyPotion();
           			    				notifyBomb();
           			    				return true;
           		    				}
           						}
           					}	
           				}
       				}
       			}
       			else {
      				if (getY() < dungeon.getHeight() - 1) {
   	    				moveBehavior = new MoveUp(this);
   	    				moveBehavior.move();  
   	    				moves++;
   	    				notifyPotion();
   	    				notifyBomb();
   	    				return true;
       				}
       			}
       		}
       		else {
       			if (getY() < dungeon.getHeight() - 1) {
       				moveBehavior = new MoveUp(this);
       				moveBehavior.move();  
       				moves++;
       				notifyPotion();
       				notifyBomb();
       				return true;
   				}
       		}
       	}
   	return false;
    }
    public boolean moveDown() {
    	List<Entity> entities = dungeon.get_curr_adjacent(getX(), getY(), "d");
    	Entity e = null;
    	if (entities.size() == 0) {
    		moveBehavior = new MoveDown(this);
			moveBehavior.move(); 
			moves++;
			notifyPotion();
			notifyBomb();
			return true;
    	}
    	else if (entities.size() == 2) {
    		for (Entity et: entities) {
    			if (et instanceof Boulder) {
    				e = et;
    			}	
    		}
    	}
    	else {
    		e = entities.get(0);
    	}
	   	if (!(e instanceof Wall)) {
	   		if (e instanceof Boulder) {
	   			boolean check = ((Boulder) e).moveDown(this);
	   			if (check) {
	   				if (getY() > 0) {
	    				moveBehavior = new MoveDown(this);
	    				moveBehavior.move();  
	    				moves++;
	    				notifyPotion();
	    				notifyBomb();
	    				return true;
	   				}
	   			}
	   		}
	   		else if (e instanceof Door) {
	   			if (!(((Door) e).get_State().isOpen())) {
	   				if (Stash.size() > 0) {
	   					for (Entity item : Stash) {
	       					if (item instanceof Key) {
	       						int id = ((Key) item).get_id();
	       						if (id == ((Door) e).get_id()) {
	       							// once the key is used, it is popped off the Stash
	       		    				Stash.remove(item);
	       		    				((Door) e).becomeOpen();
	       		    				if (getY() > 0) {
	       			    				moveBehavior = new MoveDown(this);
	       			    				moveBehavior.move();  
	       			    				moves++;
	       			    				notifyPotion();
	       			    				notifyBomb();
	       			    				return true;
	       		    				}
	       						}
	       					}	
	   					}
	   				}
	   			}
	   			else {
    				if (getY() > 0) {
	    				moveBehavior = new MoveDown(this);
	    				moveBehavior.move(); 
	    				moves++;
	    				notifyPotion();
	    				notifyBomb();
	    				return true;
    				}
    			}
    		}
    		else {
    			if (getY() > 0) {
    				moveBehavior = new MoveDown(this);
    				moveBehavior.move(); 
    				moves++;
    				notifyPotion();
    				notifyBomb();
    				return true;
				}
    		}
    	}
    	return false;
    }

    public boolean moveLeft() {
    	List<Entity> entities = dungeon.get_curr_adjacent(getX(), getY(), "l");
    	Entity e = null;
    	if (entities.size() == 0) {
    		moveBehavior = new MoveLeft(this);
			moveBehavior.move(); 
			moves++;
			notifyPotion();
			notifyBomb();
			return true;
    	}
    	else if (entities.size() == 2) {
    		for (Entity et: entities) {
    			if (et instanceof Boulder) {
    				e = et;
    			}	
    		}
    	}
    	else {
    		e = entities.get(0);
    	}
    	if (!(e instanceof Wall)) {
    		if (e instanceof Boulder) {
    			boolean check = ((Boulder) e).moveLeft(this);
    			if (check) {
    				if (getX() > 0) {
	    				moveBehavior = new MoveLeft(this);
	    				moveBehavior.move();  
	    				moves++;
	    				notifyPotion();
	    				notifyBomb();
	    				return true;
    				}
    			}
    		}
    		else if (e instanceof Door) {
    			if (!(((Door) e).get_State().isOpen())) {
    				if (Stash.size() > 0) {
    					for (Entity item : Stash) {
        					if (item instanceof Key) {
        						int id = ((Key) item).get_id();
        						if (id == ((Door) e).get_id()) {
        							// once the key is used, it is popped off the Stash
        		    				Stash.remove(item);
        		    				((Door) e).becomeOpen();
        		    				if (getX() > 0) {
        			    				moveBehavior = new MoveLeft(this);
        			    				moveBehavior.move();  
        			    				moves++;
        			    				notifyPotion();
        			    				notifyBomb();
        			    				return true;
        		    				}
        						}
        					}	
        				}
    				}
    			}
    			else {
    				if (getX() > 0) {
	    				moveBehavior = new MoveLeft(this);
	    				moveBehavior.move();  
	    				moves++;
	    				notifyPotion();
	    				notifyBomb();
	    				return true;
    				}
    			}
    		}
    		else {
    			if (getX() > 0) {
    				moveBehavior = new MoveLeft(this);
    				moveBehavior.move();
    				moves++;
    				notifyPotion();
    				notifyBomb();
    				return true;
				}
    		}
    	}
    	return false;
    }

    public boolean moveRight() {
    	List<Entity> entities = dungeon.get_curr_adjacent(getX(), getY(), "r");
    	Entity e = null;
    	if (entities.size() == 0) {
    		moveBehavior = new MoveRight(this);
			moveBehavior.move(); 
			moves++;
			notifyPotion();
			notifyBomb();
			return true;
    	}
    	else if (entities.size() == 2) {
    		for (Entity et: entities) {
    			if (et instanceof Boulder) {
    				e = et;
    			}	
    		}
    	}
    	else {
    		e = entities.get(0);
    	}
    	if (!(e instanceof Wall)) {
    		if (e instanceof Boulder) {
    			boolean check = ((Boulder) e).moveRight(this);
    			if (check) {
    				if (getX() < dungeon.getWidth() - 1) {
	    				moveBehavior = new MoveRight(this);
	    				moveBehavior.move();  
	    				moves++;
	    				notifyPotion();
	    				notifyBomb();
	    				return true;
    				}
    			}
    		}
    		else if (e instanceof Door) {
    			if (!(((Door) e).get_State().isOpen())) {
    				if (Stash.size() > 0) {
    					for (Entity item : Stash) {
        					if (item instanceof Key) {
        						int id = ((Key) item).get_id();
        						if (id == ((Door) e).get_id()) {
        							// once the key is used, it is popped off the Stash
        		    				Stash.remove(item);
        		    				((Door) e).becomeOpen();
        		    				if (getX() < dungeon.getWidth() - 1) {
        			    				moveBehavior = new MoveRight(this);
        			    				moveBehavior.move();  
        			    				moves++;
        			    				notifyPotion();
        			    				notifyBomb();
        			    				return true;
        		    				}
        						}
        					}	
        				}
    				}
    			}
    			else {
    				if (getX() < dungeon.getWidth() - 1) {
	    				moveBehavior = new MoveRight(this);
	    				moveBehavior.move();  
	    				moves++;
	    				notifyPotion();
	    				notifyBomb();
	    				return true;
    				}
    			}
    		}
    		else {
    			if (getX() < dungeon.getWidth() - 1) {
    				moveBehavior = new MoveRight(this);
    				moveBehavior.move();  
    				moves++;
    				notifyPotion();
    				notifyBomb();
    				return true;
				}
    		}
    	}
    	return false;
    }
    
    public Entity performCarry() {
    	List<Entity> list = dungeon.get_curr_adjacent(getX(), getY(), "c");
    	for (Entity e : list) {
    		if (!(e instanceof Player)) {
    			if (e.carryBehavior instanceof CanBePicked) {
    	    		boolean check = e.carryBehavior.pickUp(e, this);
    	    		if (check) return e;
    	    	}
    		}
    	}
    	return null;
    }
    
    public void dropBomb(Bomb b) {
		b.becomeLit();	
	}
    
    public int get_moves() {
    	return this.moves;
    }
    
    public void activatePotion(Potion p, int start) {
    	p.becomeActive();
      	p.setStart(start);
    }
    
    public boolean checkActivePotion() {
    	for (Entity e : Stash) {
    		if (e instanceof Potion) {
    			if (((Potion) e).getPotionState().isActive())
    				return true;
    		}
    			
    	}
    	return false;
    }
    public boolean checkSword() {
    	for (Entity e : Stash) {
    		if (e instanceof Sword) {
    			return true;
    		}	
    	}
    	return false;
    }
    
    
}
