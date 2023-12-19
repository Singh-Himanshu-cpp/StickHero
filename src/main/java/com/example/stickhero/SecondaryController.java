package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SecondaryController {
    @FXML
    private Rectangle band;

    @FXML
    private Rectangle backLeg;

    @FXML
    private Rectangle frontLeg;

    @FXML
    private Rectangle body;

    @FXML
    private Circle eyes;

    @FXML
    private ImageView cherry;

    @FXML
    private Label score;

    @FXML
    private Label cherryNum;

    @FXML
    private ImageView backGround;

    @FXML
    private void initialize() {
        try {
            Properties properties = new Properties();
            properties.load(new BufferedReader(new FileReader("src/main/resources/com/example/stickhero/dimensions.txt")));

            // Get values from properties file
            double prefWidth = Double.parseDouble(properties.getProperty("prefWidth"));
            double prefHeight = Double.parseDouble(properties.getProperty("prefHeight"));

            // Set values to the AnchorPane
            backGround.setFitWidth(prefWidth);
            backGround.setFitHeight(prefHeight);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        Cherry.initializeCherry(cherry,cherryNum);
        cherry.relocate(150,Platform.getYCoordinate()+10);
        Score.createScoreLabel(score);
        Hero.initializeHero(band,backLeg,frontLeg,body,eyes);

    }
}
