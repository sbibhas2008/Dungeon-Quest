package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");
      

        Dungeon dungeon = new Dungeon(width, height);
        dungeon.set_goal(get_Goals(json.getJSONObject("goal-condition")));

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
        	String type = jsonEntities.getJSONObject(i).getString("type");
        	
        	loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
    	// contains goal conditions
    	
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            // set wall to cannot be picked.
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
        	Boulder boulder = new Boulder(x,y, dungeon);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "key":
        	Key key = new Key(x,y, dungeon);
        	key.set_id(json.getInt("id"));
        	onLoad(key);
        	entity = key;
        	break;
        case "door":
        	Door door = new Door(x,y, dungeon);
        	door.set_id(json.getInt("id"));
        	onLoad(door);
        	entity = door;
        	break;	
        case "switch":
        	FloorSwitch fs = new FloorSwitch(x, y, dungeon);
        	onLoad(fs);
        	entity = fs;
        	break;	
        case "exit":
        	Exit exit = new Exit(x,y, dungeon);
        	CannotBePicked cb_exit = new CannotBePicked();
        	exit.setCarryBehavior(cb_exit);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x,y, dungeon);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "bomb":
        	Bomb bomb = new Bomb(x,y, dungeon);
        	CanBePicked cb_bomb = new CanBePicked();
        	bomb.setCarryBehavior(cb_bomb);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(x,y, dungeon);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "sword":
        	Sword sword = new Sword(x,y, dungeon);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "invincibility":
        	Potion potion = new Potion(x,y, dungeon);
        	onLoad(potion);
        	entity = potion;
        	break;
        case "player":
            Player player = new Player(x, y, dungeon);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;	
        	
        	// TODO Handle other possible entities
        }
        if (entity != null && !(entity instanceof Player)) dungeon.addEntity(entity);
    }
    
    public Component get_Goals (JSONObject goal) {
    	String condition = goal.getString("goal");
    	Component root = null;
    	Composite c = null;
        if (condition.equals("AND")) {
        	c = new Composite("AND");
        	JSONArray subG = goal.getJSONArray("subgoals");
        	for (int i = 0; i < subG.length(); i++) {
        		  JSONObject g = subG.getJSONObject(i);
        		  if (g.getString("goal").equals("AND") || g.getString("goal").equals("OR")) {
        			  root = get_Goals(g);
        			  c.add(root);
        		  }
        		  else {
        			  root = new Leaf(g.getString("goal"));
        			  c.add(root);
        		  }
        	}
        }
        else if (condition.equals("OR")) {
        	c = new Composite("OR");
        	JSONArray subG = goal.getJSONArray("subgoals");
        	for (int i = 0; i < subG.length(); i++) {
        		  JSONObject g = subG.getJSONObject(i);
        		  if (g.getString("goal").equals("AND") || g.getString("goal").equals("OR")) {
        			  root = get_Goals(g);
        			  c.add(root);

        		  }
        		  else {
        			  root = new Leaf(g.getString("goal"));
        			  c.add(root);

        		  }
        	}
        }
        // no condition
        else {
        	Leaf l = new Leaf(goal.getString("goal"));
        	return l;
        }
		return c;
    }
    
    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Door door);
    
    public abstract void onLoad(FloorSwitch floorswitch);
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Bomb bomb);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad(Potion potion);

    // TODO Create additional abstract methods for the other entities

}
