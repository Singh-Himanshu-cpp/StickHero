package com.example.stickhero;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Stick {
    private static final double stickWidth = 10;
    private static final double stickGrowSpeed=2;
    private static Rectangle stick;
    private static double stickHeight=stickWidth/2;
    private static double xCoordinate=Platform.getStartPlatformEnd()-stickWidth;
    private static double yCoordinate= Platform.getYCoordinate();
    private static boolean falling = false;

    public static Rectangle getStick() {
        return stick;
    }
    public static double getStickHeight() {
        return stickHeight;
    }
    public static boolean isFalling() {
        return falling;
    }
    public static void growStick(){
        stickHeight += stickGrowSpeed;
        yCoordinate-=stickGrowSpeed;
        stick.relocate(xCoordinate,yCoordinate);
        stick.setHeight(stickHeight);
    }

    public static void createStick() {
        stickHeight=stickWidth/2;
        yCoordinate=Platform.getYCoordinate();
        stick = new Rectangle(stickWidth,stickHeight);
        stick.relocate(xCoordinate,yCoordinate);
        Pane root = (Pane) HeroApplication.getScene().getRoot();
        root.getChildren().add(stick);
    }

    public static void grow() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (Play.isMousePressed()) {
                    growStick();
                } else {
                    stop();  // Stop the AnimationTimer when the mouse is released
                }
            }
        }.start();
    }

    public static void fall() {
        falling = true;
        // changing the pivot point to end of the stick
        stick.getTransforms().add(new Translate(0,-stickHeight/2));
        stick.setTranslateX(0); stick.setTranslateY(stickHeight/2);
        // rotating the stick
        RotateTransition rotate = new RotateTransition(Duration.seconds(1), stick);
        rotate.setByAngle(90);
        rotate.setNode(rotate.getNode());
        // action performed after the stick falls
        rotate.setOnFinished(event -> {
            falling = false;
            if(Play.stickReached()) {
                Hero.initializeTransitions(Play.getPlatformDistance());
            }
            else {
                Hero.initializeTransitions(Stick.getStickHeight() + 10);
            }
            Hero.playTransitions();
            Hero.startCollisionDetectionTimeline();
        });

        rotate.play();

    }
}
