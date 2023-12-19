package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Hero {
    @FXML
    private static Rectangle band;
    @FXML
    private static Rectangle backLeg;
    @FXML
    private static Rectangle frontLeg;
    @FXML
    private static Rectangle body;
    @FXML
    private static Circle eyes;
    private static TranslateTransition bandTransition;
    private static TranslateTransition backLegTransition;
    private static TranslateTransition frontLegTransition;
    private static TranslateTransition bodyTransition;
    private static TranslateTransition eyesTransition;
    private static  TranslateTransition bandFallTransition;
    private static  TranslateTransition backLegFallTransition;
    private static  TranslateTransition frontLegFallTransition;
    private static  TranslateTransition bodyFallTransition;
    private static  TranslateTransition eyesFallTransition;
    private static Timeline collisionDetectionTimeline;
    private static boolean isFlipped = false;
    private static boolean isMovingForward = false;
    private static boolean MovingBack = false;

    public static boolean isMovingBack() {
        return MovingBack;
    }

    public static double getXCoordinate(){
        Bounds bandBound = band.getBoundsInParent();
        return bandBound.getMaxX();
    }
    public static void resetHero(){
        isFlipped = false;
        isMovingForward = false;
        MovingBack = false;
    }
    public static void initializeHero(Rectangle band,Rectangle backLeg,Rectangle frontLeg,Rectangle body,Circle eyes) {
        Hero.band=band;
        Hero.backLeg = backLeg;
        Hero.frontLeg =frontLeg;
        Hero.body =body;
        Hero.eyes =eyes;
        band.relocate(band.getLayoutX()-(100-Platform.getStartPlatformEnd()),Platform.getYCoordinate()-28);
        backLeg.relocate(backLeg.getLayoutX()-(100-Platform.getStartPlatformEnd()),Platform.getYCoordinate()-10);
        frontLeg.relocate(frontLeg.getLayoutX()-(100-Platform.getStartPlatformEnd()),Platform.getYCoordinate()-10);
        body.relocate(body.getLayoutX()-(100-Platform.getStartPlatformEnd()),Platform.getYCoordinate()-30);
        eyes.relocate(band.getLayoutX() +12,band.getLayoutY() + 1);
    }

    public static boolean isMovingForward(){
        return isMovingForward;
    }

    public static void flipHero() {
        // Toggle the flipped state
        if(!isFlipped){
            flipHeroBack();
        }
        else{
            flipHeroFront();
        }
        isFlipped = !isFlipped;
    }

    private static void flipHeroFront() {
        band.relocate(band.getLayoutX(), band.getLayoutY()  - 61);
        backLeg.relocate(backLeg.getLayoutX(), backLeg.getLayoutY()  - 15);
        frontLeg.relocate(frontLeg.getLayoutX(), frontLeg.getLayoutY()  - 15);
        body.relocate(body.getLayoutX(), body.getLayoutY()  - 45);
        eyes.relocate(band.getLayoutX() +12, band.getLayoutY()+1);
    }

    private static void flipHeroBack() {
        band.relocate(band.getLayoutX(), band.getLayoutY()  + 61);
        backLeg.relocate(backLeg.getLayoutX(), backLeg.getLayoutY()  + 15);
        frontLeg.relocate(frontLeg.getLayoutX(), frontLeg.getLayoutY()  + 15);
        body.relocate(body.getLayoutX(), body.getLayoutY()  + 45);
        eyes.relocate(band.getLayoutX() +12, band.getLayoutY()-4);
    }


    public static void playTransitions(){
        bandTransition.play();
        bodyTransition.play();
        eyesTransition.play();
        backLegTransition.play();
        frontLegTransition.play();
    }

    // make forward transition if distance is positive and backward transition if distance is negative
    // forward transition on end calls backward transition

    public static void initializeTransitions(double distance){
        if(distance>0) isMovingForward = true;
        bandTransition = makeTranslateTransition(band, distance);
        backLegTransition = makeTranslateTransition(backLeg, distance);
        frontLegTransition = makeTranslateTransition(frontLeg, distance);
        bodyTransition = makeTranslateTransition(body, distance);
        bodyTransition.setOnFinished(event -> {
            if(distance >0) handleForwardTransitionEnd(distance);
            else handleBackwardTransitionEnd(-distance);
        });
        eyesTransition = makeTranslateTransition(eyes, distance);
    }
    // updates score and play cherry,stick,hero,platform transitions

    public static void handleForwardTransitionEnd(double distance){
        isMovingForward = false;
        MovingBack = true;
        if(Play.stickReached()) {
            Cherry.makeCherryInvisible();
            Score.updateScoreLabel();
            initializeTransitions(-distance);
            makeTranslateTransition(Play.getPlatformStart().getPlatform(),-distance).play();
            makeTranslateTransition(Play.getPlatformEnd().getPlatform(),-distance).play();
            makeTranslateTransition(Stick.getStick(),-distance).play();
            playTransitions();
        } else {
            Play.gameOver(false);
        }
    }
    // stop collision timeline, make new platform and cherry

    public static void handleBackwardTransitionEnd(double distance){
        isMovingForward = false;
        MovingBack = false;
        stopCollisionDetectionTimeline();
        Random random = new Random();
        int width = random.nextInt(Platform.getMinWidth(),Platform.getMaxWidth());
        Stick.createStick();
        Play.getPlatformEnd().setXCoordinate(Play.getPlatformEnd().getXCoordinate()-distance); // needs to be changed in variable breadth
        Play.setPlatformStart(Play.getPlatformEnd());
        Play.setPlatformEnd(new Platform(random.nextInt(max(2*(int)Platform.getStartPlatformEnd()-width+5,(int)Platform.getStartPlatformEnd()+5),HeroApplication.getSceneWidth()-width),width));
        Cherry.randomizeCherryPosition();
    }
    public static TranslateTransition makeTranslateTransition(Node node, double distance){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.01*abs(distance)), node);
        transition.setByX(distance); // Adjust the distance as needed
        return transition;
    }

    public static void initializeFallingTransition(double distance) {
        bandFallTransition = makeFallingTransition(band, distance);
        bandFallTransition.setOnFinished(event -> Play.changeScene());
        backLegFallTransition = makeFallingTransition(backLeg, distance);
        frontLegFallTransition = makeFallingTransition(frontLeg, distance);
        bodyFallTransition = makeFallingTransition(body, distance);
        eyesFallTransition = makeFallingTransition(eyes, distance);
    }

    public static TranslateTransition makeFallingTransition(Node node, double distance){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.003*abs(distance)), node);
        transition.setByY(distance); // Adjust the distance as needed
        return transition;
    }

    public static void playFallingTransition() {
        bandFallTransition.play();
        bodyFallTransition.play();
        eyesFallTransition.play();
        backLegFallTransition.play();
        frontLegFallTransition.play();
    }

    public static void startCollisionDetectionTimeline() {
        if(collisionDetectionTimeline==null) makeCollisionDetectionTimeline();
        else collisionDetectionTimeline.play();
    }
    private static void makeCollisionDetectionTimeline() {
        // Check for collision with cherry
        collisionDetectionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    if (hasCollidedWithCherry(Cherry.getCherryImageView()) && Cherry.getCherryImageView().isVisible()){
                        Cherry.CollectCherry();
                    }

                    if(hasCollidedWithPlatform(Play.getPlatformEnd())){
                        Play.gameOver(true);
                    }
                })
        );
        collisionDetectionTimeline.setCycleCount(Timeline.INDEFINITE);
        collisionDetectionTimeline.play();
    }

    public static void stopCollisionDetectionTimeline() {
        if (collisionDetectionTimeline != null) {
            collisionDetectionTimeline.stop();
        }
    }

    public static boolean hasCollidedWithCherry(ImageView cherry) {
        Bounds heroBounds = band.localToScene(band.getBoundsInLocal());
        Bounds cherryBounds = cherry.localToScene(cherry.getBoundsInLocal());
        return heroBounds.intersects(cherryBounds);
    }

    public static boolean hasCollidedWithPlatform(Platform platform) {
        Bounds heroBounds = band.localToScene(band.getBoundsInLocal());
        Bounds platformBounds = platform.getPlatform().localToScene(platform.getPlatform().getBoundsInLocal());
        return heroBounds.intersects(platformBounds);
    }

    public static void stopTransitions() {
        bandTransition.stop();
        bodyTransition.stop();
        eyesTransition.stop();
        backLegTransition.stop();
        frontLegTransition.stop();
    }
}

