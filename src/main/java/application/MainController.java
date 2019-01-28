package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    MainController() {

    }

    public void start(Stage primaryStage) {

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        // create a Load Text button
        Button loadButton = new Button();
        loadButton.setText("Text");
        loadButton.setMinWidth(100);
        toolBar.getItems().add(loadButton);
        toolBar.getItems().add(new Separator());

        // create a Flesch score button
        Button fleschButton = new Button();
        fleschButton.setText("Flesch");
        fleschButton.setMinWidth(100);
        toolBar.getItems().add(fleschButton);
        toolBar.getItems().add(new Separator());


        // create a FleschKincaid button
        Button fleschkincaid = new Button();
        fleschkincaid.setText("FleschKincaid");
        fleschkincaid.setMinWidth(100);
        toolBar.getItems().add(fleschkincaid);
        toolBar.getItems().add(new Separator());

        // create a text area for results
        TextArea text = new TextArea();
        Text fscore = new Text();
        Text fKInterpretation = new Text();
        Text levelPercentage = new Text();

        // set actions for buttons
        final TextController ctrl = new TextController(text, fscore, fKInterpretation, levelPercentage);
        loadButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    ctrl.loadText();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        fleschButton.setOnAction(new EventHandler<ActionEvent>() {
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

        // create a checkbox for levels
        final CheckBox cbKET = new CheckBox("KET");
        //cbKET.setIndeterminate(true);
       // boolean isSelected = cbKET.isSelected();
        toolBar.getItems().add(cbKET);

        // set actions for checkbox
        cbKET.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cbKET.isSelected()){
                    ctrl.percentageKet();
                }
            }
        });



// set parameters for the text area
        text.setFont(new Font(12));
        text.setText("");

        // create the general layout
        HBox root = new HBox();
        // create textfield area
        VBox textField = new VBox();
        root.getChildren().add(textField);
        textField.getChildren().add(text);
        text.setWrapText(true);

        // create area for buttons
        VBox buttonField = new VBox(toolBar);

        root.getChildren().add(buttonField);
//        buttonField.getChildren().add(loadButton);
//        buttonField.getChildren().add(fleschButton);
//        buttonField.getChildren().add(fleschkincaid);

        // create area for displaying information under the text area
        HBox fleschField = new HBox();
        textField.getChildren().add(fleschField);
        textField.getChildren().add(fscore);
        textField.getChildren().add(fKInterpretation);
        textField.getChildren().add(levelPercentage);
        //textField.getChildren().add(fleschkincaid);

        //fscore.setText("fscore");


        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Teacher's Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
// save button
// file chooser
// disappearing information when click Hide
// autocomplete
// spelling
