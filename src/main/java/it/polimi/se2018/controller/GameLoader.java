package it.polimi.se2018.controller;

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
     * Name of Card tag in XML file
     */
    private static final String CARD_TAG = "card";
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
        createSchemaCard();
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

        int id = ID_FIRST_PRIVATE_OBJ_CARD;
        String name;
        String description;
        ColourEnum colour;
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\PrivateObjCard.xml";

        try {

            NodeList nodeList = getNodeList(pathName, CARD_TAG);
            if (nodeList == null) throw new RuntimeException(FILE_ERROR_MESSAGE);

            privateObjDeck = new CardDeck(CardTypeEnum.PRIVATEOBJCARD);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    name = eElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                    description = eElement.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
                    colour = ColourEnum.valueOf(eElement.getElementsByTagName("colour").item(0).getTextContent());

                    PrivateObjCard card = new PrivateObjCard(id, name, description, colour);
                    privateObjDeck.insertCard(card);
                }
                id ++;
            }

        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }

    }

    /**
     * Creates Public Objective Cards Deck
     */
    private void createPublicObjDeck(){

        int id = ID_FIRST_PUBLIC_OBJ_CARD;
        String name;
        String description;
        int bonus;
        String strategy;
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\PublicObjCards.xml";

        try {

            NodeList nodeList = getNodeList(pathName, CARD_TAG);
            if (nodeList == null) throw new RuntimeException(FILE_ERROR_MESSAGE);

            publicObjDeck = new CardDeck(CardTypeEnum.PUBLICOBJCARD);

            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                    description = element.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
                    strategy = element.getElementsByTagName("typeofcalculation").item(0).getTextContent();
                    bonus = valueOf(element.getElementsByTagName("bonus").item(0).getTextContent());

                    PublicObjCard card = new PublicObjCard(id, name, description, bonus, strategy);
                    publicObjDeck.insertCard(card);
                }
                id ++;
            }

        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }

    }

    /**
     * Creates Schema Cards Deck
     */
    private void createSchemaCard(){
        int id = ID_FIRST_SCHEMA_CARD;
        String name;
        int difficulty;
        List<Cell> cellList;
        final String DIFFICULTYTAG = "difficulty";

        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\SchemaCards.xml";

        try {

            NodeList nodeList = getNodeList(pathName, CARD_TAG);
            if (nodeList == null) throw new NullPointerException(FILE_ERROR_MESSAGE);

            schemaDeck = new CardDeck(CardTypeEnum.SCHEMACARD);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                    difficulty = valueOf(element.getElementsByTagName(DIFFICULTYTAG).item(0).getTextContent());

                    cellList = extractCells(0, element);

                    SchemaCard frontCard = new SchemaCard(id, name, "", difficulty, cellList);

                    id ++;

                    name = element.getElementsByTagName(NAME_TAG).item(1).getTextContent();
                    difficulty = valueOf(element.getElementsByTagName(DIFFICULTYTAG).item(1).getTextContent());

                    cellList = extractCells(NUMBER_OF_SCHEMA_COL * NUMBER_OF_SCHEMA_ROW, element);

                    SchemaCard backCard = new SchemaCard(id, name, "", difficulty, cellList);
                    id ++;
                    frontCard.setBackSchema(backCard);
                    backCard.setBackSchema(frontCard);

                    schemaDeck.insertCard(frontCard);

                }
            }

        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }
    }

    /**
     * Creates Tool Cards Deck
     */
    private void createToolDeck(){

        int id = ID_FIRST_TOOL_CARD;
        String name;
        String description;
        ColourEnum colour;
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\ToolCards.xml";

        try {

            NodeList nodeList = getNodeList(pathName, CARD_TAG);
            if (nodeList == null) throw new NullPointerException(FILE_ERROR_MESSAGE);

            toolDeck = new CardDeck(CardTypeEnum.TOOLCARD);

            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    name = element.getElementsByTagName(NAME_TAG).item(0).getTextContent();
                    description = element.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent();
                    colour = ColourEnum.valueOf(element.getElementsByTagName("colour").item(0).getTextContent());

                    ToolCard card = new ToolCard(id, name, description, colour);
                    toolDeck.insertCard(card);
                }
                id ++;
            }

        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }

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
    private NodeList getNodeList(String pathName, String tag){
        try{
            File file = new File(pathName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            return document.getElementsByTagName(tag);
        }catch (Exception e){

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
