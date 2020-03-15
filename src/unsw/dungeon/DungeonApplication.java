package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;

import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
    
    	StartScreen startScreen = new StartScreen(primaryStage);
    	GameScreenLevel1 gameScreen1 = new GameScreenLevel1(primaryStage);
    	GameScreenLevel2 gameScreen2 = new GameScreenLevel2(primaryStage);
    	GameScreenLevel3 gameScreen3 = new GameScreenLevel3(primaryStage);
    	GameOverScreen gameOverScreen = new GameOverScreen(primaryStage);
    	GameWinScreen win = new GameWinScreen(primaryStage);
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

    public static void main(String[] args) {
        launch(args);
    }

}
