package com.example.stickhero;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryControllerTest {
    @Test
    public void testOpenPlayPage() throws IOException {
        HeroApplication heroApplication = new HeroApplication();
        Platform.startup(() -> {
            try {
                heroApplication.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Delay to ensure the scene is loaded before assertions
        Platform.runLater(() -> {
            assertNotNull(HeroApplication.getScene().getRoot());
        });
    }
}