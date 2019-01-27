package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        MainController controller = new MainController();
        controller.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

