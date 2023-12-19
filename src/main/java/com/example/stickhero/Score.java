package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.util.Scanner;

public class Score {
    private static int score = 0;
    private static int bestScore = 0;
    @FXML
    private static Label scoreLabel;

    public static int getScore() {
        return score;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static Label getScoreLabel() {
        return scoreLabel;
    }
    public static void resetScore(){
        score = 0;
    }

    public static void createScoreLabel(Label scoreLabel){
        Score.scoreLabel = scoreLabel;
    }
    // updates the score label, cherry label and best score and write them in a file
    public static void updateScoreLabel(){
        score = getScore() + 1;
        if(score > bestScore){
            bestScore = score;
        }
        scoreLabel.setText(String.valueOf(score));
        Cherry.getCherryCountLabel().setText(Integer.toString(Cherry.getCherryCount()));
        writeScore();
    }

    // writes best score in first line and cherry count in second line
    public static void writeScore(){
        try (PrintWriter writer = new PrintWriter("src/main/resources/com/example/stickhero/score.txt")) {
            writer.println(bestScore);
            writer.println(Cherry.getCherryCount());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // reads the score from the file
    public static void readScore(){
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("src/main/resources/com/example/stickhero/score.txt")))) {
            bestScore = scanner.nextInt();
            Cherry.setCherryCount(scanner.nextInt());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
