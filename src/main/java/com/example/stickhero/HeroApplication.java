package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HeroApplication extends Application {
    private static Scene scene;
    private static final double sceneWidth = 360;
    private static final double sceneHeight = 600;
    // Also need to change play.fxml, start.fxml and gameOver to have the same width and height(along with the background image)
    public static double getSceneHeight() {
        return sceneHeight;
    }

    public static void setScene(Scene scene) {
        HeroApplication.scene = scene;
    }

    public static int getSceneWidth() {
        return (int) sceneWidth;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Write dimensions to dimensions.txt
        writeDimensionsToFile();

        scene = new Scene(loadFXML("start"), sceneWidth, sceneHeight);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Scene getScene(){
        return scene;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HeroApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void writeDimensionsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/com/example/stickhero/dimensions.txt"))) {
            writer.println("prefWidth=" + HeroApplication.sceneWidth);
            writer.println("prefHeight=" + HeroApplication.sceneHeight);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}