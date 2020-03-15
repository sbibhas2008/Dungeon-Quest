/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Component goal; 
   
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goal = null;
   }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }
    
    public Component get_goal() {
    	return this.goal;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void set_goal(Component c) {
    	this.goal = c;
    }
    
    public List<Entity> getEntities() {
    	return this.entities;
    }
    
    public boolean isValidCoordinate(int x, int y) {
    	if ((x >= 0 && x <= width) && (y >= 0 && y < height))
    		return true;
    	return false;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    // Method to check if a coordinate has an entity in the dungeon
    public Entity checkEntity(int x, int y, String next_move) {
    	if (next_move.equals("r")) {
    		for (Entity e : entities) {
        		if (e.getX() == x+1 && e.getY() == y) {
        			return e;
        		}	
        	}
    		if (player.getX() == x+1 && player.getY() == y)
    			return player;
    	}
    	else if (next_move.equals("l")) {
    		for (Entity e : entities) {
        		if (e.getX() == x-1 && e.getY() == y) 
        			return e;
        	}
    		if (player.getX() == x-1 && player.getY() == y)
    			return player;
    	}
    	else if (next_move.equals("u")) {
    		for (Entity e : entities) {
        		if (e.getY() == y+1 && e.getX() == x) 
        			return e;
        	}
    		if (player.getY() == y+1 && player.getX() == x)
    			return player;
    	}
    	else {
    		for (Entity e : entities) {
        		if (e.getY() == y-1 && e.getX() == x) 
        			return e;
        	}
    		if (player.getY() == y-1 && player.getX() == x)
    			return player;
    	}
    	return null;
    }
    
    public List<Entity> get_curr_adjacent(int x, int y, String loc) {
    	List<Entity> list = new ArrayList<Entity>();
    	if (loc.equals("c")) {
    		for (Entity e : entities) {
        		if (e.getY() == y && e.getX() == x)
        			list.add(e);
        	}
    		if (player != null) {
    			if (player.getX() == x && player.getY() == y) 
        			list.add(player);
    		}
    	}
    	else if (loc.equals("u")) {
    		for (Entity e : entities) {
        		if (e.getY() == y+1 && e.getX() == x)
        			list.add(e);
        	}
    	}
    	else if (loc.equals("d")) {
    		for (Entity e : entities) {
        		if (e.getY() == y-1 && e.getX() == x)
        			list.add(e);
        	}
    	}
    	else if (loc.equals("l")) {
    		for (Entity e : entities) {
        		if (e.getY() == y && e.getX() == x-1)
        			list.add(e);
        	}
    	}
    	else if (loc.equals("r")) {
    		for (Entity e : entities) {
        		if (e.getY() == y && e.getX() == x+1)
        			list.add(e);
        	}
    	}
    	
    	
    	return list;
    }
    
    public void removeEntity(Entity e) {
    	entities.remove(e);
    }
    
    public void killPlayer() {
    	
    	this.player = null;
    }
    
    public boolean check_player() {
    	if (getPlayer() == null) return false;
    	return true;
    }
    public boolean check_curr_state(Component goal) {
    	boolean check = false;
    	if (goal.get_name().equals("AND")) {
    		for (Component c : goal.get_children()) {
    			check = check_curr_state(c);
    			if (check == false) return false;
    		}
    		return true;
    	}
    	else if (goal.get_name().equals("OR")) {
    		for (Component c : goal.get_children()) {
    			check = check_curr_state(c);
    			if (check == true) return true;
    		}
    		return false;
    	}
    	else {
    		String condition = goal.get_name();
    		if (condition.equals("treasure")) {
    			boolean found = false;
    			for (Entity e : entities) {
    				if (e instanceof Treasure) {

    					found = true;
    					break;
    				}
    			}
    			if (!found) {
    				check = true;
    			}
    		}
    		else if (condition.equals("exit")) {
    			for (Entity e : entities) {
    				if (e instanceof Exit) {
    					if (e.getX() == player.getX() && e.getY() == player.getY()) {
    						check = true;
    						break;
    					}
    				}
    			}
    		}
    		else if (condition.equals("enemies")) {
    			boolean found = false;
    			for (Entity e : entities) {
    				if (e instanceof Enemy) {
    					found = true;
    					break;
    				}
    			}
    			if (!found) 
    				check = true;
    		}
    		else if (condition.equals("boulders")) {
    			boolean found = true;
    			for (Entity e : entities) {
    				if (e instanceof FloorSwitch) {
    					List<Entity> curr = get_curr_adjacent(e.getX(), e.getY(), "c");
    					boolean found_boulder = false;
    					for (Entity e1 : curr) {
    						if (e1 instanceof Boulder) found_boulder = true;
    					}
    					if (found_boulder == false) {
    						found = false;
        					break;
    					}
    				}
    			}
        		if (found) check = true;
    		}
    	}
    	return check;
    }
    
    public Entity trigger_entity() {
    	if (player != null) {
    		List<Entity> curr = get_curr_adjacent(player.getX(), player.getY(), "c");
        	for (Entity e: curr) {
        		if (e instanceof Door) {
        			return e;
        		}
        	}
        	for (Entity e: entities) {			
        		if (e instanceof Bomb) {
        			if (((Bomb) e).getBombState() instanceof LitState) {
        				return e;
        			}
        		}
        	}
    	}
    	// if the player is null there are 2 things to take care of 
    	// 1. remove player image from the dungeon, 2. if it were killed by a bomb, explode it
    	else {
    		for (Entity e : entities) {
        		if (e instanceof Bomb) {
        			if (((Bomb) e).getBombState() instanceof LitState) {
        				return e;
        			}
        		}
    		}	
    	}
    	return null;
    }
    
    public int getNumEnemy() {
    	int num = 0;
    	for (Entity e : entities) {
    		if (e instanceof Enemy) {
    			num++;
    		}
    	}
    	return num;
    }
    
    @Override
    public String toString() {
    	String goal = "";
    	if (this.goal.get_name().equals("AND") || this.goal.get_name().equals("OR")) {
    		int x = 0;
    		for (Component c : this.goal.get_children()) {
    			if (c.get_children() != null) {
    				int i = 0;
    				for (Component leaf : c.get_children()) {
    					if (i == c.get_children().size() - 1) {
    						goal += leaf.get_name() + " ";
    					}
    					else {
        					goal += leaf.get_name() + " " + c.get_name() + " ";
    					}
    					i++;
    				}
    			}
    			else {
    				goal += c.get_name() + " ";
    			}
    			if (x != this.goal.get_children().size() -1) {
    				goal += this.goal.get_name() + " ";
    			}
    			
    			x++;
    		}
    	}
    	else {
    		goal = this.goal.get_name();
    	}
		return goal;
    	
    }
}


	

