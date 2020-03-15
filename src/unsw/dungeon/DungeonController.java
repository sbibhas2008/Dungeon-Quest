package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    
    private GameOverScreen gameOver;
    
    private GameWinScreen win;
    
    private int stashIndex;
    
    private List<Integer> potionIndex;
    
    private int swordIndex;
    
    private int swordHits;

    private List<Integer> keyIndex;
    
    private List<Integer> keyIds;
    
    private List<Integer> doorIds;
    
    private int noEnemies;
    
    String level;

    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, String level) {
    	this.level = level;
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.stashIndex = 0;
        potionIndex = new ArrayList<Integer>(); 
        swordIndex = 0;
        keyIndex = new ArrayList<Integer>(); 
        keyIds = new ArrayList<Integer>(); 
        doorIds = new ArrayList<Integer>(); 
        keyIds.add(0);
        keyIds.add(1);
        keyIds.add(2);
        doorIds.add(0);
        doorIds.add(1);
        doorIds.add(2);
        noEnemies = dungeon.getNumEnemy();
    }

    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight()+3; y++) {
                squares.add(new ImageView(ground), x, y);
               
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        Button restart = new Button("Restart");
        restart.setOnAction(value ->  {
        	Stage stage = (Stage) restart.getScene().getWindow();
        	try {
        		StartScreen startScreen = new StartScreen(stage);
            	GameScreenLevel1 gameScreen1 = new GameScreenLevel1(stage);
            	GameScreenLevel2 gameScreen2 = new GameScreenLevel2(stage);
            	GameScreenLevel3 gameScreen3 = new GameScreenLevel3(stage);
            	GameOverScreen gameOverScreen = new GameOverScreen(stage);
            	GameWinScreen win = new GameWinScreen(stage);
            	startScreen.getController().setGameScreen1(gameScreen1);   
            	startScreen.getController().setGameScreen2(gameScreen2);  
            	startScreen.getController().setGameScreen3(gameScreen3);  
            	gameScreen1.getController().setGameOverScreen(gameOverScreen);
            	gameScreen2.getController().setGameOverScreen(gameOverScreen);
            	gameScreen3.getController().setGameOverScreen(gameOverScreen);
              	gameScreen1.getController().setWinScreen(win);
            	gameScreen2.getController().setWinScreen(win);
            	gameScreen3.getController().setWinScreen(win);
            	gameOverScreen.getController().setStartScreen(startScreen);
            	win.getController().setStartScreen(startScreen);
                if (level.equals("maze.json")) {
                	try {
    					gameScreen1.start();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
                else if (level.equals("boulders.json")) {
                	try {
    					gameScreen2.start();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
                else if (level.equals("advanced.json")) {
                	try {
                		
    					gameScreen3.start();

    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
        	}
        	catch (IOException e){
        		e.printStackTrace();
        	}
        	
         });
        squares.add(restart, dungeon.getWidth()-2, dungeon.getHeight(), 2, 1);
        Button mainMenu = new Button("Menu");
        mainMenu.setOnAction(value ->  {
        	Stage stage = (Stage) restart.getScene().getWindow();
        	try {
        		StartScreen startScreen = new StartScreen(stage);
            	GameScreenLevel1 gameScreen1 = new GameScreenLevel1(stage);
            	GameScreenLevel2 gameScreen2 = new GameScreenLevel2(stage);
            	GameScreenLevel3 gameScreen3 = new GameScreenLevel3(stage);
            	GameOverScreen gameOverScreen = new GameOverScreen(stage);
            	GameWinScreen win = new GameWinScreen(stage);
            	startScreen.getController().setGameScreen1(gameScreen1);   
            	startScreen.getController().setGameScreen2(gameScreen2);  
            	startScreen.getController().setGameScreen3(gameScreen3);  
            	gameScreen1.getController().setGameOverScreen(gameOverScreen);
            	gameScreen2.getController().setGameOverScreen(gameOverScreen);
            	gameScreen3.getController().setGameOverScreen(gameOverScreen);
              	gameScreen1.getController().setWinScreen(win);
            	gameScreen2.getController().setWinScreen(win);
            	gameScreen3.getController().setWinScreen(win);
            	gameOverScreen.getController().setStartScreen(startScreen);
            	win.getController().setStartScreen(startScreen);
            	startScreen.start();
        	}    
        	catch (IOException e){
        		e.printStackTrace();
        	}
        	
         });
        squares.add(mainMenu, dungeon.getWidth()-2, dungeon.getHeight()+1, 3, 1);
        Label status = new Label();
        status.setText("Mission: " + dungeon.toString());
        status.setFont(new Font(20));
        status.setTextFill(Color.web("white"));
        squares.add(status, 0, dungeon.getHeight()+2, dungeon.getWidth(), 1);
        Label stash = new Label("Player Stash ");
        stash.setFont(new Font(20));
        stash.setTextFill(Color.web("white"));
        squares.add(stash, 0, dungeon.getHeight(), 10, 1);
        
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
    	 boolean check_player_alive = dungeon.check_player();
    	 if (check_player_alive)  {
    	    notifySword();

            switch (event.getCode()) {
            case DOWN:
            	if (player.moveUp()) {
            		 Entity e = dungeon.trigger_entity();
                     if (e instanceof Door) {
                    	 
                     	List<Node> doorNode = getNodeByRowColumnIndex(e.getY(), e.getX(), squares);
                     	((ImageView) doorNode.get(1)).setImage(new Image("/open_door.png"));
                     	boolean found = false;
                     	int i = 0;
                     	for (int id : keyIds) {
                     		if (((Door) e).get_id() == id) {
                     			notifyKeys();
                     			found = true;
                     			break;
                     		}
                     		i++;
                     	}
                     	if (found) {
                     		keyIds.remove(i);
                     	}
                     	

                     }
                     else if (e instanceof Bomb) {
                     	List<Node> doorNode = getNodeByRowColumnIndex(e.getY(), e.getX(), squares);
                     	if (((Bomb) e).get_fuse() == 3) {
                     		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_2.png")); 
                     		}
                     		else {
                     			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_2.png")); 
                     		}
                     	}
                     	else if (((Bomb) e).get_fuse() == 2) {
                     		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_3.png")); 
                     		}
                     		else {
                     			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_3.png")); 
                     		}
                     	}
                     	else if (((Bomb) e).get_fuse() == 1) {
                     		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_4.png")); 
                     		}
                     		else {
                     			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_4.png")); 
                     		}
                     	}
                     }
                     int get_response_enemy = player.notifyEnemies();
                     HashMap<List<Integer>, String> get_response_bombs = player.kill_on_explosion();
                     if (get_response_enemy == 1) {
                     	List<Node> doorNode = getNodeByRowColumnIndex(player.getY(), player.getX(), squares);
                     	if (doorNode.size() == 4) {
                    		squares.getChildren().remove(doorNode.get(3));
                    	}
                    	else {
                    		squares.getChildren().remove(doorNode.get(2)); 
                    	}
                     }
                     // terminate the game
                     else if (get_response_enemy == 2){
                    	 this.gameOver.start();
                     }
                     if (get_response_bombs != null) {
                    	 for (Entry<List<Integer>, String> entry : get_response_bombs.entrySet()) {
                           	 // enemy vanishes
                           	 if (entry.getValue().equals("enemy")) {
                           		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                 	squares.getChildren().remove(doorNode.get(1)); 
                           	 }
                           	 // terminate the game
                           	 if (entry.getValue().equals("player")) {
                           		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                  	squares.getChildren().remove(doorNode.get(1)); 
                                  	this.gameOver.start();
                           	 }
                           	 // boulder vanishes
                           	 if (entry.getValue().equals("boulder")) {
                           		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                  	squares.getChildren().remove(doorNode.get(1)); 
                           	 }
                            }
                     }
                     
            	}
               
                break;
            case UP:
            	if (player.moveDown()) {
            		 Entity e1 = dungeon.trigger_entity();
                     if (e1 instanceof Door) {
                     	List<Node> doorNode = getNodeByRowColumnIndex(e1.getY(), e1.getX(), squares);
                     	((ImageView) doorNode.get(1)).setImage(new Image("/open_door.png"));
                     	boolean found = false;
                     	int i = 0;
                     	for (int id : keyIds) {
                     		if (((Door) e1).get_id() == id) {
                     			notifyKeys();
                     			found = true;
                     			break;
                     		}
                     	}
                     	if (found) {
                     		keyIds.remove(i);
                     	}
                     	
//                     	squares.add(new ImageView(new Image("/open_door.png")), e1.getX(), e1.getY());
                     }
                     else if (e1 instanceof Bomb) {
                     	List<Node> doorNode = getNodeByRowColumnIndex(e1.getY(), e1.getX(), squares);
                     	if (((Bomb) e1).get_fuse() == 3) {
                     		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_2.png")); 
                     		}
                     		else {
                     			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_2.png")); 
                     		}
                     	}
                     	else if (((Bomb) e1).get_fuse() == 2) {
                     		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_3.png")); 
                     		}
                     		else {
                     			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_3.png")); 
                     		}
                     	}
                     	else if (((Bomb) e1).get_fuse() == 1) {
                     		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_4.png")); 
                     		}
                     		else {
                     			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_4.png")); 
                     		}
                     	}
                     }
                     int get_response_enemy = player.notifyEnemies();
                     HashMap<List<Integer>, String> get_response_bombs = player.kill_on_explosion();
                     if (get_response_enemy == 1) {
                     	List<Node> doorNode = getNodeByRowColumnIndex(player.getY(), player.getX(), squares);
                     	if (doorNode.size() == 4) {
                    		squares.getChildren().remove(doorNode.get(3));
                    	}
                    	else {
                    		squares.getChildren().remove(doorNode.get(2)); 
                    	}
                     }
                     // terminate the game
                     else if (get_response_enemy == 2) {
                    	 this.gameOver.start();
                     }
                     if (get_response_bombs != null) {
                    	 for (Entry<List<Integer>, String> entry : get_response_bombs.entrySet()) {
                           	 // enemy vanishes
                           	 if (entry.getValue().equals("enemy")) {
                           		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                 	squares.getChildren().remove(doorNode.get(1)); 
                           	 }
                           	 // terminate the game
                           	 if (entry.getValue().equals("player")) {
                           		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                  	squares.getChildren().remove(doorNode.get(1)); 
                                  	this.gameOver.start();
                           	 }
                           	 // boulder vanishes
                           	 if (entry.getValue().equals("boulder")) {
                           		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                  	squares.getChildren().remove(doorNode.get(1)); 
                           	 }
                            }
                     }
            	}
                break;
            case LEFT:
            	if (player.moveLeft()) {
            		Entity e2 = dungeon.trigger_entity();
                    if (e2 instanceof Door) {
                    	List<Node> doorNode = getNodeByRowColumnIndex(e2.getY(), e2.getX(), squares);
                    	((ImageView) doorNode.get(1)).setImage(new Image("/open_door.png"));
                    	boolean found = false;
                    	int i = 0;
                     	for (int id : keyIds) {
                     		if (((Door) e2).get_id() == id) {
                     			notifyKeys();
                     			found = true;
                     			break;
                     		}
                     	}
                     	if (found) {
                     		keyIds.remove(i);
                     	}
                     	
                    }
                    else if (e2 instanceof Bomb) {
                    	List<Node> doorNode = getNodeByRowColumnIndex(e2.getY(), e2.getX(), squares);
                    	if (((Bomb) e2).get_fuse() == 3) {
                    		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_2.png")); 
                     		}
                    		else {
                    			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_2.png")); 
                    		}
                    	}
                    	else if (((Bomb) e2).get_fuse() == 2) {
                    		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_3.png")); 
                     		}
                    		else {
                    			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_3.png")); 
                    		}
                    	}
                    	else if (((Bomb) e2).get_fuse() == 1) {
                    		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_4.png")); 
                     		}
                    		else {
                    			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_4.png")); 
                    		}
                    	}
                    }
                    int get_response_enemy = player.notifyEnemies();
                    HashMap<List<Integer>, String> get_response_bombs = player.kill_on_explosion();
                    if (get_response_enemy == 1) {
                    	List<Node> doorNode = getNodeByRowColumnIndex(player.getY(), player.getX(), squares);
                    	if (doorNode.size() == 4) {
                    		squares.getChildren().remove(doorNode.get(3));
                    	}
                    	else {
                    		squares.getChildren().remove(doorNode.get(2)); 
                    	}
                    }
                    // terminate the game
                    else if (get_response_enemy == 2) {
                    	this.gameOver.start();
                    }
                    if (get_response_bombs != null) {
                    	for (Entry<List<Integer>, String> entry : get_response_bombs.entrySet()) {
                          	 // enemy vanishes
                          	 if (entry.getValue().equals("enemy")) {
                          		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                	squares.getChildren().remove(doorNode.get(1)); 
                          	 }
                          	 // terminate the game
                          	 if (entry.getValue().equals("player")) {
                          		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                 	squares.getChildren().remove(doorNode.get(1)); 
                                 	this.gameOver.start();
                          	 }
                          	 // boulder vanishes
                          	 if (entry.getValue().equals("boulder")) {
                          		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                                 	squares.getChildren().remove(doorNode.get(1)); 
                          	 }
                           }
                    }
            	}
                break;
            case RIGHT:
            	if (player.moveRight()) {
            		Entity e3 = dungeon.trigger_entity();
                    if (e3 instanceof Door) {
                    	List<Node> doorNode = getNodeByRowColumnIndex(e3.getY(), e3.getX(), squares);
                    	((ImageView) doorNode.get(1)).setImage(new Image("/open_door.png"));
                    	boolean found = false;
                    	int i = 0;
                     	for (int id : keyIds) {

                     		if (((Door) e3).get_id() == id) {
                     			notifyKeys();
                     			found = true;
                     			break;
                     		}
                     	}
                     	if (found) {
                     		keyIds.remove(i);
                     	}
                     	
                    }
                    // this block shows that the bomb has been dropped
                    else if (e3 instanceof Bomb) {
                    	List<Node> doorNode = getNodeByRowColumnIndex(e3.getY(), e3.getX(), squares);
                    	if (((Bomb) e3).get_fuse() == 3) {
                    		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_2.png")); 
                     		}
                    		else {
                    			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_2.png")); 
                    		}
                    	}
                    	else if (((Bomb) e3).get_fuse() == 2) {
                    		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_3.png")); 
                     		}
                    		else {
                    			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_3.png")); 
                    		}
                    	}
                    	else if (((Bomb) e3).get_fuse() == 1) {
                    		if (doorNode.size() == 4) {
                     			((ImageView) doorNode.get(3)).setImage(new Image("/bomb_lit_4.png")); 
                     		}
                    		else {
                    			((ImageView) doorNode.get(2)).setImage(new Image("/bomb_lit_4.png")); 
                    		}
                    	}
                    }
                    int get_response_enemy = player.notifyEnemies();
                    HashMap<List<Integer>, String> get_response_bombs = player.kill_on_explosion();
                    if (get_response_enemy == 1) {
                    	List<Node> doorNode = getNodeByRowColumnIndex(player.getY(), player.getX(), squares);
                    	if (doorNode.size() == 4) {
                    		squares.getChildren().remove(doorNode.get(3));
                    	}
                    	else {
                    		squares.getChildren().remove(doorNode.get(2)); 
                    	}
                    }
                    // terminate the game
                    else if (get_response_enemy == 2) {
                    	this.gameOver.start();
                    }
                    if (get_response_bombs != null) {
                    	int i = 0; 
                   	 for (Entry<List<Integer>, String> entry : get_response_bombs.entrySet()) {
                       	 // enemy vanishes
                       	 if (entry.getValue().equals("enemy")) {
                       		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                             	squares.getChildren().remove(doorNode.get(1)); 
                       	 }
                       	 // terminate the game
                       	 if (entry.getValue().equals("player")) {
                       		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                              	squares.getChildren().remove(doorNode.get(1)); 
                              	this.gameOver.start();
                       	 }
                       	 // boulder vanishes
                       	 if (entry.getValue().equals("boulder")) {
                       		List<Node> doorNode = getNodeByRowColumnIndex(entry.getKey().get(1), entry.getKey().get(0), squares);
                              	squares.getChildren().remove(doorNode.get(1)); 
                       	 }
                        }
                    }
                   
            	}
                

                break;
            case ENTER:
            	Entity pU = player.performCarry();
            	// need to be changed in a way such that the image goes to the stash
            	if (pU != null) {
            		if (pU instanceof Potion) {
            			this.potionIndex.add(stashIndex);
            		}
            		else if (pU instanceof Key) {
            			this.keyIndex.add(stashIndex);
            			
            		}
            		else if (pU instanceof Sword) {
            			this.swordHits = 0;
            			swordIndex = stashIndex;
            		}
            		pU.x().set(stashIndex);
	            	pU.y().set(dungeon.getHeight()+1);
	            	this.stashIndex++;
            		

            	}
            	
            	break;
            case P:
            	for (Entity potion:player.Stash) {
            		if (potion instanceof Potion) {
            			player.activatePotion(((Potion) potion), player.get_moves());
            			List<Node> doorNode = getNodeByRowColumnIndex(dungeon.getHeight()+1, potionIndex.get(potionIndex.size()-1), squares);
    	            	squares.getChildren().remove(doorNode.get(1)); 
            			potionIndex.remove(potionIndex.size()-1);
            		}
            	}
            case D:
            	boolean rem_bomb = false;
            	Entity b = null;
            	for (Entity bomb: player.Stash) {
            		if (bomb instanceof Bomb) {
            			player.dropBomb((Bomb) bomb);
            			List<Node> doorNode = getNodeByRowColumnIndex(player.getY(), player.getX(), squares);
                    	squares.add(new ImageView(new Image("/bomb_lit_1.png")), player.getX(), player.getY()); 
            			rem_bomb = true;
            			b = bomb;
            			break;
            		}
            	}
    			if (rem_bomb) {
    				player.Stash.remove(b);
    				dungeon.addEntity(b);
    				b.x().set(player.getX());
    				b.y().set(player.getY());
    			}
            	

            default:
                break;
            }

            
        }

        if (! check_player_alive) {
        	this.gameOver.start();
        }
       
        if (check_player_alive) {
        	boolean check_goal = dungeon.check_curr_state(dungeon.get_goal());
        	if (check_goal) {
            	this.win.start();
            }
        }
        
        	
    }
    
    public Player get_player() {
    	return this.player;
    }
    
    
    public List<Node> getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
    	List<Node> result = new ArrayList<Node>();
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result.add(node);
            }
        }

        return result;
    }
    
    public void setGameOverScreen(GameOverScreen gameOver) {
    	this.gameOver = gameOver;
    }
    
    public void setWinScreen(GameWinScreen win) {
    	this.win = win;
    }
    
    public void notifyKeys() {
		if (keyIndex.size() != 0) {
	    	List<Node> doorNode = getNodeByRowColumnIndex(dungeon.getHeight()+1, keyIndex.get(keyIndex.size()-1), squares);
	    	squares.getChildren().remove(doorNode.get(1)); 
	    	keyIndex.remove(keyIndex.size()-1);
    	}
    				
   	}
    
    public void notifySword() {
    	if (noEnemies > dungeon.getNumEnemy()) {
    		this.swordHits++;
    		this.noEnemies--;
    	}
    	
    	if (swordHits == 5) {
    		List<Node> doorNode = getNodeByRowColumnIndex(dungeon.getHeight()+1, swordIndex, squares);
        	squares.getChildren().remove(doorNode.get(1)); 
        	Entity sword = null;
        	for (Entity e : player.Stash) {
        		if (e instanceof Sword) 
        			sword = e;
        	}
        	player.Stash.remove(sword);
        	swordHits = 0;
    	}
    }


    
    
}

