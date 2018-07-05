package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.socket.SocketTypeClient;
import it.polimi.se2018.controller.client.ClientController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.PlayerSetupper;
import it.polimi.se2018.view.SyntaxController;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.graphic.TypeOfInputAsked;
import it.polimi.se2018.view.graphic.cli.CommandLinePrint;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;


import java.io.File;
import java.net.MalformedURLException;
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

    @FXML
    private HBox hBoxDraftDice;
    @FXML
    private AnchorPane primaryScene;
    @FXML
    private AnchorPane schemePane;
    @FXML
    private ImageView cardImg;
    @FXML
    private HBox publicCardPane;
    @FXML
    private ImageView privateCardImg;
    @FXML
    private HBox privateCardPane;
    @FXML
    private GridPane gridPane;

    @FXML
    private Button extractButton;
    //-------------------------------------------- SCHEME SELECTION -----------------------------

    @FXML AnchorPane backgroundPane;
    @FXML AnchorPane loginPane;

    //-----------
    //VBOX CONTENENTE GLI SCHEMI DEI 4 GIOCATORI DURANTE LA PARTITA

    @FXML
    private VBox schemeVBOX;
    @FXML
    private VBox schemeVBOX1;
    @FXML
    private VBox schemeVBOX2;
    @FXML
    private VBox schemeVBOX3;

    //--------------------------------------------------------------------------------

    @FXML
    private VBox privateCardBox0;

    @FXML
    private AnchorPane schemeSelectionPane;

    @FXML
    private AnchorPane gameboardPane;

    /**
     * Message Area in the table
     */
    private static TextArea msgArea;


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

    PlayerChoice playerChoice;

    @FXML GridPane choiceToolCardGrid;

    //-------------------

    @FXML AnchorPane nicknamePane;
    @FXML
    TextArea nicknameAreaText;

    @FXML GridPane trackBoardGrid;

    //---------------------

    ClientBoard clientBoard;

    @FXML HBox hBoxMsgArea;

    //----------------------

    //scorrimento lettura carte pubbliche e obj privato
    private int indexChangeCard;

    @FXML
    private AnchorPane backPane;

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


        cardSize();

        toolCardPane.setDisable(true);
        schemeSelectionPane.setDisable(true);
        loginPane.setDisable(true);
        nicknamePane.setDisable(true);
        gameboardPane.setDisable(true);


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
        typeOfInputAsked = null;

        msgArea = new TextArea();
        msgArea.setEditable(false);
        msgArea.setMouseTransparent(true);
        msgArea.setFocusTraversable(false);

        msgArea.maxHeight(215);
        msgArea.maxWidth(338);
        hBoxMsgArea.getChildren().addAll(msgArea);

    }


    //TODO: TOGLIERE GIA' SI VISUALIZZANO DAL MENU
    private void showPrivateCards() {
      //  privateCardImg.setImage(showCard((PrivateObjCard) gameLoader.getPrivateObjDeck().extractCard(),
      //          "privateObjCard"));
        //privateCardImg.setPreserveRatio(true);
        //privateCardImg.setFitWidth(200);
        privateCardImg.fitWidthProperty().bind(privateCardPane.widthProperty());
        privateCardImg.fitHeightProperty().bind(privateCardPane.heightProperty().subtract(20));
    }

    private void showPublicCards(ClientBoard clientBoard, int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getPublicObjCardList().get(index), "publicObjCard"));
    }

    private void showToolCards(ClientBoard clientBoard, int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getToolCardList().get(index), "toolCard"));
    }

    private void showNextDieTrackBoard (ClientBoard clientBoard, int index, int indexRound){
        diceExtraImgTrackboardList.get(indexRound).setImage(showDie
                (clientBoard.getTrackBoardDice().getDiceList().get(indexRound).get(index)));
    }

    private void cardSize (){
        cardImg.setPreserveRatio(true);
        cardImg.setFitWidth(200);
        cardImg.fitWidthProperty().bind(publicCardPane.widthProperty());
        cardImg.fitHeightProperty().bind(publicCardPane.heightProperty().subtract(20));
    }


    private Image showCard(Card card, String stringa) {
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\";

        try {
            return new Image(String.valueOf(new File(string + stringa + "\\" +
                    card.getName().replaceAll(" ", "") + ".jpg").toURI().toURL()));
        } catch (MalformedURLException e) {
            // return new MalformedURLException("Public card image not found");
        }
        return null;
    }

    //TODO CHECK STATIC
    static public Image showDie(Die die) {
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\dice\\";

        try {
            return new Image(String.valueOf(new File(string + die.getColour().toString().toLowerCase()
                    + "\\" + die.getValue() + ".jpg").toURI().toURL()));
        } catch (MalformedURLException e) {
            // return new MalformedURLException("Die image not found");
        }
        return null;
    }


    //TODO: INTRODURRE SHOWPRIVATECARD
    @FXML private void nextCard() {
        if (indexChangeCard == 2) {
            indexChangeCard = -1;
        }
        indexChangeCard++;
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
        sendNickname();
    }

    @FXML private void enableNicknamePane (){
        enableBackgroundPane();
        nicknamePane.setOpacity(1);
        nicknamePane.setVisible(true);
        nicknamePane.setDisable(false);
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
        schemeSelectionPane.setOpacity(0);
        schemeSelectionPane.setVisible(false);
        schemeSelectionPane.setDisable(true);
        sendSelectedScheme();

    }

    @FXML private void enableSchemeSelectionPane (List<SchemaCard> schemeToChooseList){
        inizSchemeCardSelection(schemeToChooseList);
        enableBackgroundPane();
        schemeSelectionPane.setOpacity(1);
        schemeSelectionPane.setVisible(true);
        schemeSelectionPane.setDisable(false);
    }

    @FXML private void disableToolCardPane (){
        //confirmToolCardChoice();
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
        inizToolCard();
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
        gameboardPane.setOpacity(1);
        gameboardPane.setVisible(true);
        gameboardPane.setDisable(false);
    }




    // REQUEST: richieste invocate dal GuiInput //

    public void requestSchemeSelection (PlayerChoice playerChoice){
        this.playerChoice=playerChoice;
        TypeOfInputAsked typeOfInputAsked = playerSetupper.newChoiceReceived(playerChoice); //la stringa che ti invio è per scelta scheme

        enableSchemeSelectionPane(playerChoice.getSchemaCardList());
    }

    public void requestYourTurn (ClientBoard clientBoard, PlayerMove playerMove){

        TypeOfInputAsked typeOfInputAsked = syntaxController.newMoveReceived(playerMove, clientBoard);
        this.nextCommandType = typeOfInputAsked.getCommandTypeEnum(); //la mossa richiesta

        //extractButton.setEffect(Shadow);
    }

    public void requestNickname (User user){
        enableNicknamePane();

        TypeOfInputAsked typeOfInputAsked = playerSetupper.newUserReceived(user); //la stringa che ti invio è per il nick dell'user
    }

    //UPDATE
    public void requestShowGameboard (ClientBoard clientBoard){
        this.clientBoard = clientBoard;
        enableGameboardPane();
        inizGameboard(clientBoard);
    }


    //SEND: invio della scelta fatta dall'utente

    public void sendSelectedScheme (){
        System.out.println(selectedButtonScheme.getText());

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


    public void sendNickname (){
        playerSetupper.validNickname(nicknameAreaText.getText());
    }


    //INIZ: chiamati per posizionare le carte nel corrispettivo pane //

    //ADD DINAMICO DEI 4 SCHEMI CHE L'UTENTE DEVE SCEGLIERE
    //TODO: ADD CARTA OBJ PRIVATO
    public void inizSchemeCardSelection(List<SchemaCard> schemeToChooseList){

        Platform.runLater(() -> {
            for(int i = 0; i <5; i++) {
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(50);
                if(i == 0){
                    Text text = new Text("La tua carta obiettivo privato");
                    text.setFill(Color.WHITE);
                    vBox.getChildren().addAll(text);
                }else {
                    Button button = new Button("Schema " + (i));
                    button.setOnAction(event -> {
                        selectedButtonScheme = button;
                        disableSchemeSelectionPane();
                    });

                    vBox.getChildren().addAll(button, createScheme(schemeToChooseList.get(i - 1)));
                }

                schemeSelectionGrid.add(vBox, i, 0);
            }
        });
    }


    public void inizToolCard (){
        for(int i = 0; i < 3; i++) {
            VBox vBox = new VBox();
            ImageView toolImg = new ImageView(showCard(clientBoard.getCardOnBoard().getToolCardList().get(i), "toolCard"));
            toolImg.setPreserveRatio(true);
            toolImg.setFitWidth(300);
            //toolImg.fitWidthProperty().bind(vBox.widthProperty());
            Button button = new Button("ToolCard " + i);

            button.setOnAction(e -> {
                selectedButtonToolCardToUse = button;
                checkToolIndex();
                disableToolCardPane();
            });
            toolChoiceButtonList.add(button);

            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(50);
            vBox.getChildren().addAll(button, toolImg);

            if(i==1){
                Button cancelButton = new Button("Torna al menù");

                cancelButton.setOnAction(e -> disableToolCardPane());
                vBox.getChildren().add(cancelButton);
            }
            choiceToolCardGrid.add(vBox, i,0);
        }
    }


    //TODO: FIX, NON SAI DI CHI E' LO SCHEMA, NUM DI GIOCATORI
    public void inizGameboard (ClientBoard clientBoard){
        Platform.runLater(() -> {

                emptyPanes();

                schemeVBOX.getChildren().add(createScheme(clientBoard.getPlayerList().get(0).getSchemaCard()));
                schemeVBOX2.getChildren().add(createScheme(clientBoard.getPlayerList().get(1).getSchemaCard()));

                System.out.println("..update..");

                showPublicCards(clientBoard,0);
                showDiceBoard();
                insertDiceTrackboard();
        });

      //  schemeVBOX2.getChildren().add(createScheme(schemaCardList.get(2)));
      //  schemeVBOX3.getChildren().add(createScheme(schemaCardList.get(3)));
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
        schemeVBOX2.getChildren().clear();
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
                        (clientBoard.getTrackBoardDice().getDiceList().get(i).get(0))); //TODO: INDEX

                trackBoardGrid.add(diceExtraImgTrackboardList.get(i), 0, 9 - i);
                Button button = new Button(">");
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


    public GridPane createScheme (SchemaCard schemaCard) {
        int col = 0;
        int row = 0;

        GridPane schemeGridPane = new GridPane();
        gridPane.maxHeight(350);
        gridPane.maxWidth(280);
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
                cellImg.setImage(ControllerMatchTable.showDie(c.getDie()));

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
                    cellImg.setImage(ControllerMatchTable.showDie(c.getDie()));
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
        return schemeGridPane;
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
                break;
            case DICEBOARDINDEX: //abilita diceboard
                break;
            case DICESCHEMAWHERETOLEAVE:
            case DICESCHEMAWHERETOTAKE:
                //abilita schema
                break;
            case TRACKBOARDINDEX:
                //abilita track
                break;
            case VALUE:
                ValueRequest.display(syntaxController);
                break;
            default:
                break;
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

    private ImageView configCellImg (){
        ImageView cellImg = new ImageView();
        cellImg.setPreserveRatio(true); //used for the correct resize of the starting image
        cellImg.maxHeight(45);
        cellImg.setFitWidth(45);
        cellImg.setPreserveRatio(false); //to let the image resize bind to the anchorpane

        return cellImg;
    }

    private void setCellValueRestriction (Cell c, ImageView cellImg){
        try {
            Image img = new Image(String.valueOf(new File("src\\main\\java\\it\\polimi\\se2018\\img\\dice\\restriction\\" +
                    c.getValue() + ".jpg").toURI().toURL()));
            cellImg.setOpacity(0.90);
            cellImg.setImage(img);
        }catch (Exception e){}
    }

}
