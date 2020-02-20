package com.darjeedes.timetracker;

import java.io.InputStream;

import com.darjeedes.timetracker.business.SceneManager;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.switchToMainScene();

        primaryStage.setTitle("timetracker");
        InputStream iconStream = ClassLoader.getSystemResourceAsStream("icons8-uhr-64.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        }
        primaryStage.setOnCloseRequest(e -> handleExit());
        primaryStage.show();
    }

    private void handleExit() {
        // TODO: save entities before exit.
    }

    public static void main(String[] args) {
        launch(args);
    }

}
