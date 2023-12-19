package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable {

    @FXML
    private Label score;

    @FXML
    private Label bestScore;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(String.valueOf(Score.getScore()));
        bestScore.setText(String.valueOf(Score.getBestScore())); // You can uncomment this line if needed
    }
    @FXML
    public void goToHomePage() throws IOException {
        HeroApplication.setRoot("start");
        Hero.resetHero();
        Score.resetScore();
        Cherry.getCherryCountLabel().setText(String.valueOf(Cherry.getCherryCount()));
    }

    @FXML
    public void restartGame() throws IOException {
        Score.resetScore();
        resetGame();
    }

    public void revivePlayer() throws IOException {
        if(Cherry.getCherryCount() < 2){
            System.out.println("Not enough cherries");
            return;
        }
        else{
            Cherry.setCherryCount(Cherry.getCherryCount() - 2);
        }
        resetGame();
    }

    public void resetGame() throws IOException {
        Hero.resetHero();
        HeroApplication.setRoot("play");
        Cherry.getCherryCountLabel().setText(String.valueOf(Cherry.getCherryCount()));
        Score.getScoreLabel().setText(String.valueOf(Score.getScore()));
        Score.writeScore();
        Play.start();
    }
}
