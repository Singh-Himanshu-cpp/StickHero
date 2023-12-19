package com.example.stickhero;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Platform {
    private static final int minWidth = 20;
    private static final int maxWidth = 100;
    private final static double startPlatformEnd = 100;
    private final static double height = 190;
    private double xCoordinate;
    private final double width;
    private final static double yCoordinate = HeroApplication.getSceneHeight()-height;
    private Rectangle platform;
    public static double getStartPlatformEnd() {
        return startPlatformEnd;
    }

    public static int getMinWidth() {
        return minWidth;
    }

    public static int getMaxWidth() {
        return maxWidth;
    }

    public static double getYCoordinate(){return yCoordinate;}
    public static double getHeight() {
        return height;
    }

    public Rectangle getPlatform() {
        return platform;
    }
    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }
    public double getXCoordinate() {
        return xCoordinate;
    }

    public Platform(double xCoordinate, double width) {
        this.xCoordinate = xCoordinate;
        this.width = width;
        createPlatform();
    }

    public double getStartX() {
        return xCoordinate;
    }

    public double getEndX(){
        return xCoordinate + width;
    }

    public void createPlatform(){
        platform = new Rectangle(width,height);
        platform.relocate(xCoordinate,yCoordinate);
        Pane root = (Pane) HeroApplication.getScene().getRoot();
        root.getChildren().add(platform);
    }
}
