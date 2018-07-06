package it.polimi.se2018.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.se2018.model.CardTypeEnum;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.publiccard.PublicObjCard;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static it.polimi.se2018.model.Config.*;
import static java.lang.Integer.valueOf;


/**
 * Creates Public Objective, Private Objective and Tool Cards and loads Schema Cards
 *
 * @author Cristian Giannetti
 */
public class GameLoader {
    /**
     * Deck with Private Objective Cards
     */
    private CardDeck privateObjDeck;
    /**
     * Deck with Public Objective Cards
     */
    private CardDeck publicObjDeck;
    /**
     * Deck with Schema Cards
     */
    private CardDeck schemaDeck;
    /**
     * Deck with Tool Cards
     */
    private CardDeck toolDeck;
    /**
     * List of Frame Colour
     */
    private List<ColourEnum> windowFrame;
    /**
     * Name of Name tag in XML file
     */
    private static final String NAME_TAG = "name";
    /**
     * Name of Description tag in XML file
     */
    private static final String DESCRIPTION_TAG = "description";

    private static final String FILE_ERROR_MESSAGE = "FILE ERROR";

    /**
     * The constructor creates all type of Deck and the list of frame colour
     */
    public GameLoader(){
        createPrivateObjDeck();
        createPublicObjDeck();
        createSchemaCardDeck();
        createToolDeck();
        createWindowFrame();
    }

    /**
     * Gets the list of Window Frame colours
     * @return List of Window Frame colours
     */
    public List<ColourEnum> getWindowFrame() {
        return windowFrame;
    }

    /**
     * Gets the Deck of Private Objective Cards
     * @return Deck of Private Objective Cards
     */
    public CardDeck getPrivateObjDeck() {
        return privateObjDeck;
    }

    /**
     * Gets the Deck of Public Objective Cards
     * @return Deck of Public Objective Cards
     */
    public CardDeck getPublicObjDeck() {
        return publicObjDeck;
    }

    /**
     * Gets the Deck of Schema Cards
     * @return Deck of Schema Cards
     */
    public CardDeck getSchemaDeck() {
        return schemaDeck;
    }

    /**
     * Gets the Deck of Tool Cards
     * @return Deck of Tool Cards
     */
    public CardDeck getToolDeck() {
        return toolDeck;
    }

    /**
     * Creates Private Objective Cards Deck
     */
    private void createPrivateObjDeck(){

        String cardTag = "privateObjCard";
        int id = ID_FIRST_PRIVATE_OBJ_CARD;
        String stringId;

        final String pathName = "private";

        try {
            privateObjDeck = new CardDeck(CardTypeEnum.PRIVATEOBJCARD);

            for (int i = 1; i <= NUMBER_OF_PRIVATE_OBJ_CARD; i++) {

                stringId = getTwoCharacterId(i);
                NodeList cardNodeList = getNodeList(pathName + stringId + ".xml", cardTag);

                if (cardNodeList == null) throw new NullPointerException(FILE_ERROR_MESSAGE);

                Node node = cardNodeList.item(0);

                PrivateObjCard card = loadPrivateObjCard(id, node);
                if (card != null) privateObjDeck.insertCard(card);

                id ++;
            }
        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }
    }

    /**
     * Loads a single Private Objective Card
     * @param id ID to be assigned to the new Private Objective Card
     * @param node Node containing xml information of the Private Objective Card
     * @return Private Objective Card created
     */
    private PrivateObjCard loadPrivateObjCard(int id, Node node){
        String name;
        String description;
        ColourEnum colour;
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
            description = element.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
            colour = ColourEnum.valueOf(element.getElementsByTagName("colour").item(0).getTextContent());

            return new PrivateObjCard(id, name, description, colour);
        }
        return null;
    }



    /**
     * Creates Public Objective Cards Deck
     */
    private void createPublicObjDeck(){

        String cardTag = "publicObjCard";
        int id = ID_FIRST_PUBLIC_OBJ_CARD;
        String stringId;

        final String pathName = "public";

        try {
            publicObjDeck = new CardDeck(CardTypeEnum.PUBLICOBJCARD);

            for (int i = 1; i <= NUMBER_OF_PUBLIC_OBJ_CARD; i++) {

                stringId = getTwoCharacterId(i);
                NodeList cardNodeList = getNodeList(pathName + stringId + ".xml", cardTag);

                if (cardNodeList == null) throw new NullPointerException(FILE_ERROR_MESSAGE);

                Node node = cardNodeList.item(0);

                PublicObjCard card = loadPublicObjCard(id, node);
                if (card != null) publicObjDeck.insertCard(card);

                id ++;
            }
        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }

    }

    /**
     * Loads a single Public Objective Card
     * @param id ID to be assigned to the new Public Objective Card
     * @param node Node containing xml information of the Public Objective Card
     * @return Public Objective Card created
     */
    private PublicObjCard loadPublicObjCard(int id, Node node){

        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            String name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
            String description = element.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
            String strategy = element.getElementsByTagName("typeofcalculation").item(0).getTextContent();
            int bonus = Integer.parseInt(element.getElementsByTagName("bonus").item(0).getTextContent());

            return new PublicObjCard(id, name, description, bonus, strategy);
        }
        return null;
    }

    /**
     * Creates Schema Cards Deck
     */
    private void createSchemaCardDeck() {

        String cardTag = "schemaCard";
        int id = ID_FIRST_SCHEMA_CARD;
        String stringId;

        final String pathName = "schema";

        try {
            schemaDeck = new CardDeck(CardTypeEnum.SCHEMACARD);

            for (int i = 1; i <= NUMBER_OF_SCHEMA_CARD; i++) {

                stringId = getTwoCharacterId(i);
                NodeList cardNodeList = getNodeList(pathName + stringId + ".xml", cardTag);

                if (cardNodeList == null) throw new NullPointerException(FILE_ERROR_MESSAGE);

                Node node = cardNodeList.item(0);

                SchemaCard card = loadSchemaCard(id, node);
                if (card != null) schemaDeck.insertCard(card);

                id += 2;
            }
        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }

    }

    /**
     * Loads a single SchemaCard
     * @param id ID to be assigned to the new SchemaCard
     * @param node Node containing xml information of the SchemaCard
     * @return SchemaCard created
     */
    private SchemaCard loadSchemaCard(int id, Node node) {
        String name;
        int difficulty;
        List<Cell> cellList;

        final String difficultyTag = "difficulty";

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
            difficulty = valueOf(element.getElementsByTagName(difficultyTag).item(0).getTextContent());

            cellList = extractCells(0, element);

            SchemaCard frontCard = new SchemaCard(id, name, "", difficulty, cellList);

            id++;

            name = element.getElementsByTagName(NAME_TAG).item(1).getTextContent();
            difficulty = valueOf(element.getElementsByTagName(difficultyTag).item(1).getTextContent());

            cellList = extractCells(NUMBER_OF_SCHEMA_COL * NUMBER_OF_SCHEMA_ROW, element);

            SchemaCard backCard = new SchemaCard(id, name, "", difficulty, cellList);
            frontCard.setBackSchema(backCard);
            backCard.setBackSchema(frontCard);

            return frontCard;
        }
        return null;
    }

    /**
     * Creates Tool Cards Deck
     */
    private void createToolDeck(){
        String cardTag = "toolCard";
        int id = ID_FIRST_TOOL_CARD;
        String stringId;

        final String pathName = "tool";

        try {
            toolDeck = new CardDeck(CardTypeEnum.TOOLCARD);

            for(int i = 1; i <= NUMBER_OF_TOOL_CARD; i++) {

                stringId = getTwoCharacterId(i);
                NodeList cardNodeList = getNodeList(pathName + stringId + ".xml", cardTag);

                if (cardNodeList == null) throw new NullPointerException(FILE_ERROR_MESSAGE);

                Node node = cardNodeList.item(0);

                ToolCard card = loadToolCard(id, node);
                if(card != null) toolDeck.insertCard(card);

                id++;
            }
        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }

    }

    /**
     * Loads a single ToolCard
     * @param id ID to be assigned to the new ToolCard
     * @param node Node containing xml information of the ToolCard
     * @return ToolCard created
     */
    private ToolCard loadToolCard(int id, Node node){
        String turnTag = "turn";
        String minQuantityTag = "minQuantity";
        String maxQuantityTag = "maxQuantity";
        String restrictionTag = "restriction";
        String stateTag = "state";
        String operationTag = "operation";
        String actionTag = "action";
        String diceContainerTag = "diceContainer";

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            String name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
            String description = element.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
            ColourEnum colour = ColourEnum.valueOf(element.getElementsByTagName("colour").item(0).getTextContent());

            int indexOfTurn = Integer.parseInt(element.getElementsByTagName(turnTag).item(0).getTextContent());
            int minNumberOfDice = Integer.parseInt(element.getElementsByTagName(minQuantityTag).item(0).getTextContent());
            int maxNumberOfDice = Integer.parseInt(element.getElementsByTagName(maxQuantityTag).item(0).getTextContent());
            String avoidedRestriction = element.getElementsByTagName(restrictionTag).item(0).getTextContent();

            List<List<OperationString>> operationStrings = new ArrayList<>();

            NodeList stateNodeList = element.getElementsByTagName(stateTag);
            for(int stateIndex = 0; stateIndex < stateNodeList.getLength(); stateIndex++) {

                operationStrings.add(new ArrayList<>());
                Node stateNode = stateNodeList.item(stateIndex);

                if (stateNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element stateElement = (Element) stateNode;

                    NodeList operationNodeList = stateElement.getElementsByTagName(operationTag);

                    for (int operationIndex = 0; operationIndex < operationNodeList.getLength(); operationIndex++) {

                        Node actionNode = operationNodeList.item(operationIndex);

                        if (actionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element actionElement = (Element) actionNode;

                            operationStrings.get(stateIndex).add(new OperationString(actionElement.getElementsByTagName(actionTag)
                                    .item(0).getTextContent(), actionElement.getElementsByTagName(diceContainerTag)
                                    .item(0).getTextContent()));
                        }
                    }
                }
            }

            return new ToolCard(id, name, description, colour, indexOfTurn, minNumberOfDice, maxNumberOfDice, avoidedRestriction, operationStrings);
        }
        return null;
    }

    /**
     * Transforms a number in two character String
     * @param i number to be transformed
     * @return Two character containing given number
     */
    private String getTwoCharacterId(int i) {
        if( i/10 == 0) return "0" + i;
        else return "" + i;
    }

    /**
     * Assigns the colours of existing Window Frame
     */
    private void createWindowFrame(){
        windowFrame = new ArrayList<>();
        windowFrame.add(ColourEnum.BLUE);
        windowFrame.add(ColourEnum.GREEN);
        windowFrame.add(ColourEnum.PURPLE);
        windowFrame.add(ColourEnum.RED);
    }

    /**
     * Parses the restriction from string to values
     * @param restriction String containing the description of the restriction
     * @return New Cell with restriction set
     */
    private Cell parseCell(String restriction){

        int value;
        ColourEnum colour;

        if(restriction.charAt(0) >= 49 && restriction.charAt(0) <= 54){
            value = restriction.charAt(0) - 48;
            colour = null;
        }else if(restriction.equals("BLANK")){
            value = 0;
            colour = null;
        }else{
            value = 0;
            try{
                colour = ColourEnum.valueOf(restriction);
            }catch (IllegalArgumentException e){
                colour = null;
            }
        }

        return new Cell(value, colour);
    }

    /**
     * Creates a NodeList from a XML file
     * @param pathName Path of XML File
     * @param tag Tag of a single element in the file
     * @return List of elements divided by the tag selected
     */
    private static NodeList getNodeList(String pathName, String tag){
        try{
            File file = new File(pathName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            System.out.println(GameLoader.class.toString());
            System.out.println(GameLoader.class.getResource(pathName));
            System.out.println(GameLoader.class.getResource(pathName).toURI());
            System.out.println(GameLoader.class.getResource(pathName).toURI().toString());

            Document document = documentBuilder.parse(GameLoader.class.getResource(pathName).toURI().toString());

            return document.getElementsByTagName(tag);
        }catch (Exception e){
            System.out.println("NON CARICO CARTE");

        }
        return null;
    }

    /**
     * Extract a List of Cells in the Element selected
     * @param startingIndex Index of the first cell in file
     * @param element Element where the cells are
     * @return Entire list of cell imported from the file
     */
    private List<Cell> extractCells(int startingIndex, Element element){

        final String CELLTAG = "cell";
        List<Cell> cellList = new ArrayList<>();
        Cell cell;
        String restriction;

        for (int row = 0; row < NUMBER_OF_SCHEMA_ROW; row++) {
            for (int col = 0; col < NUMBER_OF_SCHEMA_COL; col++) {

                restriction = element.getElementsByTagName(CELLTAG).item(startingIndex + row * NUMBER_OF_SCHEMA_COL + col).getTextContent();
                cell = parseCell(restriction);
                cellList.add(cell);

            }
        }
        return cellList;
    }


}
