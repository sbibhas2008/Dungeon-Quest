package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameScreenLevel1 {
	private Stage stage;
    private String title;
    private DungeonController controller;
    private Scene scene;
    DungeonControllerLoader dungeonLoader;

    public GameScreenLevel1(Stage stage) throws IOException {
        this.stage = stage;
        title = "Dungeon";
        dungeonLoader = new DungeonControllerLoader("maze.json");
        controller = dungeonLoader.loadController();	
    }

   	public void start() throws IOException {
		// TODO Auto-generated method stub
   		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
		stage.setTitle(title);
	    stage.setScene(scene);
	    stage.show();
	}
   	
   	
   	
    public DungeonController getController() {
        return controller;
    }
}
