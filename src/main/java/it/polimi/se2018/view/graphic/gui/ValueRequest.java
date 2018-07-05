package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.view.SyntaxController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * View's Graphic Class: Value Request
 *
 * @author Davide Gioiosa
 */

public class ValueRequest {

    public static void display (SyntaxController syntaxController){

        Stage toolReqWindow = new Stage();
        toolReqWindow.setOnCloseRequest(event -> {
            // prevent window from closing
            event.consume();

        });
        toolReqWindow.setTitle("Tool Request");
        toolReqWindow.setMinWidth(250);
        toolReqWindow.setMinHeight(250);
        toolReqWindow.setMaxHeight(250);
        toolReqWindow.setMaxWidth(250);
        toolReqWindow.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label();
        label.setText("Inserisci il valore adeguato");
        label.setFont(Font.font("Verdana", FontWeight.BOLD,14));
        TextField textField = new TextField();
        textField.setMaxWidth(40);
        textField.setPrefHeight(40);
        textField.setStyle("-fx-font-size: 16px;");

        Text errorText = new Text();


        Button confirmButton = new Button("Conferma");
        confirmButton.setOnAction(event -> {
            try {
                int valueInsert = Integer.parseInt(textField.getText());
                if(textField.getText().isEmpty() || valueInsert < -1 || valueInsert > 6 ||  valueInsert == 0 ) {
                    errorText.setText("Valore non corretto ! Reinserisci valore");
                }
                else {
                    syntaxController.validCommand(textField.getText());
                    toolReqWindow.close();
                }
            } catch (NumberFormatException e) {
                errorText.setText("Valore non corretto ! Reinserisci valore");
            }

        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, textField, errorText, confirmButton);

        Scene scene = new Scene(vBox);
        toolReqWindow.setScene(scene);
        toolReqWindow.showAndWait();

    }
}
