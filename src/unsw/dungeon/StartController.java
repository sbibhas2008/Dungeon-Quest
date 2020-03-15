package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

    private GameScreenLevel1 gameScreen1;
    private GameScreenLevel2 gameScreen2;
    private GameScreenLevel3 gameScreen3;

    @FXML
    private Button StartLevel1;

    @FXML
    private Button StartLevel2;

    @FXML
    private Button StartLevel3;
    

    @FXML
    void handleLevel1Button(ActionEvent event) throws IOException {
    	gameScreen1.start();
    }

    @FXML
    void handleLevel2Button(ActionEvent event) throws IOException {
    	gameScreen2.start();
    }

    @FXML
    void handleLevel3Button(ActionEvent event) throws IOException {
    	gameScreen3.start();
    }
    public void setGameScreen1(GameScreenLevel1 gameScreen) {
        this.gameScreen1 = gameScreen;
    }
    public void setGameScreen2(GameScreenLevel2 gameScreen) {
        this.gameScreen2 = gameScreen;
    }
    public void setGameScreen3(GameScreenLevel3 gameScreen) {
        this.gameScreen3 = gameScreen;
    }
    
    
   

}
