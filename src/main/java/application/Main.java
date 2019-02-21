package application;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Main class
 * @author Elena Khasanova
 * @version 1.1;
 */

public class Main extends Application {

    public void start(Stage primaryStage) {
        MainView controller = new MainView();
        controller.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

