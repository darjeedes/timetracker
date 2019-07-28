package com.darjeedes.timetracker;

import com.darjeedes.timetracker.business.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.switchToMainScene();

        primaryStage.setTitle("Hello World");
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
