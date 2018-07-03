package it.polimi.se2018.view.graphic.gui;

import it.polimi.se2018.connection.client.Client;
import it.polimi.se2018.connection.client.socket.SocketTypeClient;
import it.polimi.se2018.controller.GameLoader;
import it.polimi.se2018.controller.client.ClientController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
import it.polimi.se2018.model.player.TypeOfConnection;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.PlayerSetupper;
import it.polimi.se2018.view.SyntaxController;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.graphic.TypeOfInputAsked;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;


import java.io.File;
import java.io.IOException;
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

    private List<ImageView> dice;

    @FXML
    private HBox hbox;
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


    //-------------------------------------------- SCHEME SELECTION -----------------------------

    @FXML
    private VBox schemeVBOX;
    @FXML
    private VBox schemeVBOX1;
    @FXML
    private VBox schemeVBOX2;
    @FXML
    private VBox schemeVBOX3;

    @FXML
    private VBox choiceSchemeBox1;

    @FXML
    private VBox choiceSchemeBox2;

    @FXML
    private VBox choiceSchemeBox3;

    @FXML
    private VBox choiceSchemeBox4;

    @FXML
    private VBox privateCardBox0;

    @FXML
    private AnchorPane schemeSelectionPane;

    @FXML
    private AnchorPane gameboardPane;

    //-----------------------------
   // @FXML VBox useToolBox1;

   // @FXML VBox useToolBox2;

   // @FXML VBox useToolBox3;
    PlayerChoice playerChoice;

    @FXML GridPane choiceToolCardGrid;

    //-------------------

    @FXML AnchorPane nicknamePane;
    @FXML
    TextArea nicknameAreaText;

    //---------------------

    @FXML RadioButton buttonSchemeChosen1;
    @FXML RadioButton buttonSchemeChosen2;
    @FXML RadioButton buttonSchemeChosen3;
    @FXML RadioButton buttonSchemeChosen4;

    //scorrimento lettura carte pubbliche e tool
    private int indexChangeCard;

    private GameLoader gameLoader;
    private final String NICKNAME = "Nickname";
    private final ColourEnum FRAMECOLOUR = ColourEnum.BLUE;
    private final boolean CONNECTION = true;
    private SchemaCard schemaCard;
    private Player player;
    private List<Player> playerList;
    private ToolCard toolCard;
    private List<ToolCard> toolCardList;
    private PublicObjCard publicObjCard;
    private List<PublicObjCard> publicCardList;
    private BoardDice boardDice;
    private TrackBoard trackBoardDice;
    private BagDice bagDice;
    private BoardCard boardCard;
    private PrivateObjCard privateObjCard;
    private List<PrivatePlayer> privatePlayerList;
    private ClientBoard clientBoard;

    List<SchemaCard> schemaCardList;

    ToggleGroup selectionSchemeButtonGroup;

    @FXML
    private AnchorPane backPane;


    View viewSocket;

    Client clientSocket;

    ClientController clientControllerSocket;

    PlayerSetupper playerSetupper;

    SyntaxController syntaxController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();
        primaryScene.setPrefWidth(width);
        primaryScene.setPrefHeight(height);


        //------------------------------------------------------------------------------------------------

        playerList = new ArrayList<>();
        playerList.add(new Player("Player1", CONNECTION, FRAMECOLOUR, (SchemaCard) gameLoader.getSchemaDeck().extractCard(), 3));
        playerList.add(new Player("Player2", CONNECTION, FRAMECOLOUR, (SchemaCard) gameLoader.getSchemaDeck().extractCard(), 2));
        playerList.add(new Player("Player3", CONNECTION, FRAMECOLOUR, (SchemaCard) gameLoader.getSchemaDeck().extractCard(), 1));
        playerList.add(new Player("Player4", CONNECTION, FRAMECOLOUR, (SchemaCard) gameLoader.getSchemaDeck().extractCard(), 5));

        publicCardList = new ArrayList<>();
        publicCardList.add((PublicObjCard) gameLoader.getPublicObjDeck().extractCard());
        publicCardList.add((PublicObjCard) gameLoader.getPublicObjDeck().extractCard());
        publicCardList.add((PublicObjCard) gameLoader.getPublicObjDeck().extractCard());

        toolCardList = new ArrayList<>();
        toolCardList.add((ToolCard) gameLoader.getToolDeck().extractCard());
        toolCardList.add((ToolCard) gameLoader.getToolDeck().extractCard());
        toolCardList.add((ToolCard) gameLoader.getToolDeck().extractCard());

        boardDice = new BoardDice();
        trackBoardDice = new TrackBoard();

        for (int i = 0; i < 9; i++) {
            Die die = new Die(ColourEnum.RED);
            die.firstRoll();
            boardDice.insertDie(die);
        }
        trackBoardDice = new TrackBoard();

        for (int i = 0; i < 10; i++) {
            List<Die> dieList = new ArrayList<>();
            Die die = new Die(ColourEnum.BLUE);
            die.firstRoll();
            dieList.add(die);
            Die die1 = new Die(ColourEnum.YELLOW);
            die1.firstRoll();
            dieList.add(die1);
            trackBoardDice.insertDice(dieList);
        }

        boardCard = new BoardCard(publicCardList, toolCardList);
        clientBoard = new ClientBoard(playerList, boardDice, trackBoardDice, boardCard);

        PlayerChoice playerChoice = new PlayerChoice(new User(TypeOfConnection.SOCKET));
        schemaCardList = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            schemaCardList.add((SchemaCard) gameLoader.getSchemaDeck().extractCard());
        }

        playerChoice.setSchemaCardList(schemaCardList);
        //-------------------------------------------------------------------------------------------------------


        cardSize();
        // showPrivateCards();
        showPublicCards(0);

        //gridPane.add(createScheme(), 1,2);

        toolCardPane.setDisable(true);
        schemeSelectionPane.setDisable(true);
        loginPane.setDisable(true);
        nicknamePane.setDisable(true);
        gameboardPane.setDisable(true);


        viewSocket = new View();
        GuiInput guiInput = (GuiInput) viewSocket.getInputStrategy();
        guiInput.setControllerMatchTable(this);
        clientSocket = new Client(new SocketTypeClient("localhost", 1111), viewSocket);
        playerSetupper = viewSocket.getInputStrategy().getPlayerSetupper();
        syntaxController = viewSocket.getInputStrategy().getSyntaxController();
        clientControllerSocket = new ClientController(clientSocket, viewSocket);

        viewSocket.getInputStrategy().getPlayerSetupper().addObserver(clientControllerSocket);
        viewSocket.getInputStrategy().getSyntaxController().addObserver(clientControllerSocket);
        clientSocket.connect();

        selectionSchemeButtonGroup = new ToggleGroup();
        buttonSchemeChosen1.setToggleGroup(selectionSchemeButtonGroup);
        buttonSchemeChosen2.setToggleGroup(selectionSchemeButtonGroup);
        buttonSchemeChosen3.setToggleGroup(selectionSchemeButtonGroup);
        buttonSchemeChosen4.setToggleGroup(selectionSchemeButtonGroup);


        enableLoginPane();


        dice = new ArrayList();

        //AnchorPane anchorPane = showSchemeSelection();
        //primaryScene.getChildren().add(anchorPane);
    }

    @FXML
    HBox hboxScheme;

    public AnchorPane loadSchema() {
        try {
            URL url = new File("src\\main\\java\\it\\polimi\\se2018\\view\\graphic\\gui\\SchemeCard.fxml").toURI().toURL();
            AnchorPane schemeCardMatrix = FXMLLoader.load(url);

            //gridPane.setAlignment(Pos.CENTER);
            return schemeCardMatrix;
        } catch (Exception e) {
        }
        return null;
    }

    //TODO: TOGLIERE
    private void showPrivateCards() {
        privateCardImg.setImage(showCard((PrivateObjCard) gameLoader.getPrivateObjDeck().extractCard(),
                "privateObjCard"));
        //privateCardImg.setPreserveRatio(true);
        //privateCardImg.setFitWidth(200);
        privateCardImg.fitWidthProperty().bind(privateCardPane.widthProperty());
        privateCardImg.fitHeightProperty().bind(privateCardPane.heightProperty().subtract(20));
    }

    private void showPublicCards(int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getPublicObjCardList().get(index), "publicObjCard"));
    }

    private void showToolCards(int index) {
        cardImg.setImage(showCard(clientBoard.getCardOnBoard().getToolCardList().get(index), "toolCard"));
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


    //INTRODURRE SHOWPRIVATECARD
    public void nextCard(ActionEvent actionEvent) throws IOException {
        if (indexChangeCard == 2) {
            indexChangeCard = -1;
        }
        indexChangeCard++;
        showPublicCards(indexChangeCard);
    }

    /**
     * TODO: POSSIBILE SOLO AL PRIMO PLAYER DEL ROUND
     * Boolean isClicked
     */
    public void extractClick(ActionEvent actionEvent) throws IOException {

        for (int i = 0; i < clientBoard.getPlayerList().size() * 2 + 1; i++) {
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
            dice.get(i).setImage(showDie(clientBoard.getBoardDice().getDieList().get(i)));

            hbox.getChildren().add(dice.get(i));
            die.fitWidthProperty().bind(hbox.widthProperty());
            die.fitHeightProperty().bind(hbox.heightProperty().subtract(20));
        }
    }

    //TODO: TOGLIERE???
    @FXML
    public void showScheme(ActionEvent event) {
        //primaryScene.getChildren().add(showSchemeSelection());
    }


    @FXML AnchorPane backgroundPane;
    @FXML AnchorPane loginPane;

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
        sendNickname();

        disableBackgroundPane();
        nicknamePane.setOpacity(0);
        nicknamePane.setVisible(false);
        nicknamePane.setDisable(true);


        enableBackgroundPane(); //---------------------------------------------------
    }

    public void sendNickname (){
        playerSetupper.validNickname(nicknameAreaText.getText());
    }

    public void requestNickname (User user){
        enableNicknamePane();

        TypeOfInputAsked typeOfInputAsked = playerSetupper.newUserReceived(user); //la stringa che ti invio è per il nick dell'user
    }


    @FXML private void enableNicknamePane (){
        enableBackgroundPane();
        nicknamePane.setOpacity(1);
        nicknamePane.setVisible(true);
        nicknamePane.setDisable(false);
    }

    @FXML private void disableLoginPane (){
        disableBackgroundPane();
        loginPane.setOpacity(0);
        loginPane.setVisible(false);
        loginPane.setDisable(true);

        //dà l'indirizzo IP inserito

        enableNicknamePane();

    }

    @FXML private void enableLoginPane (){
        enableBackgroundPane();
        loginPane.setOpacity(1);
        loginPane.setVisible(true);
        loginPane.setDisable(false);
    }

   // @FXML private AnchorPane schemeSelectionPane;

    @FXML private void disableSchemeSelectionPane (){
        sendSelectedScheme();

        disableBackgroundPane();
        schemeSelectionPane.setOpacity(0);
        schemeSelectionPane.setVisible(false);
        schemeSelectionPane.setDisable(true);
    }

    @FXML private void enableSchemeSelectionPane (List<SchemaCard> schemeToChooseList){
        inizSchemeCardSelection(schemeToChooseList);
        enableBackgroundPane();
        schemeSelectionPane.setOpacity(1);
        schemeSelectionPane.setVisible(true);
        schemeSelectionPane.setDisable(false);
    }

    public void sendSelectedScheme (){

        playerSetupper.validCommand(((Integer)playerChoice.getSchemaCardList().get(0).getId()).toString());

         if(buttonSchemeChosen2.isPressed()){
            playerSetupper.validCommand(((Integer)playerChoice.getSchemaCardList().get(1).getId()).toString());
        }
        else if(buttonSchemeChosen3.isPressed()){
            playerSetupper.validCommand(((Integer)playerChoice.getSchemaCardList().get(2).getId()).toString());
        }
        else if(buttonSchemeChosen4.isPressed()){
            playerSetupper.validCommand(((Integer)playerChoice.getSchemaCardList().get(3).getId()).toString());
        }
    }

    public void requestSchemeSelection (PlayerChoice playerChoice){
        this.playerChoice=playerChoice;
        TypeOfInputAsked typeOfInputAsked = playerSetupper.newChoiceReceived(playerChoice); //la stringa che ti invio è per scelta scheme

        enableSchemeSelectionPane(playerChoice.getSchemaCardList());
    }

    public void requestYourTurn (ClientBoard clientBoard, PlayerMove playerMove){
        TypeOfInputAsked typeOfInputAsked = syntaxController.newMoveReceived(playerMove, clientBoard);
        CommandTypeEnum nextCommandType = typeOfInputAsked.getCommandTypeEnum();


        enableGameboardPane(clientBoard, playerMove);
    }


    @FXML private AnchorPane toolCardPane;

    @FXML private void disableToolCardPane (){
        //confirmSchemeChoice();
        disableBackgroundPane();
        toolCardPane.setOpacity(0);
        toolCardPane.setVisible(false);
        toolCardPane.setDisable(true);

        //dà i dati della toolcard scelta
    }

    @FXML private void enableToolCardPane (){
        inizToolCard();
        enableBackgroundPane();
        toolCardPane.setOpacity(1);
        toolCardPane.setVisible(true);
        toolCardPane.setDisable(false);
    }

    @FXML private void disableGameboardPane (){

        gameboardPane.setOpacity(0);
        gameboardPane.setVisible(false);
        gameboardPane.setDisable(true);

        //dà i dati della toolcard scelta
    }

    @FXML private void enableGameboardPane (ClientBoard clientBoard, PlayerMove playerMove){
        disableBackgroundPane();
        inizGameboard(clientBoard, playerMove);
        gameboardPane.setOpacity(1);
        gameboardPane.setVisible(true);
        gameboardPane.setDisable(false);
    }

    //TODO: FIX, NON SAI DI CHI E' LO SCHEMO
    public void inizGameboard (ClientBoard clientBoard, PlayerMove playerMove){
        Platform.runLater(() -> {
                    schemeVBOX.getChildren().add(createScheme(clientBoard.getPlayerList().get(0).getSchemaCard()));
                    schemeVBOX2.getChildren().add(createScheme(clientBoard.getPlayerList().get(1).getSchemaCard()));
                });

      //  schemeVBOX2.getChildren().add(createScheme(schemaCardList.get(2)));
      //  schemeVBOX3.getChildren().add(createScheme(schemaCardList.get(3)));
    }

    //------------------------------------------------------------------------------------

    public GridPane createScheme (SchemaCard schemaCard) {
        int col = 0;
        int row = 0;

        GridPane schemeGridPane = new GridPane();
        gridPane.maxHeight(350);
        gridPane.maxWidth(280);
        gridPane.setGridLinesVisible(true);


        for (Cell c : schemaCard.getCellList()) {
            ImageView cellImg = configCellImg();
            AnchorPane anchorPane = configAnchorPane();

            if (c.getValue() == 0 && c.getColour() == null) {
                anchorPane.setStyle("-fx-background-color: white");
            }
            else if (c.getColour() != null) {
                Map<ColourEnum, String> map = colorMap();
                anchorPane.setStyle("-fx-background-color: #" + map.get(c.getColour()));

            }
            else {
                setCellValueRestriction(c, cellImg);
            }

            if(!c.isEmpty()){
                cellImg.setImage(ControllerMatchTable.showDie(c.getDie()));

                cellImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(c.getValue() != 0){
                            setCellValueRestriction(c, cellImg);
                        }else {
                            cellImg.setImage(null);
                        }
                        event.consume();
                    }
                });

                cellImg.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        cellImg.setImage(ControllerMatchTable.showDie(c.getDie()));
                        event.consume();
                    }
                });
            }

            if (col == 5) {
                col = 0;
                row++;
            }

            anchorPane.getChildren().addAll(cellImg);
            AnchorPane.setTopAnchor(cellImg, 0.6);
            AnchorPane.setLeftAnchor(cellImg, 0.3);
            cellImg.fitHeightProperty().bind(anchorPane.heightProperty().subtract(7));
            cellImg.fitWidthProperty().bind(anchorPane.widthProperty().subtract(7));
            schemeGridPane.add(anchorPane, col, row);
            col++;
        }
        return schemeGridPane;
    }

    /**
     * Association of the stardard color to web color
     * @return
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

    public void inizSchemeCardSelection(List<SchemaCard> schemeToChooseList){
        Button button = new Button("A");
        Platform.runLater(() -> {
            choiceSchemeBox1.getChildren().add(createScheme(schemeToChooseList.get(0)));
            choiceSchemeBox2.getChildren().add(createScheme(schemeToChooseList.get(1)));
            choiceSchemeBox3.getChildren().add(createScheme(schemeToChooseList.get(2)));
            choiceSchemeBox4.getChildren().add(createScheme(schemeToChooseList.get(3)));
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

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    disableToolCardPane();
                }
            });

            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(50);
            vBox.getChildren().addAll(button, toolImg);

            if(i==1){
                Button cancelButton = new Button("Torna al menù");

                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        disableToolCardPane();
                    }
                });
                vBox.getChildren().add(cancelButton);
            }
            choiceToolCardGrid.add(vBox, i,0);
        }
    }

    @FXML
    void confirmSchemeChoice(ActionEvent event) {
        //return schemaCardList.get(index).getId();
    }

}
