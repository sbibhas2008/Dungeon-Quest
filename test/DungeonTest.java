package unsw.dungeon.test;

import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import unsw.dungeon.ActivePotion;
import unsw.dungeon.Bomb;
import unsw.dungeon.Boulder;
import unsw.dungeon.CanBePicked;
import unsw.dungeon.CarryBehavior;
import unsw.dungeon.Component;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonController;
import unsw.dungeon.DungeonControllerLoader;
import unsw.dungeon.Enemy;
import unsw.dungeon.Key;
import unsw.dungeon.LitState;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;
import unsw.dungeon.Wall;

public class DungeonTest {
	
	@Test
	public void BasicMovement() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		assertEquals(true, player.moveUp(), "Nothing to stop the player from moving");
		assertEquals(true, player.moveDown(), "Nothing to stop the player from moving");
		assertEquals(true, player.moveDown(), "Nothing to stop the player from moving");
		assertEquals(false, player.moveDown(), "Cannot move out of boundary");
		assertEquals(true, player.moveLeft(), "Cannot move out of boundary");
		assertEquals(false, player.moveLeft(), "Cannot move out of boundary");
		assertEquals(true, player.moveRight(), "Nothing to stop the player from moving");
	}
	
	@Test
	public void WallMovement() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Wall wall = new Wall(3,1);
		dungeon.addEntity(wall);
		assertEquals(true, player.moveRight(), "Nothing to stop the player from moving");
		assertEquals(false, player.moveRight(), "Wall stops the player");
	}
	
	@Test
	public void BouldersMovement() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Boulder b1 = new Boulder (2,1, dungeon);
		dungeon.addEntity(b1);
		assertEquals(true, player.moveRight(), "Player can push 1 boulder");
		Boulder b2 = new Boulder (4,1 , dungeon);
		dungeon.addEntity(b2);
		assertEquals(false, player.moveRight(), "Player cannot push 2 boulders");
	}
	
	@Test 
	public void BombTest() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Bomb bomb = new Bomb(2,1);
		dungeon.addEntity(bomb);
		assertEquals(true, player.moveRight(), "Player can move to entity which can be picked up");
		player.performCarry();
		assertEquals(1, player.Stash.size(), "Player carried bomb in their stash");
		player.dropBomb(bomb);
		assertEquals(0, player.Stash.size(), "Player dropped the bomb");
		assertEquals(true, bomb.getBombState() instanceof LitState, "State of bomb changed to lit");
	}
	
	@Test
	public void DoorsAndKeysTest() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Key key1 = new Key(2,1);
		key1.set_id(1);
		dungeon.addEntity(key1);
		Key key2 = new Key(3,1);
		key2.set_id(2);
		dungeon.addEntity(key2);
		player.moveRight();
		player.performCarry();
		assertEquals(1, player.Stash.size(), "Player carried a key in their stash");
		player.moveRight();
		player.performCarry();
		assertEquals(1, player.Stash.size(), "Player cannot carry a second key");
		Door door2 = new Door(3,2);
		door2.set_id(2);
		dungeon.addEntity(door2);
		assertEquals(false, player.moveUp(), "Player cannot unlock a door with incorrect key");
		Door door1 = new Door(4,1);
		door1.set_id(1);
		dungeon.addEntity(door1);
		assertEquals(true, player.moveRight(), "Player can go through the door cause he has the key");
	}
	
	@Test
	public void TestEnemyMovement() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Enemy enemy = new Enemy(3,3, dungeon);
		dungeon.addEntity(enemy);
		assertEquals(4, enemy.MahattanDistance(), "Mahattan Distance from player to enemy is 4");
		player.moveUp();
		assertEquals(2, enemy.MahattanDistance(), "Mahattan Distance from player to enemy is 2");
	}
	
	@Test
	public void TestEnemyMovementWithPotion() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Enemy enemy = new Enemy(1, 6, dungeon);
		dungeon.addEntity(enemy);
		Potion p = new Potion(1,2);
		dungeon.addEntity(p);
		player.moveUp();
		player.performCarry();
		assertEquals(1, player.Stash.size(), "Player pick up the potion");
		player.activatePotion(p, player.get_moves());
		assertEquals(true, p.getPotionState() instanceof ActivePotion, "The Potion is activated");
		assertEquals(3, enemy.MahattanDistance(), "Mahattan Distance from player to enemy is 3");
		player.moveUp();
		assertEquals(3, enemy.MahattanDistance(), "Mahattan Distance from player to enemy is still 3 since enemy runs away from player");
		player.moveUp();
		player.moveUp();
		player.moveUp();
		assertEquals(3, enemy.MahattanDistance(), "Mahattan Distance from player to enemy is still 3 since enemy runs away from player");
		//Potion expired
		player.moveUp();
		assertEquals(1, enemy.MahattanDistance(), "Mahattan Distance from player to enemy is 1 since enemy runs towards the player");
	}
	
	@Test
	public void TreasureTest() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Treasure t1 = new Treasure(2,1);
		Treasure t2 = new Treasure(3,1);
		dungeon.addEntity(t1);
		dungeon.addEntity(t2);
		player.moveRight();
		player.performCarry();
		player.moveRight();
		player.performCarry();
		assertEquals(2, ((Treasure) player.Stash.get(0)).get_treasure_level(), "Player has 2 lots of treasure");
	}
	
	@Test
	public void TestSword() {
		Dungeon dungeon = new Dungeon(20, 25);
		Player player = new Player(dungeon, 1,1);
		dungeon.setPlayer(player);
		Sword s = new Sword(2,1);
		Sword s1 = new Sword(3,1);
		dungeon.addEntity(s);
		dungeon.addEntity(s1);
		player.moveRight();
		player.performCarry();
		assertEquals(1, player.Stash.size(), "Player picks up a sword");
		player.moveRight();
		player.performCarry();
		assertEquals(1, player.Stash.size(), "Player can pick up only one sword");
		Enemy enemy = new Enemy(3,1, dungeon);
		dungeon.addEntity(enemy);
		player.killEnemy();
		assertEquals(4, s.get_charge(), "Charge is reduced to 4 since enemy has been hit and enemy dies");
	}
	
	@Test
	public void TestGoalCondition() throws FileNotFoundException {
		
		JFXPanel jfxpanel = new JFXPanel();

       DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("advanced.json");

       Dungeon d = dungeonLoader.load();
       Component goal = d.get_goal();
       System.out.println(goal.get_name());
       System.out.println(goal.get_children().get(0).get_name());
	
	}
}	
	
