package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.SchemaCard;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerScheme implements Initializable {

    @FXML
    private GridPane schemeGridPane;

    private int col,row;

    public void createScheme (SchemaCard schemaCard) {

        for (Cell c : schemaCard.getCellList()) {
            ImageView cell = new ImageView();
            cell.setPreserveRatio(true); //used for the correct resize of the starting image
            cell.maxHeight(45);
            cell.setFitWidth(45);
            cell.setPreserveRatio(false); //to let the image resize bind to the anchorpane
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setMinHeight(40);
            anchorPane.setMinWidth(40);
            anchorPane.setPrefHeight(50);
            anchorPane.setPrefWidth(50);
            anchorPane.maxHeight(50);
            anchorPane.maxWidth(50);
            //anchorPane.setPadding(new Insets(4,4,4,4));  //TODO: fix

            anchorPane.setBorder(new Border(new BorderStroke(Color.web("2C3E50"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
            //anchorPane.setStyle("-fx-background-color: red");

            if (c.getValue() == 0 && c.getColour() == null) {
                anchorPane.setStyle("-fx-background-color: white");
            } else if (c.getColour() != null) {
                Map<ColourEnum, String> map = colorMap();
                anchorPane.setStyle("-fx-background-color: #" + map.get(c.getColour()));

            } else {
                try {
                    Image img = new Image(String.valueOf(new File("src\\main\\java\\it\\polimi\\se2018\\img\\dice\\restriction\\" +
                            c.getValue() + ".jpg").toURI().toURL()));
                    cell.setImage(img);
                } catch (MalformedURLException e) {
                    // return new MalformedURLException("Die image not found");
                }
            }
            if (col == 5) {
                col = 0;
                row++;
            }
            anchorPane.getChildren().addAll(cell);
            cell.fitHeightProperty().bind(anchorPane.heightProperty());
            cell.fitWidthProperty().bind(anchorPane.widthProperty());
            schemeGridPane.add(anchorPane, col, row);
            col++;
        }
        TextField textField = new TextField("Scheme Name");
        //textField.setStyle("-fx-text-fill: white; -fx-font-size: 60");
        //textField.setStyle("-fx-background-color: #2C3E50");
        schemeGridPane.add(textField, 2, 5);
    }

    private Map colorMap (){
        Map<ColourEnum, String> map = new EnumMap<>(ColourEnum.class);
        map.put(ColourEnum.BLUE, "6599CB");
        map.put(ColourEnum.RED, "FF6666");
        map.put(ColourEnum.GREEN, "66CC66");
        map.put(ColourEnum.YELLOW, "FEFF99");
        map.put(ColourEnum.PURPLE, "CC66CC");

        return map;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
