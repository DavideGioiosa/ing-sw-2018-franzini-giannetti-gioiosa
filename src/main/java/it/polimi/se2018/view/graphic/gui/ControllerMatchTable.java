package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class ControllerMatchTable implements Initializable{

    private List<ImageView> dice;
    private List<PublicObjCard> publicObjCards;

    @FXML
    private HBox hbox;
    @FXML
    private AnchorPane primaryScene;
    @FXML
    private AnchorPane schemePane;
    @FXML
    private ImageView publicCardImg;
    @FXML
    private HBox publicCardPane;
    @FXML
    private ImageView privateCardImg;
    @FXML
    private HBox privateCardPane;

    //scorrimento lettura carte
    private int indexPublicCard;

    private GameLoader gameLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int width = (int) Screen.getPrimary().getBounds().getWidth() ;
        int height = (int) Screen.getPrimary().getBounds().getHeight();
        primaryScene.setPrefWidth(width);
        primaryScene.setPrefHeight(height);

        gameLoader = new GameLoader();

        SchemaCard schemaCard = null;
        while (schemaCard == null || schemaCard.getId() != 400){
            schemaCard = (SchemaCard) gameLoader.getSchemaDeck().extractCard();
        }

        publicObjCards = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            publicObjCards.add((PublicObjCard) gameLoader.getPublicObjDeck().extractCard());
        }
        //componeScheme(schemaCard);
        showPrivateCards();
        showPublicCards();
        dice = new ArrayList();
    }

    private void showPrivateCards() {
        privateCardImg.setImage(showCard((PrivateObjCard) gameLoader.getPrivateObjDeck().extractCard(),
                "privateObjCard"));
        privateCardImg.setPreserveRatio(true);
        privateCardImg.setFitWidth(200);
        privateCardImg.fitWidthProperty().bind(privateCardPane.widthProperty());
        privateCardImg.fitHeightProperty().bind(privateCardPane.heightProperty().subtract(20));
    }


    private void showPublicCards() {
        publicCardImg.setImage(showCard(publicObjCards.get(0), "publicObjCard"));
        publicCardImg.setPreserveRatio(true);
        publicCardImg.setFitWidth(200);
        publicCardImg.fitWidthProperty().bind(publicCardPane.widthProperty());
        publicCardImg.fitHeightProperty().bind(publicCardPane.heightProperty().subtract(20));
    }


    //dadi sul draftpoll
    public BoardDice draftPoolStart (){

        BoardDice boardDice = new BoardDice();
        Die die1 = new Die(ColourEnum.BLUE);
        die1.firstRoll();
        Die die2 = new Die(ColourEnum.YELLOW);
        die2.firstRoll();
        Die die3 = new Die(ColourEnum.GREEN);
        die3.firstRoll();
        Die die4 = new Die(ColourEnum.PURPLE);
        die4.firstRoll();
        Die die5 = new Die(ColourEnum.RED);
        die5.firstRoll();
        boardDice.insertDice(die1);
        boardDice.insertDice(die2);
        boardDice.insertDice(die3);
        boardDice.insertDice(die4);
        boardDice.insertDice(die5);

        return boardDice;
    }


    /**
     * Set del numero di dadi che dovrò estrarre ogni volta nella riserva
     */
    //public void setNumDice(GameBoard gameBoard) {
        //this.numDraftDice = new ImageView[gameBoard.getPlayerList().size() * 2 + 1];
    //}


    private Image showCard (Card card, String stringa){
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\";

        try {
            return new Image(String.valueOf(new File(string + stringa + "\\" +
                    card.getName().replaceAll(" ", "") + ".jpg").toURI().toURL()));
        }catch (MalformedURLException e){
            // return new MalformedURLException("Public card image not found");
        }
        return null;
    }


    private Image showDie (Die die){
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\dice\\";

        try {
            return new Image(String.valueOf(new File(string + die.getColour().toString().toLowerCase()
                    + "\\" + die.getValue() + ".jpg").toURI().toURL()));
        }catch (MalformedURLException e){
            // return new MalformedURLException("Die image not found");
        }
        return null;
    }


    public void nextCard (ActionEvent actionEvent) throws IOException {
        if(indexPublicCard == 2){
            indexPublicCard = -1;
        }
        indexPublicCard++;
        publicCardImg.setImage(showCard(publicObjCards.get(indexPublicCard), "publicObjCard"));
    }

    /**
     * TODO: POSSIBILE SOLO AL PRIMO PLAYER DEL ROUND
     * Boolean isClicked
     */
    public void extractClick(ActionEvent actionEvent) throws IOException {
        BoardDice boardDice = draftPoolStart();

        // i < numDraftDice
        for (int i = 0; i < 5; i++) {
            ImageView die = new ImageView();
            die.setPreserveRatio(true);
            die.setFitWidth(45);
            die.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    //provaClickDado(); da implementare
                    event.consume();
                }
            });
            dice.add(die);
            dice.get(i).setImage(showDie(boardDice.getDieList().get(i)));

            hbox.getChildren().add(dice.get(i));
            die.fitWidthProperty().bind(hbox.widthProperty());
            die.fitHeightProperty().bind(hbox.heightProperty().subtract(20));
        }
    }


}

