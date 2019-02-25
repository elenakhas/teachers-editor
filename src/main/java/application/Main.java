package application;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * The starting point of the application
 * @author Elena Khasanova
 * @version 0.8;
 */

public class Main extends Application {

    public void start(Stage primaryStage) {
        MainTextView controller = new MainTextView();
        controller.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

