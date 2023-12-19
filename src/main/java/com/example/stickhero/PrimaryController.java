package com.example.stickhero;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static java.lang.Math.abs;

public class PrimaryController {
    @FXML
    public Rectangle platform;
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
    private ImageView backGround;

    private int clickCount = 0;

    public void initialize(){
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
        band.relocate(band.getLayoutX(),Platform.getYCoordinate()-28);
        backLeg.relocate(backLeg.getLayoutX(),Platform.getYCoordinate()-10);
        frontLeg.relocate(frontLeg.getLayoutX(),Platform.getYCoordinate()-10);
        body.relocate(body.getLayoutX(),Platform.getYCoordinate()-30);
        eyes.relocate(band.getLayoutX() +12,band.getLayoutY() + 1);
        platform.relocate(platform.getLayoutX(),Platform.getYCoordinate());
    }

    @FXML
    private void openPlayPage(){
        if(clickCount>0) return;
        // making backward animation on click of start button
        clickCount++;
        double distance = Platform.getStartPlatformEnd();
        TranslateTransition bandTransition = makeTranslateTransition(band, distance-200);
        TranslateTransition backLegTransition = makeTranslateTransition(backLeg, distance-200);
        TranslateTransition frontLegTransition = makeTranslateTransition(frontLeg, distance-200);
        TranslateTransition bodyTransition = makeTranslateTransition(body, distance-200);
        TranslateTransition eyesTransition = makeTranslateTransition(eyes, distance-200);
        TranslateTransition platformTransition = makeTranslateTransition(platform, distance-230,true);
        bandTransition.play();
        backLegTransition.play();
        frontLegTransition.play();
        bodyTransition.play();
        eyesTransition.play();
        platformTransition.play();
        bandTransition.setOnFinished(e -> {
            try {
                platform.setVisible(false);
                HeroApplication.setRoot("play");
                Play.start();
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }
        });
    }

    public static TranslateTransition makeTranslateTransition(Node node, double distance){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.01*abs(distance)), node);
        transition.setByX(distance); // Adjust the distance as needed
        return transition;
    }

    public static TranslateTransition makeTranslateTransition(Node node, double distance,boolean custom){
        double duration = (abs(30+distance))*0.01;
        TranslateTransition transition = new TranslateTransition(Duration.seconds(duration), node);
        transition.setByX(distance); // Adjust the distance as needed
        return transition;
    }


}
