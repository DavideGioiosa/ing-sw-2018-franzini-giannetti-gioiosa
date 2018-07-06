package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.rmi.RMITypeClient;
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
import javafx.scene.control.RadioButton;
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

    /**
     * list of dice in the DraftPool
     */
    private List<ImageView> diceOnDraftList;

    /**
     * list of cells componing the scheme of the player
     */
    private List<AnchorPane> cellSchemeList;

    /**
     * list of tool's button choice
     */
    private List<Button> toolChoiceButtonList;

    /**
     * list of dice on the trackboard
     */
    private List<ImageView> diceExtraImgTrackboardList;

    /**
     * list of switch trackboard's die button
     */
    private List<Button> buttonTrackBoarIndexList;

    /**
     * list of index of the list of dice on the trackboard in a round
     */
    private List<Integer> indexChangeDiceTrackBoard;

    /**
     * Panes componing gameTable
     */
    @FXML private GridPane schemeSelectionGrid;
    @FXML private AnchorPane toolCardPane;
    @FXML private HBox hBoxDraftDice;
    @FXML private AnchorPane primaryScene;
    @FXML private ImageView cardImg;
    @FXML private HBox publicCardPane;
    @FXML private GridPane gridPane;
    @FXML private GridPane trackBoardGrid;


    /**
     * Buttons on the table
     */
    @FXML private Button extractButton;
    @FXML private Button toolCardButton;
    @FXML private Button resetMoveButton;
    @FXML private Button passButton;


    //-----------

    @FXML private VBox schemeVBOX;
    /**
     * List of the opponents vBox schemes
     */
    private List<VBox> schemeVBoxList;

    /**
     * Main panes of different scenes
     */
    @FXML private AnchorPane schemeSelectionPane;
    @FXML private AnchorPane gameboardPane;
    @FXML private AnchorPane lobbyPane;
    @FXML private AnchorPane scorePane;
    @FXML private AnchorPane backgroundPane;
    @FXML private AnchorPane nicknamePane;

    /**
     * Message Area in the table
     */
    private static TextArea msgArea;
    @FXML private TextArea msgWinnerArea;


    /**
     * Login pane
     */
    @FXML private AnchorPane loginPane;
    @FXML private TextArea ipAreaText;
    @FXML private TextArea portAreaText;
    @FXML private RadioButton socketRadioButton;
    @FXML private RadioButton RMIRadioButton;

    /**
     * Img die selected from the DraftPool
     */
    private ImageView selectedDie;

    /**
     * Button selected for the choice of the toolCard's use
     */
    private Button selectedButtonToolCardToUse;

    /**
     * AnchorPane of the clicked cell
     */
    private AnchorPane cellSelected;

    /**
     * Button pressed of the selection Scheme Pane
     */
    private Button selectedButtonScheme;

    /**
     * Img die selected from the Trackboard
     */
    private ImageView selectedTrackboardDie;

    /**
     * Button on the Trackboard pressed to see the other extra dice
     */
    private Button selectedButtonTrackBoardNext;

    private PlayerChoice playerChoice;

    @FXML private GridPane choiceToolCardGrid;

    @FXML private TextArea nicknameAreaText;

    private ClientBoard clientBoard;

    private ClientModel clientModel;

    @FXML private HBox hBoxMsgArea;
    @FXML private AnchorPane loginAnchorPaneInfo;


    /**
     * index for sliding reading of public card and objPrivateCard
     */
    private int indexChangeCard;

    private View viewGraphic;

    private Client clientGui;

    private ClientController clientControllerGui;

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
        disablePanes();

        viewGraphic = new View();
        GuiInput guiInput = (GuiInput) viewGraphic.getInputStrategy();
        guiInput.setControllerMatchTable(this);
        GuiOutput guiOutput = (GuiOutput) viewGraphic.getOutputStrategy();
        guiOutput.setControllerMatchTable(this);
        playerSetupper = viewGraphic.getInputStrategy().getPlayerSetupper();
        syntaxController = viewGraphic.getInputStrategy().getSyntaxController();

        arrayCreation();
        typeOfInputAsked = null;
        createMsgArea();

        enableLoginPane(); //first pane

    }


    private void showPrivateCards() {
        cardImg.setImage(showCard(clientModel.getPrivateObjCard(), "privateObjCard"));
    }

    private void showPublicCards(ClientBoard clientBoard, int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getPublicObjCardList().get(index), "publicObjCard"));
    }

    private void showNextDieTrackBoard (int index, int indexRound){
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

    /**
     * Creates image of the card depending on the its characteristics, through the path in resources
     * @param card from which to take the img
     * @param string type of Card
     * @return image of the card requested
     */
    private Image showCard(Card card, String string) {
        String path = "it/polimi/se2018/view/graphic/gui/img/" + string + "/" + card.getName().replaceAll(" ", "") + ".jpg" ;

        return new Image(path);

    }

    /**
     * Creates image of the die depending on the its characteristics, through the path in resources
     * @param die from which to take the img
     * @return image of the die requested
     */
     private Image showDie(Die die) {
        String string = "it/polimi/se2018/view/graphic/gui/img/dice/" +
                die.getColour().toString().toLowerCase() + "/" + die.getValue() + ".jpg";

        return new Image(string);
    }

    /**
     * Switches public and private Card on the gameboard
     */
    @FXML private void nextCard() {
        if (indexChangeCard == 3) {
            indexChangeCard = -1;
        }
        indexChangeCard++;
        if(indexChangeCard == 3){
            showPrivateCards();
        }
        else {
            showPublicCards(clientBoard, indexChangeCard);
        }
    }

    /**
     * Switches dice on the trackboard in a Round
     * @param selectedButtonIndex index of what button of the Trackboard's one is pressed
     * @param numExtraDiceRound num of dice in the Trackboard in that round
     */
    @FXML private void nextDieTrackboard(int selectedButtonIndex, int numExtraDiceRound) {
        if (indexChangeDiceTrackBoard.get(selectedButtonIndex) == numExtraDiceRound - 1) {
            indexChangeDiceTrackBoard.remove(selectedButtonIndex);
            indexChangeDiceTrackBoard.add(selectedButtonIndex, -1);
        }
        int indexValue = indexChangeDiceTrackBoard.get(selectedButtonIndex);
        indexChangeDiceTrackBoard.remove(selectedButtonIndex);
        indexChangeDiceTrackBoard.add(selectedButtonIndex, (indexValue + 1));

        showNextDieTrackBoard(indexValue + 1, selectedButtonIndex);
    }

    /**
     * Command move 'extract'
     */
    @FXML private void extractClick() {
        syntaxController.validCommand("extract");
    }

    /**
     * Command move 'pass'
     */
    @FXML private void passClick () {
        syntaxController.validCommand("pass");
    }

    /**
     * Command move 'cancel'
     */
    @FXML private void cancelClick () {
        syntaxController.validCommand("cancel");
    }

    /**
     * Show dice on the DraftPool
     */
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

    /**
     * Enabling and disabling of the pane
     */

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
        if(socketRadioButton.isSelected() || RMIRadioButton.isSelected()) {
            disableBackgroundPane();
            loginPane.setOpacity(0);
            loginPane.setVisible(false);
            loginPane.setDisable(true);
            createNetworking();
        }
        else{
            Text text = new Text("*Non hai compilato tutto i campi richiesti");
            text.setFill(Color.WHITE);
            loginAnchorPaneInfo.getChildren().addAll(text);
        }
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

    @FXML private void enableScorePane (){
        scorePane.setOpacity(1);
        scorePane.setVisible(true);
        scorePane.setDisable(false);
    }


    /**
     * Creates the correct client depending on the type of Connection chosen by the user
     */
    private void createNetworking (){

        if(socketRadioButton.isSelected())
            clientGui = new Client(new SocketTypeClient(ipAreaText.getText(), Integer.parseInt(portAreaText.getText())),
                    viewGraphic);
        else if (RMIRadioButton.isSelected()){
            clientGui = new Client (new RMITypeClient(ipAreaText.getText(), Integer.parseInt(portAreaText.getText())),
                    viewGraphic);
        }
        clientControllerGui = new ClientController(clientGui, viewGraphic);

        viewGraphic.getInputStrategy().getPlayerSetupper().addObserver(clientControllerGui);
        viewGraphic.getInputStrategy().getSyntaxController().addObserver(clientControllerGui);
        clientGui.connect();
    }


    /**
     * Requests invokated by the GuiInput
     */

    public void requestSchemeSelection (PlayerChoice playerChoice){
        this.playerChoice=playerChoice;
        playerSetupper.newChoiceReceived(playerChoice); //string of request scheme selection

        enableSchemeSelectionPane(playerChoice.getSchemaCardList(), playerChoice.getPrivateObjCard());
    }

    public void requestYourTurn (ClientBoard clientBoard, PlayerMove playerMove){
        enableCommandButton();
        enableToolChoiceButton();
        hBoxDraftDice.setDisable(false);

        typeOfInputAsked = syntaxController.newMoveReceived(playerMove, clientBoard);
        this.nextCommandType = typeOfInputAsked.getCommandTypeEnum(); //move requested
    }

    public void requestNickname (User user){
        enableNicknamePane();

        playerSetupper.newUserReceived(user); //nickname string
    }

    public void requestLogin (){
        enableLoginPane();
    }


    /**
     * Update of the current situation of the game Table
     * @param clientModel contains all the updates
     */
    public void requestShowGameboard (ClientModel clientModel){
        this.clientBoard = clientModel.getClientBoard();
        this.clientModel = clientModel;
        disableNotYourTurn();
        enableGameboardPane();
        inizGameboard(clientBoard);
    }

    public void requestShowScore (List<Player> playerList){
        enableScorePane();
        String string = "";
        String winner = "Nessuno";
        int maxScore = 0;
        for (int i = 0; i <playerList.size(); i++) {
            string = (playerList.size()-i) + ". " + playerList.get(i).getNickname() + " " + playerList.get(i).getScore() + "\n" + string ;

            if (playerList.get(i).getScore() > maxScore){
                maxScore = playerList.get(i).getScore();
                winner = playerList.get(i).getNickname();
            }
        }
        msgWinnerArea.setText("La partita è terminata.\nLa classifica è:\n\n" + string +
                "\nIl vincitore è :\n" + winner);
        msgWinnerArea.setStyle("-fx-font-size: 24");
    }


    //SEND:send of the player choice

    /**
     * Send's the scheme selected which the player wants to player
     */
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

    /**
     * Sends player's nickname
     */
    private void sendNickname (){
        playerSetupper.validNickname(nicknameAreaText.getText());
    }


    /**
     * Dynamic creation of 4 scheme, the player will choose which one he wants to use
     * @param schemeToChooseList list of the 4 schemes
     * @param privateObjCard privateCard that belongs to the player
     */
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

    /**
     * Allocates dynamically toolCard's extracted
     */
    private void inizToolCard (){
        for(int i = 0; i < 3; i++) {
            VBox vBox = new VBox();
            ImageView toolImg = new ImageView(showCard(clientBoard.getCardOnBoard().getToolCardList().get(i), "toolCard"));
            toolImg.setPreserveRatio(true);
            toolImg.setFitWidth(300);

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

    /**
     * Updates the current state of the game Table
     * @param clientBoard contains all the information about the current state of the game Table
     */
    private void inizGameboard (ClientBoard clientBoard){
        Platform.runLater(() -> {

                emptyPanes();
                schemeVBOX.getChildren().add(createScheme(clientModel.getActualPlayer().getSchemaCard()));
                HBox hBoxActualPl = new HBox();
                hBoxActualPl.setAlignment(Pos.CENTER);
                Text numTokenActualPl = new Text("Tokens disponibili : ");
                numTokenActualPl.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
                Text nicknameMe = new Text ("  " +clientModel.getActualPlayer().getNickname());
                nicknameMe.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));


                hBoxActualPl.getChildren().addAll(numTokenActualPl);

                for(int j = 0; j < clientModel.getActualPlayer().getTokens(); j++){
                    Circle circle = createTokens();
                    hBoxActualPl.getChildren().add(circle);
                 }

                 hBoxActualPl.getChildren().addAll(nicknameMe);
                 schemeVBOX.getChildren().addAll(hBoxActualPl);

                int i;
                for(i = 0; i < clientBoard.getPlayerList().size(); i++) {
                    VBox vBox = new VBox();
                    vBox.setPadding(new Insets(10,60,0,60));
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER);
                    Text numTokenPl = new Text("Tokens disponibili : ");
                    numTokenPl.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
                    hBox.getChildren().addAll(numTokenPl);

                    for(int j = 0; j < clientBoard.getPlayerList().get(i).getTokens(); j++){
                        Circle circle = createTokens();
                        hBox.getChildren().add(circle);
                    }
                    Text nicknameText = new Text ("  " +clientBoard.getPlayerList().get(i).getNickname());
                    nicknameText.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
                    hBox.getChildren().add(nicknameText);
                    vBox.getChildren().addAll(createScheme(clientBoard.getPlayerList().get(i).getSchemaCard()), hBox);
                    vBox.prefWidthProperty().bind(schemeVBOX.widthProperty());
                    vBox.prefHeightProperty().bind(schemeVBOX.heightProperty());
                    schemeVBoxList.add(vBox);

                    if(i == 2) {
                        gridPane.add(vBox, 0, 0);
                    }
                    else if(i == 3){}
                    else {
                        gridPane.add(vBox, i + 1, 0);
                    }
                }

                System.out.println("..update..");
                inizToolCard();
            for (Button aToolChoiceButtonList : toolChoiceButtonList) {
                aToolChoiceButtonList.setDisable(true);
            }
                showPublicCards(clientBoard,0);
                showDiceBoard();
                insertDiceTrackboard();
        });
    }

    public static void inizMsgAreaError (int id){
        if(id != 0) {
            String string = msgArea.getText();
            msgArea.setText("ERROR: " + CommandLinePrint.getErrorMap().get(id) + "\n" + string);        }
    }

    public static void inizMsgAreaMessage (int id){
        if(id != 0) {
            String string = msgArea.getText();
            msgArea.setText("Message: " + CommandLinePrint.getMessageMap().get(id) + "\n" + string);        }
    }

    /**
     * Empty panels to prepare them for the next update
     */
    private void emptyPanes(){
        schemeVBOX.getChildren().clear();
        for (int i = 0; i < schemeVBoxList.size(); i++){
            schemeVBoxList.get(i).getChildren().clear();
        }
        schemeVBoxList.clear();

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

                    int buttonTBIndex = checkButtonTrackBoardIndex();
                    if(buttonTBIndex != -1) {
                        nextDieTrackboard(buttonTBIndex,
                                clientBoard.getTrackBoardDice().getDiceList().get(buttonTBIndex).size());
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

    /**
     * Creates a scheme Card dynamically
     * @param schemaCard to compone in vectorial graphic
     * @return gridpane of the scheme
     */
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
                Circle circle = createTokens();
                infoScheme.getChildren().add(circle);
            }

            schemeGridPane.add(infoScheme, 0, row + 1);
            schemeGridPane.setColumnSpan(infoScheme, 5);

            schemeGridPane.setBorder(new Border(new BorderStroke(Color.web("2C3E50"),
                BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths (0))));


        return schemeGridPane;
    }

    /**
     * Sets the value restriction image of a die during the construction of a scheme Card
     * @param c cell with value restriction
     * @param cellImg imageview to be filled
     */
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

    /**
     * Checks which useToolCard button is selected and sends it to the syntaxController
     */
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

    /**
     * Checks which draftPool die is selected and sends it to the syntaxController
     */
    private void checkDiceBoardIndex(){
        syntaxController.validCommand("pick");

        for (int i = 0; i < clientBoard.getBoardDice().getDieList().size(); i++) {
            if(diceOnDraftList.get(i) == selectedDie){
                typeOfInputAsked = syntaxController.validCommand(((Integer) i).toString());
                nextCommandSequence(typeOfInputAsked);
                return;
            }
        }
    }

    /**
     * Checks Trackboard die is selected and sends it to the syntaxController
     */
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

    /**
     * Checks which nextDieTrackboard button is selected and switch the die to the next one
     */
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
            case DICEBOARDINDEX:
                hBoxDraftDice.setDisable(false);    //abilita diceboard
                break;
            case DICESCHEMAWHERETOLEAVE:
            case DICESCHEMAWHERETOTAKE:
                schemeVBOX.setDisable(false);
                break;
            case VALUE:
                ValueRequest.display(syntaxController);
                break;
            case TRACKBOARDINDEX:
                enableDiceTrackBoard();
                break;
            default:
                break;
        }
    }

    /**
     * Disabling of the operation that a player can't do when it isn't his turn
     */
    private void disableNotYourTurn (){
        extractButton.setDisable(true);
        resetMoveButton.setDisable(true);
        passButton.setDisable(true);
        for (ImageView aDiceExtraImgTrackboardList : diceExtraImgTrackboardList) {
            aDiceExtraImgTrackboardList.setDisable(true);
        }
        hBoxDraftDice.setDisable(true);

    }

    /**
     * Enables command buttons on the gameBoard
     */
    private void enableCommandButton (){
        Platform.runLater(() -> {
                    if (hBoxDraftDice.getChildren().isEmpty()) {
                        extractButton.setDisable(false);
                    }
                });
        resetMoveButton.setDisable(false);
        toolCardButton.setDisable(false);
        passButton.setDisable(false);
    }

    /**
     * Enables tool choice buttons
     */
    private void enableToolChoiceButton (){
        Platform.runLater(() -> {
            for (Button aToolChoiceButtonList : toolChoiceButtonList) {
                aToolChoiceButtonList.setDisable(false);
                aToolChoiceButtonList.setCursor(Cursor.HAND);
            }
        });
    }

    /**
     * Enables dice on Trackboard
     */
    private void enableDiceTrackBoard(){
        for (ImageView aDiceExtraImgTrackboardList : diceExtraImgTrackboardList) {
            aDiceExtraImgTrackboardList.setDisable(false);
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

    // CONFIG: set of correct size of pane & binding

    /**
     * Configuration of an AnchorPane of the size needed
     * @return the anchorPane created
     */
    private AnchorPane configAnchorPane (){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinHeight(40);
        anchorPane.setMinWidth(40);
        anchorPane.setPrefHeight(50);
        anchorPane.setPrefWidth(50);
        anchorPane.maxHeight(50);
        anchorPane.maxWidth(50);
        anchorPane.setBorder(new Border(new BorderStroke(Color.web("2C3E50"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));

        return anchorPane;
    }

    /**
     * Configuration of an HBox of the size needed
     * @return the HBox created
     */
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

    /**
     * Configuration of an ImageView of the size needed
     * @return the ImageView created
     */
    private ImageView configCellImg (){
        ImageView cellImg = new ImageView();
        cellImg.setPreserveRatio(true); //used for the correct resize of the starting image
        cellImg.maxHeight(45);
        cellImg.setFitWidth(45);
        cellImg.setPreserveRatio(false); //to let the image resize bind to the anchorpane

        return cellImg;
    }

    /**
     * Configuration of a TextArea of the size needed
     */
    private void createMsgArea (){
        msgArea = new TextArea();
        msgArea.setEditable(false);
        msgArea.setMouseTransparent(true);
        msgArea.setFocusTraversable(false);
        msgArea.maxHeight(215);
        msgArea.maxWidth(338);
        msgArea.setStyle("-fx-background-color: #808080;");
        hBoxMsgArea.getChildren().addAll(msgArea);
    }



    /**
     * Disables all the panes that compose the Scene
     */
    private void disablePanes (){
        toolCardPane.setDisable(true);
        schemeSelectionPane.setDisable(true);
        loginPane.setDisable(true);
        nicknamePane.setDisable(true);
        gameboardPane.setDisable(true);
        lobbyPane.setDisable(true);
        scorePane.setDisable(true);
    }

    /**
     * Allocates arraylist needed to group elements of the FXML create dynamically
     */
    private void arrayCreation (){
        cellSchemeList = new ArrayList<>();
        diceOnDraftList = new ArrayList();
        toolChoiceButtonList = new ArrayList<>();
        diceExtraImgTrackboardList = new ArrayList<>();
        buttonTrackBoarIndexList = new ArrayList<>();
        indexChangeDiceTrackBoard = new ArrayList<>();
        schemeVBoxList = new ArrayList<>();
    }

    /**
     * Creates the number of token of the player in the current turn
     */
    private Circle createTokens (){
        Circle circle = new Circle(5, 5, 4);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);
        circle.setFill(Color.web("#2C3E50"));

        return circle;
    }

}
