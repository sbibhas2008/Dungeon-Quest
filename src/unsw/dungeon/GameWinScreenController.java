package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameWinScreenController {
	private StartScreen mainmenu;

    @FXML
    private Button ExitButton;

    @FXML
    private Button MainMenuButton;
    
    Stage stage;

  
    @FXML
    void handleExitButton(ActionEvent event) {
    	Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleMainMenuButton(ActionEvent event) throws IOException {
    	Stage stage = (Stage) MainMenuButton.getScene().getWindow();
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
    
    public void setStartScreen(StartScreen startscreen) {
        this.mainmenu = startscreen;
    }

}
