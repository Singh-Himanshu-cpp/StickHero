package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Cherry {
    @FXML
    private static ImageView cherryImageView;
    @FXML
    private static int cherryCount = 0;
    private static Label cherryCountLabel;

    // getter and setter methods
    public static Label getCherryCountLabel() {
        return cherryCountLabel;
    }

    public static ImageView getCherryImageView() {
        return Cherry.cherryImageView;
    }

    public static int getCherryCount() {
        return cherryCount;
    }

    public static void setCherryCount(int i) {
        cherryCount = i;
    }

    public static void setCherry(ImageView cherryImage) {
        Cherry.cherryImageView = cherryImage;
    }

    private static void setCherryCountLabel(Label cherryCountLabel) {
        Cherry.cherryCountLabel = cherryCountLabel;
    }

    // other methods
    public static void makeCherryVisible(){
        cherryImageView.setVisible(true);
    }

    public static void makeCherryInvisible() {
        cherryImageView.setVisible(false);
    }

    public static void initializeCherry(ImageView cherry, Label cherryNum) {
        setCherry(cherry);
        setCherryCountLabel(cherryNum);
    }

    public static void CollectCherry(){
        cherryImageView.setVisible(false);
        cherryCount++;
    }

    public static void randomizeCherryPosition(){
        if(Play.getPlatformEnd().getStartX() - Play.getPlatformStart().getEndX() < 35) return;
        Random random = new Random();
        int position = random.nextInt((int)Play.getPlatformStart().getEndX(),(int)Play.getPlatformEnd().getStartX()-30);
        cherryImageView.setLayoutX(position);
        makeCherryVisible();
    }

}
