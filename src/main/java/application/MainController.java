package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MainController {

    MainController() {

    }
    private Desktop desktop = Desktop.getDesktop();

    public void start(Stage primaryStage) {

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        // create a Load Text button
//        Button loadButton = new Button();
//        loadButton.setText("Text");
//        loadButton.setMinWidth(100);
//        toolBar.getItems().add(loadButton);
//        toolBar.getItems().add(new Separator());

        Button chooser = new Button();
        chooser.setText("Open...");
        chooser.setMinWidth(100);
        toolBar.getItems().add(chooser);
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

        // create a button to check the level
        Button checkLevel = new Button();
        checkLevel.setText("Check the level");
        checkLevel.setMinWidth(100);


        // create a text area for results
        TextArea text = new TextArea();
        Text fscore = new Text();
        Text fKInterpretation = new Text();
        Text levelPercentage = new Text();
//        Text petPercentage = new Text();
//        Text startersPercentage = new Text();
//        Text moversPercentage = new Text();
//        Text flyersPercentage = new Text();
//        Text toeflPercentage = new Text();
//        Text fcePercentage = new Text();
//        Text ieltsPercentage = new Text();

        // set actions for buttons
        final TextController ctrl = new TextController(text, fscore, fKInterpretation, levelPercentage);
        // ketPercentage, petPercentage,
          //      startersPercentage, moversPercentage, flyersPercentage, toeflPercentage,
            //    fcePercentage, ieltsPercentage);




        chooser.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                FileChooser fc = new FileChooser();
//                File selected = fc.showOpenDialog(null);
//                if (selected != null){
//                    try {
//                        desktop.open(selected);
//                    } catch (IOException e) {
//                        Logger.getLogger(
//                                FileChooser.class.getName()).log(
//                                Level.SEVERE, null, e
//                        );
//                    }
//                }

                try {
                    ctrl.loadText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





//        loadButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    ctrl.loadText();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
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
        final CheckBox cbPET = new CheckBox("PET");
        toolBar.getItems().add(cbPET);
        final CheckBox cbStarters = new CheckBox("Starters");
        toolBar.getItems().add(cbStarters);
        final CheckBox cbMovers = new CheckBox("Movers");
        toolBar.getItems().add(cbMovers);
        final CheckBox cbFlyers = new CheckBox("Flyers");
        toolBar.getItems().add(cbFlyers);
        final CheckBox cbFCE = new CheckBox("FCE");
        toolBar.getItems().add(cbFCE);
        final CheckBox cbIELTS = new CheckBox("IELTS");
        toolBar.getItems().add(cbIELTS);
        final CheckBox cbTOEFL = new CheckBox("TOEFL");
        toolBar.getItems().add(cbTOEFL);
        toolBar.getItems().add(new Separator());
        toolBar.getItems().add(checkLevel);

        // set actions for checkLevel button:
        checkLevel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                ctrl.handleCheckboxes(cbKET, cbPET, cbStarters, cbMovers, cbFlyers);
            }
        });


//        // set actions for checkbox
//        cbKET.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (cbKET.isSelected()){
//                    ctrl.percentageKet();
//                }
//
//            }
//        });
//
//        cbPET.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (cbPET.isSelected()){
//                    ctrl.percentagePet();
//                }
//            }
//        });

//        cbStarters.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (cbStarters.isSelected()){
//                    ctrl.percentageStarters();
//                }
//            }
//        });
//
//        cbMovers.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (cbMovers.isSelected()){
//                    ctrl.percentageMovers();
//                }
//            }
//        });
//
//        cbFlyers.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (cbFlyers.isSelected()){
//                    ctrl.percentageFlyers();
//                }
//            }
//        });



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
        //textField.getChildren().add(petPercentage);


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
/*#TODO: file chooser;
save;
unit tests;
autocomplete;
hide text if unchecked;*/
