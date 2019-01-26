package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {

    MainController(){

    }

    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("LoadText");
        btn.setMinWidth(100);
        Button flesch = new Button();
        Button fleschkincaid = new Button();
        flesch.setText("Flesch Score");
        fleschkincaid.setText("FleschKincaid");
        TextArea text = new TextArea();
        Text fscore = new Text();
        Text fKInterpretation = new Text();
        final TextController ctrl = new TextController(text, fscore, fKInterpretation);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ctrl.loadText();

            }
        });
        flesch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.getFlesch();
            }
        });
        fleschkincaid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctrl.fleschKincaid();
            }
        });


        text.setFont(new Font(12));
        text.setText("");

        // create the general layout
        HBox root = new HBox();
        VBox textField = new VBox();
        root.getChildren().add(textField);
        textField.getChildren().add(text);
        text.setWrapText(true);
        VBox buttonField = new VBox();
        root.getChildren().add(buttonField);
        buttonField.getChildren().add(btn);
        buttonField.getChildren().add(flesch);
        buttonField.getChildren().add(fleschkincaid);
        HBox fleschField = new HBox();
        textField.getChildren().add(fleschField);
        fleschField.getChildren().add(fscore);
        fleschField.getChildren().add(fKInterpretation);
        //textField.getChildren().add(fleschkincaid);

        //fscore.setText("fscore");


        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
// save button
// file chooser
// disappearing information when click Hide
// autocomplete
// spelling
