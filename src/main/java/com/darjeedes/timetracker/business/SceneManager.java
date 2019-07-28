package com.darjeedes.timetracker.business;

import java.io.IOException;

import com.darjeedes.timetracker.persistence.DataService;
import com.darjeedes.timetracker.views.BaseController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// TODO: Remove SceneManager, as it isn't needed anymore.
public class SceneManager {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private static final String VIEWS_BASE_PATH = "/com/darjeedes/timetracker/views";

    private Stage primaryStage;
    private Scene mainScene;

    public SceneManager(final Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            DataService dataService = new DataService();
            this.mainScene = createScene(VIEWS_BASE_PATH + "/main/layout.fxml", dataService);
        } catch (IOException ex) {
            // TODO: Log error or at least display it. Shouldn't ever occur as long as the app files have integrity.
            // TODO: Don't exit from here, as it's ugly.
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private Scene createScene(final String resourcePath, final DataService dataService) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        BaseController controller = loader.getController();
        controller.setSceneManager(this);
        controller.setDataService(dataService);

        return scene;
    }

    public void switchToMainScene() {
        this.primaryStage.setScene(this.mainScene);
    }

}
