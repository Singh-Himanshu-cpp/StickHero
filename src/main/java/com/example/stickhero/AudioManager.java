package com.example.stickhero;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioManager {
    private static MediaPlayer mediaPlayer;

    public static void startMusic() {
        if (mediaPlayer == null) {
            String relativePath = "src/main/resources/com/example/stickhero/music1.mp3";
            Media media = new Media(new File(relativePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(Integer.MAX_VALUE);
            mediaPlayer.play();
        }
    }
}
