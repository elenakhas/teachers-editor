package application;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Main gui class
 * @author Elena Khasanova
 * @version 1.1;
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

