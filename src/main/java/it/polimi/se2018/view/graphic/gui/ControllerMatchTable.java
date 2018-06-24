package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.BoardDice;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.cards.SchemaCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ControllerMatchTable {
    @FXML
    private ImageView dieimg1, dieimg2, dieimg3, dieimg4, dieimg5, dieimg6, dieimg7, dieimg8, dieimg9;

    private ImageView dice[];

    /**
     * Number of dice to extract and put into the Draft Pool
     * @param gameBoard
     */
    public void setNumDice(GameBoard gameBoard) {
        dice = new ImageView[] {dieimg1, dieimg2, dieimg3, dieimg4, dieimg5, dieimg6, dieimg7, dieimg8, dieimg9};
        //TODO: this.dice = new ImageView[gameBoard.getPlayerList().size() * 2 + 1];
    }

    private Image showDie (Die die){
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\dice\\";

        try {
            return new Image(String.valueOf(new File(string + die.getColour().toString().toLowerCase()
                    + "\\" + die.getValue() + ".jpg").toURI().toURL()));
        }catch (Exception e){
            //TODO: exception image not reached
        }
        return null;
    }

    /**
     * TODO: POSSIBILE SOLO AL PRIMO PLAYER DEL ROUND
     */
    public void initialize(ActionEvent actionEvent, GameBoard gameBoard) throws IOException {
        setNumDice(gameBoard);
        for (int i = 0; i < 5; i++) {
            //ImageView die = new ImageView();
            dice[i].setImage(showDie(gameBoard.getBoardDice().getDieList().get(i)));
        }
    }

}
