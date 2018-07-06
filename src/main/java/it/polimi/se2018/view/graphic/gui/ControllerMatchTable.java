package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.socket.SocketTypeClient;
import it.polimi.se2018.controller.client.ClientController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.PlayerSetupper;
import it.polimi.se2018.view.SyntaxController;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.graphic.TypeOfInputAsked;
import it.polimi.se2018.view.graphic.cli.CommandLinePrint;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Screen;


import java.net.URL;
import java.util.*;
import java.util.List;


/**
 * View's Graphic Class: ControllerMatchTable
 *
 * @author Davide Gioiosa
 */

public class ControllerMatchTable implements Initializable {

    //Lista delle ImageView dei dadi nel DraftPool
    private List<ImageView> diceOnDraftList;

    //Lista delle celle dello schema scelto dall'utente
    private List<AnchorPane> cellSchemeList;

    //Lista dei bottoni delle ToolCard da usare
    private List<Button> toolChoiceButtonList;

    //Lista di liste per dadi sul trackboard
    List<ImageView> diceExtraImgTrackboardList;

    List<Button> buttonTrackBoarIndexList;

    //Lista index dello switch dei dadi sulla track
    List<Integer> indexChangeDiceTrackBoard;

    @FXML GridPane schemeSelectionGrid;

    @FXML private AnchorPane toolCardPane;

    @FXML private HBox hBoxDraftDice;
    @FXML private AnchorPane primaryScene;
    @FXML private ImageView cardImg;
    @FXML private HBox publicCardPane;
    @FXML private ImageView privateCardImg;
    @FXML private GridPane gridPane;


    //TASTI SUL TAVOLO

    @FXML private Button extractButton;
    @FXML private Button toolCardButton;
    @FXML private Button resetMoveButton;
    @FXML private Button passButton;
    //-------------------------------------------- SCHEME SELECTION -----------------------------

    @FXML AnchorPane backgroundPane;
    @FXML AnchorPane loginPane;

    //-----------
    //VBOX CONTENENTE GLI SCHEMI DEI 4 GIOCATORI DURANTE LA PARTITA

    @FXML private VBox schemeVBOX;
    private List<VBox> schemeVBoxList;
    //--------------------------------------------------------------------------------

    @FXML private AnchorPane schemeSelectionPane;

    @FXML private AnchorPane gameboardPane;

    @FXML private AnchorPane lobbyPane;

    @FXML private AnchorPane scorePane;
    /**
     * Message Area in the table
     */
    private static TextArea msgArea;
    @FXML private TextArea msgWinnerArea;

    @FXML Text numTokens;

    /**
     * Img Dado selezionato dal Draft per la PICK
     */
    private ImageView selectedDie;

    /**
     * Button selezionato per la scelta dell'uso della toolCard
     */
    private Button selectedButtonToolCardToUse;

    /**
     * AnchorPane della cella cliccata
     */
    private AnchorPane cellSelected;

    /**
     * Button pressed of the selection Scheme Pane
     */
    private Button selectedButtonScheme;

    /**
     * Img Dado selezionato dalla Trackboard
     */
    private ImageView selectedTrackboardDie;

    /**
     * Button on the Trackboard pressed to see the other extra dice
     */
    private Button selectedButtonTrackBoardNext;

    //-----------------------------

    private PlayerChoice playerChoice;

    @FXML GridPane choiceToolCardGrid;

    //-------------------

    @FXML AnchorPane nicknamePane;
    @FXML TextArea nicknameAreaText;
    @FXML GridPane trackBoardGrid;

    //---------------------

    ClientBoard clientBoard;

    ClientModel clientModel;

    @FXML HBox hBoxMsgArea;

    //----------------------

    //scorrimento lettura carte pubbliche e obj privato
    private int indexChangeCard;

    private View viewSocket;

    private Client clientSocket;

    private ClientController clientControllerSocket;

    private PlayerSetupper playerSetupper;

    private SyntaxController syntaxController;

    private CommandTypeEnum nextCommandType;

    private TypeOfInputAsked typeOfInputAsked;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();
        primaryScene.setPrefWidth(width);
        primaryScene.setPrefHeight(height);


        cardSize(cardImg);

        toolCardPane.setDisable(true);
        schemeSelectionPane.setDisable(true);
        loginPane.setDisable(true);
        nicknamePane.setDisable(true);
        gameboardPane.setDisable(true);
        lobbyPane.setDisable(true);
        scorePane.setDisable(true);

        viewSocket = new View();
        GuiInput guiInput = (GuiInput) viewSocket.getInputStrategy();
        guiInput.setControllerMatchTable(this);
        GuiOutput guiOutput = (GuiOutput) viewSocket.getOutputStrategy();
        guiOutput.setControllerMatchTable(this);
        clientSocket = new Client(new SocketTypeClient("localhost", 1111), viewSocket);
        playerSetupper = viewSocket.getInputStrategy().getPlayerSetupper();
        syntaxController = viewSocket.getInputStrategy().getSyntaxController();
        clientControllerSocket = new ClientController(clientSocket, viewSocket);

        viewSocket.getInputStrategy().getPlayerSetupper().addObserver(clientControllerSocket);
        viewSocket.getInputStrategy().getSyntaxController().addObserver(clientControllerSocket);
        clientSocket.connect();

        //------------------------------------

        cellSchemeList = new ArrayList<>();
        diceOnDraftList = new ArrayList();
        toolChoiceButtonList = new ArrayList<>();
        diceExtraImgTrackboardList = new ArrayList<>();
        buttonTrackBoarIndexList = new ArrayList<>();
        indexChangeDiceTrackBoard = new ArrayList<>();
        schemeVBoxList = new ArrayList<>();

        typeOfInputAsked = null;

        createMsgArea();

    }


    private void showPrivateCards() {
        cardImg.setImage(showCard(clientModel.getPrivateObjCard(), "privateObjCard"));
    }

    private void showPublicCards(ClientBoard clientBoard, int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getPublicObjCardList().get(index), "publicObjCard"));
    }

    //TODO: TOGLIERE GIA' SI VISUALIZZANO DAL MENU
    private void showToolCards(ClientBoard clientBoard, int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getToolCardList().get(index), "toolCard"));
    }

    private void showNextDieTrackBoard (ClientBoard clientBoard, int index, int indexRound){
        diceExtraImgTrackboardList.get(indexRound).setImage(showDie
                (clientBoard.getTrackBoardDice().getDiceList().get(indexRound).get(index)));
    }

    /**
     * Sets the correct size and binding of the ImageView
     * @param cardImageView that has to be resized and binded
     */
    private void cardSize (ImageView cardImageView){
        cardImageView.setPreserveRatio(true);
        cardImageView.setFitWidth(200);
        cardImageView.fitWidthProperty().bind(publicCardPane.widthProperty());
        cardImageView.fitHeightProperty().bind(publicCardPane.heightProperty().subtract(20));
    }


    private Image showCard(Card card, String stringa) {
        String string = "it/polimi/se2018/view/graphic/gui/img/" + stringa + "/" +
                card.getName().replaceAll(" ", "") + ".jpg" ;

        return new Image(string);

    }

     private Image showDie(Die die) {
        String string = "it/polimi/se2018/view/graphic/gui/img/dice/" +
                die.getColour().toString().toLowerCase() + "/" + die.getValue() + ".jpg";

        return new Image(string);
    }


    @FXML private void nextCard() {
        if (indexChangeCard == 3) {
            indexChangeCard = -1;
        }
        indexChangeCard++;
        if(indexChangeCard == 3){
            showPrivateCards();
        }
        showPublicCards(clientBoard, indexChangeCard);
    }

    @FXML private void nextDieTrackboard(int selectedButtonIndex, int numExtraDiceRound) {
        if (indexChangeDiceTrackBoard.get(selectedButtonIndex) == numExtraDiceRound - 1) {
            indexChangeDiceTrackBoard.remove(selectedButtonIndex);
            indexChangeDiceTrackBoard.add(selectedButtonIndex, -1);
        }
        int indexValue = indexChangeDiceTrackBoard.get(selectedButtonIndex).intValue();
        indexChangeDiceTrackBoard.remove(selectedButtonIndex);
        indexChangeDiceTrackBoard.add(selectedButtonIndex, (indexValue + 1));

        showNextDieTrackBoard(clientBoard, indexValue + 1, selectedButtonIndex);
    }



    @FXML private void extractClick() {
        syntaxController.validCommand("extract");
    }

    @FXML private void passClick () {
        syntaxController.validCommand("pass");
    }

    @FXML private void cancelClick () {
        syntaxController.validCommand("cancel");
    }

    private void showDiceBoard (){
        for (int i = 0; i < clientBoard.getBoardDice().getDieList().size(); i++) {
            ImageView die = new ImageView();
            die.setPreserveRatio(true);
            die.setFitWidth(45);
            die.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                selectedDie = die;
                checkDiceBoardIndex();

                event.consume();
            });
            diceOnDraftList.add(die);
            diceOnDraftList.get(i).setImage(showDie(clientBoard.getBoardDice().getDieList().get(i)));

            hBoxDraftDice.getChildren().add(diceOnDraftList.get(i));
            die.fitWidthProperty().bind(hBoxDraftDice.widthProperty());
            die.fitHeightProperty().bind(hBoxDraftDice.heightProperty().subtract(20));
        }
    }



    //ENABLE & DISABLE PANE //

    @FXML private void disableBackgroundPane (){
        backgroundPane.setOpacity(0);
        backgroundPane.setVisible(false);
        backgroundPane.setDisable(true);
    }

    @FXML private void enableBackgroundPane (){
        backgroundPane.setOpacity(1);
        backgroundPane.setVisible(true);
        backgroundPane.setDisable(false);
    }

    @FXML private void disableNicknamePane (){
        nicknamePane.setOpacity(0);
        nicknamePane.setVisible(false);
        nicknamePane.setDisable(true);
        disableBackgroundPane();
        enableLobbyPane ();
        sendNickname();
    }

    @FXML private void enableNicknamePane (){
        enableBackgroundPane();
        nicknamePane.setOpacity(1);
        nicknamePane.setVisible(true);
        nicknamePane.setDisable(false);
    }

    @FXML private void disableLobbyPane (){
        lobbyPane.setOpacity(0);
        lobbyPane.setVisible(false);
        lobbyPane.setDisable(true);
    }

    @FXML private void enableLobbyPane (){
        enableBackgroundPane();
        lobbyPane.setOpacity(1);
        lobbyPane.setVisible(true);
        lobbyPane.setDisable(false);
    }

    @FXML private void disableLoginPane (){
        loginPane.setOpacity(0);
        loginPane.setVisible(false);
        loginPane.setDisable(true);
        disableBackgroundPane();

        //dà l'indirizzo IP inserito

    }

    @FXML private void enableLoginPane (){
        enableBackgroundPane();
        loginPane.setOpacity(1);
        loginPane.setVisible(true);
        loginPane.setDisable(false);
    }

    @FXML private void disableSchemeSelectionPane (){
        disableBackgroundPane();
        enableLobbyPane ();
        schemeSelectionPane.setOpacity(0);
        schemeSelectionPane.setVisible(false);
        schemeSelectionPane.setDisable(true);
        sendSelectedScheme();

    }

    @FXML private void enableSchemeSelectionPane (List<SchemaCard> schemeToChooseList, PrivateObjCard privateObjCard){
        inizSchemeCardSelection(schemeToChooseList, privateObjCard);
        disableLobbyPane();
        enableBackgroundPane();
        schemeSelectionPane.setOpacity(1);
        schemeSelectionPane.setVisible(true);
        schemeSelectionPane.setDisable(false);
    }

    @FXML private void disableToolCardPane (){
        //confirmToolCardChoice(); //TODO: NO??
        disableBackgroundPane();
        toolCardPane.setOpacity(0);
        toolCardPane.setVisible(false);
        toolCardPane.setDisable(true);
        enableGameboardPane();

        //dà i dati della toolcard scelta
    }

    @FXML private void enableToolCardPane (){
        disableGameboardPane();
        enableBackgroundPane();
        toolCardPane.setOpacity(1);
        toolCardPane.setVisible(true);
        toolCardPane.setDisable(false);
    }

    @FXML private void disableGameboardPane (){
        gameboardPane.setOpacity(0);
        gameboardPane.setVisible(false);
        gameboardPane.setDisable(true);

    }

    @FXML private void enableGameboardPane (){
        disableBackgroundPane();
        disableLobbyPane();
        gameboardPane.setOpacity(1);
        gameboardPane.setVisible(true);
        gameboardPane.setDisable(false);
    }

    @FXML private void disableScorePane (){
        scorePane.setOpacity(0);
        scorePane.setVisible(false);
        scorePane.setDisable(true);
    }

    @FXML private void enableScorePane (){
        scorePane.setOpacity(1);
        scorePane.setVisible(true);
        scorePane.setDisable(false);
    }

    // REQUEST: richieste invocate dal GuiInput //

    public void requestSchemeSelection (PlayerChoice playerChoice){
        this.playerChoice=playerChoice;
        playerSetupper.newChoiceReceived(playerChoice); //la stringa che ti invio è per scelta scheme

        enableSchemeSelectionPane(playerChoice.getSchemaCardList(), playerChoice.getPrivateObjCard());
    }

    public void requestYourTurn (ClientBoard clientBoard, PlayerMove playerMove){
        enableCommandButton();
        enableToolChoiceButton();
        hBoxDraftDice.setDisable(false);

        typeOfInputAsked = syntaxController.newMoveReceived(playerMove, clientBoard);
        this.nextCommandType = typeOfInputAsked.getCommandTypeEnum(); //la mossa richiesta

        //extractButton.setEffect(Shadow);
    }

    public void requestNickname (User user){
        enableNicknamePane();

        playerSetupper.newUserReceived(user); //la stringa che ti invio è per il nick dell'user
    }

    //UPDATE
    public void requestShowGameboard (ClientModel clientModel){
        this.clientBoard = clientModel.getClientBoard();
        this.clientModel = clientModel;
        this.clientModel.setPrivateObjCard(playerChoice.getPrivateObjCard());
        enableGameboardPane();
        inizGameboard(clientBoard);
        disableNotYourTurn();
    }

    public void requestShowScore (List<Player> playerList){
        enableScorePane();
        String string = "";
        String winner = "Nessuno";
        int maxScore = 0;
        for (int i = 0; i <playerList.size(); i++) {
            string = (i+1) + ". " + playerList.get(i).getNickname() + " " + playerList.get(i).getScore() + "\n" + string ;

            if (playerList.get(i).getScore() > maxScore){
                maxScore = playerList.get(i).getScore();
                winner = playerList.get(i).getNickname();
            }
        }
        msgWinnerArea.setText("La partita è terminata.\nLa classifica è:\n\n" + string +
                "\nIl vincitore è :\n" + winner);
        msgWinnerArea.setStyle("-fx-font-size: 24");
    }


    //SEND: invio della scelta fatta dall'utente

    private void sendSelectedScheme (){

        if(selectedButtonScheme.getText().equals("Schema 1")){
            playerSetupper.validCommand(((Integer) playerChoice.getSchemaCardList().get(0).getId()).toString());
        }
        else if(selectedButtonScheme.getText().equals("Schema 2")){
            playerSetupper.validCommand(((Integer) playerChoice.getSchemaCardList().get(1).getId()).toString());
        }
        else if(selectedButtonScheme.getText().equals("Schema 3")){
            playerSetupper.validCommand(((Integer) playerChoice.getSchemaCardList().get(2).getId()).toString());
        }
        else if(selectedButtonScheme.getText().equals("Schema 4")){
            playerSetupper.validCommand(((Integer) playerChoice.getSchemaCardList().get(3).getId()).toString());
        }
    }


    private void sendNickname (){
        playerSetupper.validNickname(nicknameAreaText.getText());
    }


    //INIZ: chiamati per posizionare le carte nel corrispettivo pane //

    //ADD DINAMICO DEI 4 SCHEMI CHE L'UTENTE DEVE SCEGLIERE
    //TODO: ADD CARTA OBJ PRIVATO
    private void inizSchemeCardSelection(List<SchemaCard> schemeToChooseList, PrivateObjCard privateObjCard){

        Platform.runLater(() -> {
            for(int i = 0; i <5; i++) {
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(50);
                if(i == 0){
                    Text text = new Text("La tua carta obiettivo privato :");
                    text.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
                    text.setFill(Color.WHITE);
                    ImageView privateObjImg = new ImageView(showCard(privateObjCard, "privateObjCard"));
                    cardSize(privateObjImg);
                    vBox.getChildren().addAll(text, privateObjImg);
                }else {
                    Button button = new Button("Schema " + (i));
                    button.setOnAction(event -> {
                        selectedButtonScheme = button;
                        disableSchemeSelectionPane();
                    });
                    GridPane schemeGrid = createScheme(schemeToChooseList.get(i - 1));
                    vBox.getChildren().addAll(button, schemeGrid);
                }

                schemeSelectionGrid.add(vBox, i, 0);
            }
        });
    }


    private void inizToolCard (){
        for(int i = 0; i < 3; i++) {
            VBox vBox = new VBox();
            ImageView toolImg = new ImageView(showCard(clientBoard.getCardOnBoard().getToolCardList().get(i), "toolCard"));
            toolImg.setPreserveRatio(true);
            toolImg.setFitWidth(300);
            //toolImg.fitWidthProperty().bind(vBox.widthProperty());

            Button button = new Button("ToolCard " + (i + 1));
            button.setOnAction(e -> {
                selectedButtonToolCardToUse = button;
                checkToolIndex();
                disableToolCardPane();
            });
            toolChoiceButtonList.add(button);

            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(40);
            vBox.getChildren().addAll(button, toolImg);

            if(clientBoard.getCardOnBoard().getToolCardList().get(i).getToken() > 0){
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                for(int j = 0; j < clientBoard.getCardOnBoard().getToolCardList().get(i).getToken(); j++){
                    ImageView tokenImg = new ImageView("it/polimi/se2018/view/graphic/gui/img/Token.png");
                    tokenImg.setPreserveRatio(true);
                    tokenImg.setFitWidth(25);
                    hBox.getChildren().add(tokenImg);
                }
                vBox.getChildren().add(hBox);
            }

            if(i==1){
                Button cancelButton = new Button("Torna al menù");
                cancelButton.setCursor(Cursor.HAND);
                cancelButton.setOnAction(e -> disableToolCardPane());
                vBox.getChildren().add(cancelButton);
            }
            choiceToolCardGrid.add(vBox, i,0);
        }
    }


    //TODO: FIX, NON SAI DI CHI E' LO SCHEMA, NUM DI GIOCATORI
    private void inizGameboard (ClientBoard clientBoard){
        Platform.runLater(() -> {

                emptyPanes();

                schemeVBOX.getChildren().add(createScheme(clientModel.getActualPlayer().getSchemaCard()));
                System.out.println(" SIZE " + clientBoard.getPlayerList().size());

                int i;
                for(i = 0; i < clientBoard.getPlayerList().size(); i++) {
                    VBox vBox = new VBox();
                    vBox.setPadding(new Insets(10,60,0,60));
                    vBox.getChildren().add(createScheme(clientBoard.getPlayerList().get(i).getSchemaCard()));
                    vBox.prefWidthProperty().bind(schemeVBOX.widthProperty());
                    vBox.prefHeightProperty().bind(schemeVBOX.heightProperty());
                    schemeVBoxList.add(vBox);
                    if(i == 2) {
                        gridPane.add(vBox, 0, 0);
                    }
                    else {
                        gridPane.add(vBox, i + 1, 0);
                    }
                }
                //delete extra one
                schemeVBoxList.get(0).getChildren().clear();

                System.out.println("..update..");
                inizToolCard();
                 for(int j = 0; j < toolChoiceButtonList.size(); j++) {
                     toolChoiceButtonList.get(j).setDisable(true);
                 }
                showPublicCards(clientBoard,0);
                showDiceBoard();
                insertDiceTrackboard();
                setNumTokens(String.valueOf(clientModel.getActualPlayer().getTokens()));
        });
    }

    public static void inizMsgAreaError (int id){
        if(id != 0) {
            String string = msgArea.getText();
            msgArea.setText("ERROR: " + CommandLinePrint.errorMap.get(id) + "\n" + string);
        }
    }

    public static void inizMsgAreaMessage (int id){
        if(id != 0) {
            String string = msgArea.getText();
            msgArea.setText("Message: " + CommandLinePrint.messageMap.get(id) + "\n" + string);
        }
    }

    /**
     * Empty panels to prepare them for the next update
     */
    private void emptyPanes(){
        schemeVBOX.getChildren().clear();
        for (int i = 0; i < schemeVBoxList.size(); i++){
            schemeVBoxList.get(i).getChildren().clear();
            schemeVBoxList.remove(i);
        }
        choiceToolCardGrid.getChildren().clear();
        hBoxDraftDice.getChildren().clear();
        cellSchemeList.clear();
        diceOnDraftList.clear();
        toolChoiceButtonList.clear();
        trackBoardGrid.getChildren().clear();
    }


    private void insertDiceTrackboard () {
        if(!clientBoard.getTrackBoardDice().getDiceList().isEmpty()) {

            for (int i = 0; i < clientBoard.getTrackBoardDice().getDiceList().size(); i++) {
                ImageView dieExtra = new ImageView();
                dieExtra.setPreserveRatio(true);
                dieExtra.setFitWidth(45);
                dieExtra.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    selectedTrackboardDie = dieExtra;
                    checkDieTrackBoardIndex();

                    event.consume();
                });

                diceExtraImgTrackboardList.add(dieExtra);

                diceExtraImgTrackboardList.get(i).setImage(showDie
                        (clientBoard.getTrackBoardDice().getDiceList().get(i).get(0)));

                trackBoardGrid.add(diceExtraImgTrackboardList.get(i), 0, 9 - i);
                Button button = new Button(">");
                button.setStyle("fx-background-radius: 300;");
                button.setCursor(Cursor.HAND);

                button.setOnAction(event -> {
                    selectedButtonTrackBoardNext = button;

                    //TODO: EVITARE DI CHIAMARE LA FUNZ DIVERSE VOLTE
                    if(checkButtonTrackBoardIndex() != -1) {
                        nextDieTrackboard(checkButtonTrackBoardIndex(),
                                clientBoard.getTrackBoardDice().getDiceList().get(checkButtonTrackBoardIndex()).size());
                    }
                    //è premuto, chiama metodo switcha carta
                });
                indexChangeDiceTrackBoard.add(0);
                buttonTrackBoarIndexList.add(button);
                trackBoardGrid.add(buttonTrackBoarIndexList.get(i), 1, 9 - i);


                if(clientBoard.getTrackBoardDice().getDiceList().get(i).size() == 1) {
                    buttonTrackBoarIndexList.get(i).setVisible(false);
                    buttonTrackBoarIndexList.get(i).setDisable(true);
                }
            }
        }
    }


    private GridPane createScheme (SchemaCard schemaCard) {
        int col = 0;
        int row = 0;

        GridPane schemeGridPane = new GridPane();
        gridPane.maxHeight(400);
        gridPane.maxWidth(320);
        gridPane.setGridLinesVisible(true);

        for (Cell c : schemaCard.getCellList()) {
            ImageView cellImg = configCellImg();
            AnchorPane anchorPaneCell = configAnchorPane();

            anchorPaneCell.setOnMouseClicked(event -> {
                cellSelected = anchorPaneCell;
                checkCellIndex();
                event.consume();
            });

            if (c.getValue() == 0 && c.getColour() == null) {
                anchorPaneCell.setStyle("-fx-background-color: white");
            }
            else if (c.getColour() != null) {
                Map<ColourEnum, String> map = colorMap();
                anchorPaneCell.setStyle("-fx-background-color: #" + map.get(c.getColour()));

            }
            else {
                setCellValueRestriction(c, cellImg);
            }

            if(!c.isEmpty()){
                cellImg.setImage(showDie(c.getDie()));

                //Handle the click to see what's under a die placed in the scheme
                cellImg.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                    if(c.getValue() != 0){
                        setCellValueRestriction(c, cellImg);
                    }else {
                        cellImg.setImage(null);
                    }
                    event.consume();
                });

                cellImg.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                    cellImg.setImage(showDie(c.getDie()));
                    event.consume();
                });
            }

            if (col == 5) {
                col = 0;
                row++;
            }

            anchorPaneCell.getChildren().addAll(cellImg);   //add img (conterrà dadi messi) all'anchor pane della cella
            AnchorPane.setTopAnchor(cellImg, 0.6);
            AnchorPane.setLeftAnchor(cellImg, 0.3);
            cellImg.fitHeightProperty().bind(anchorPaneCell.heightProperty().subtract(7));
            cellImg.fitWidthProperty().bind(anchorPaneCell.widthProperty().subtract(7));
            cellSchemeList.add(anchorPaneCell);


            schemeGridPane.add(anchorPaneCell, col, row);
            col++;
        }

            HBox infoScheme = configHBox();

            Text text = new Text(schemaCard.getName());
            text.setFill(Color.WHITE);
            Text emptyText = new Text("       ");
            infoScheme.getChildren().addAll(text, emptyText);

            for(int i = 0; i < schemaCard.getDifficulty(); i++) {
                Circle circle = new Circle(5, 5, 4);
                circle.setStroke(Color.WHITE);
                circle.setStrokeWidth(2);
                circle.setFill(Color.web("#2C3E50"));

                infoScheme.getChildren().add(circle);
            }

            schemeGridPane.add(infoScheme, 0, row + 1);
            schemeGridPane.setColumnSpan(infoScheme, 5);

            schemeGridPane.setBorder(new Border(new BorderStroke(Color.web("2C3E50"),
                BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths (0))));


        return schemeGridPane;
    }

    private void setCellValueRestriction (Cell c, ImageView cellImg){
        Image img = new Image("it/polimi/se2018/view/graphic/gui/img/dice/restriction/" + c.getValue() + ".jpg");
        cellImg.setOpacity(0.90);
        cellImg.setImage(img);
    }

    // CHECK: invocano il syntaxController inviandogli il comando valido //

    private void checkCellIndex() {

        for (int i = 0; i < cellSchemeList.size(); i++) {
            if(cellSchemeList.get(i) == cellSelected){
                int index = i % 20;
                int row = index / 5;
                int col = index % 5;
                typeOfInputAsked = syntaxController.validCommand(row + " " + col);
                nextCommandSequence(typeOfInputAsked);
                return;
            }
        }
    }


    private void checkToolIndex() {
        syntaxController.validCommand("tool");

        for (int i = 0; i < toolChoiceButtonList.size(); i++) {
            if(toolChoiceButtonList.get(i) == selectedButtonToolCardToUse){

                typeOfInputAsked = syntaxController.validCommand(((Integer) clientBoard.getCardOnBoard()
                        .getToolCardList().get(i).getId()).toString());
                nextCommandSequence(typeOfInputAsked);
                return;
            }
        }
    }


    private void checkDiceBoardIndex(){
        syntaxController.validCommand("pick");

        for (int i = 0; i < clientBoard.getBoardDice().getDieList().size(); i++) {
            if(diceOnDraftList.get(i) == selectedDie){
                typeOfInputAsked = syntaxController.validCommand(((Integer) i).toString());
                nextCommandSequence(typeOfInputAsked);
                return;
            }
        }

        //TODO: SE HO ERRORE?
    }

    private void checkDieTrackBoardIndex (){

        for(int i = 0; i < clientBoard.getTrackBoardDice().getDiceList().size(); i++) {
            for(int j = 0; j < clientBoard.getTrackBoardDice().getDiceList().get(i).size(); j++) {
                if (diceExtraImgTrackboardList.get(i) == selectedTrackboardDie) {
                    typeOfInputAsked = syntaxController.validCommand(i + " " + indexChangeDiceTrackBoard.get(i));
                    nextCommandSequence(typeOfInputAsked);
                }
            }
        }
    }

    private int checkButtonTrackBoardIndex (){

        for(int i = 0; i < buttonTrackBoarIndexList.size(); i++) {
            if (buttonTrackBoarIndexList.get(i) == selectedButtonTrackBoardNext) {
                return i;
            }
        }
        return -1;
    }

    private void nextCommandType (CommandTypeEnum commandTypeEnum) {
        switch (commandTypeEnum){
            case COMPLETE: //TODO: disabilita tutto
        //        disableNotYourTurn();
                break;
            case DICEBOARDINDEX:
                hBoxDraftDice.setDisable(false);    //abilita diceboard
                break;
            case DICESCHEMAWHERETOLEAVE:
            case DICESCHEMAWHERETOTAKE:             //TODO: Abilitare solo il tuo
                schemeVBOX.setDisable(false);
            //    schemeVBOX2.setDisable(false);
                break;
            case TRACKBOARDINDEX:
                enableDiceTrackBoard();
                break;
            case VALUE:
                ValueRequest.display(syntaxController);
                break;
            default:
                break;
        }
    }

    private void disableNotYourTurn (){
        extractButton.setDisable(true);
        resetMoveButton.setDisable(true);
        passButton.setDisable(true);
        for(int i = 0; i < diceExtraImgTrackboardList.size(); i++) {
            diceExtraImgTrackboardList.get(i).setDisable(true);
        }
        hBoxDraftDice.setDisable(true);

      //  schemeVBOX.setDisable(true);      //TODO: DISABILITARLI?
      //  schemeVBOX1.setDisable(true);
      //  schemeVBOX2.setDisable(true);
      //  schemeVBOX3.setDisable(true);
    }

    private void enableCommandButton (){
        Platform.runLater(() -> {
                    if (hBoxDraftDice.getChildren().isEmpty()) {
                        extractButton.setDisable(false);
                    }
                });
        //extractButton.setDisable(false);
        resetMoveButton.setDisable(false);
        toolCardButton.setDisable(false);
        passButton.setDisable(false);
    }

    private void enableToolChoiceButton (){
        Platform.runLater(() -> {
            for (int i = 0; i < toolChoiceButtonList.size(); i++) {
                toolChoiceButtonList.get(i).setDisable(false);
                toolChoiceButtonList.get(i).setCursor(Cursor.HAND);
            }
        });
    }

    private void enableDiceTrackBoard(){
        for(int i = 0; i < diceExtraImgTrackboardList.size(); i++) {
            diceExtraImgTrackboardList.get(i).setDisable(false);
        }
    }

    private void nextCommandSequence (TypeOfInputAsked typeOfInputAsked){
        inizMsgAreaError(typeOfInputAsked.getError());
        inizMsgAreaMessage(typeOfInputAsked.getMessage());
        nextCommandType(typeOfInputAsked.getCommandTypeEnum());
    }

    /**
     * Association of the standard color to web color
     * @return hashMap of the association
     */
    private Map colorMap (){
        Map<ColourEnum, String> map = new EnumMap<>(ColourEnum.class);
        map.put(ColourEnum.BLUE, "6599CB");
        map.put(ColourEnum.RED, "FF6666");
        map.put(ColourEnum.GREEN, "66CC66");
        map.put(ColourEnum.YELLOW, "FEFF99");
        map.put(ColourEnum.PURPLE, "CC66CC");

        return map;
    }

    // CONFIG: set delle dimensioni corrette dei pane & binding

    private AnchorPane configAnchorPane (){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinHeight(40);
        anchorPane.setMinWidth(40);
        anchorPane.setPrefHeight(50);
        anchorPane.setPrefWidth(50);
        anchorPane.maxHeight(50);
        anchorPane.maxWidth(50);
        // anchorPane.setPadding(new Insets(4,4,4,4));
        anchorPane.setBorder(new Border(new BorderStroke(Color.web("2C3E50"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));

        return anchorPane;
    }

    private HBox configHBox (){
        HBox infoScheme = new HBox();
        infoScheme.setMinHeight(20);
        infoScheme.setMinWidth(40);
        infoScheme.setPrefHeight(30);
        infoScheme.setPrefWidth(50);
        infoScheme.maxHeight(30);
        infoScheme.maxWidth(50);
        infoScheme.setStyle("-fx-background-color: #2C3E50");
        infoScheme.setAlignment(Pos.CENTER);
        infoScheme.setSpacing(4);

        return infoScheme;
    }

    private ImageView configCellImg (){
        ImageView cellImg = new ImageView();
        cellImg.setPreserveRatio(true); //used for the correct resize of the starting image
        cellImg.maxHeight(45);
        cellImg.setFitWidth(45);
        cellImg.setPreserveRatio(false); //to let the image resize bind to the anchorpane

        return cellImg;
    }

    private void createMsgArea (){
        msgArea = new TextArea();
        msgArea.setEditable(false);
        msgArea.setMouseTransparent(true);
        msgArea.setFocusTraversable(false);
        msgArea.maxHeight(215);
        msgArea.maxWidth(338);
        msgArea.setStyle("-fx-background-color: #808080;"); //TODO: CHECK NOT WORKING
        hBoxMsgArea.getChildren().addAll(msgArea);
    }

    public void setNumTokens(String numPlayerTokens) {
        numTokens.setText(numPlayerTokens);
    }
}
