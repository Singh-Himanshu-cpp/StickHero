package com.example.stickhero;

import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class Play{
    private static boolean MousePressed = false;
    private static Platform platformStart;
    private static Platform platformEnd;
    private static Thread musicThread;


    public static boolean isMousePressed() {
        return MousePressed;
    }

    public static Platform getPlatformStart() {
        return platformStart;
    }

    public static void setPlatformStart(Platform platformStart) {
        Play.platformStart = platformStart;
    }

    public static Platform getPlatformEnd() {
        return platformEnd;
    }

    public static void setPlatformEnd(Platform platformEnd) {
        Play.platformEnd = platformEnd;
    }

    public static void start(){
        if(musicThread==null) {
            musicThread = new Thread(
                    AudioManager::startMusic
            );
            musicThread.start();
        }

        Stick.createStick();
        platformStart = new Platform(Platform.getStartPlatformEnd()-100,100);
        platformEnd = new Platform(177,100);
        mousePressed();
        Score.readScore();
        Cherry.getCherryCountLabel().setText(String.valueOf(Cherry.getCherryCount()));
    }


    private static void mousePressed() {
        // lambda expression
        HeroApplication.getScene().setOnMousePressed((MouseEvent event) -> {
            if(!Hero.isMovingForward() && !Stick.isFalling() && !Hero.isMovingBack()) MousePressed=true;
            if(!Stick.isFalling() && !Hero.isMovingBack()) {
                if(!Hero.isMovingForward()) Stick.grow();
                else if(Hero.getXCoordinate() <platformEnd.getStartX() && Hero.isMovingForward()) Hero.flipHero();
            }
        });
        HeroApplication.getScene().setOnMouseReleased((MouseEvent event) -> {
            if(!Hero.isMovingForward() && !Stick.isFalling() && MousePressed) {
                Stick.fall();
            }
            MousePressed = false;
        });
    }

    public static boolean stickReached(){
        Bounds stickBounds = Stick.getStick().getBoundsInParent();
        Bounds platformBounds = platformEnd.getPlatform().getBoundsInParent();
        return stickBounds.getMaxX()>=platformBounds.getMinX() && stickBounds.getMaxX()<=platformBounds.getMaxX();
    }

    public static double getPlatformDistance(){
        return platformEnd.getEndX()-platformStart.getEndX();
    }

    public static void gameOver(boolean collision){
        if(collision) Hero.stopTransitions();
        Hero.stopCollisionDetectionTimeline();
        Hero.initializeFallingTransition(Platform.getHeight()+50);
        Hero.playFallingTransition();
    }

    public static void changeScene(){
        try {
            HeroApplication.setRoot("gameOver");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
