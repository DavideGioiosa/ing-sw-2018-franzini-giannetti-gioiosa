package it.polimi.se2018.controller;

import it.polimi.se2018.model.CardTypeEnum;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.public_card.PublicObjCard;
import it.polimi.se2018.model.cards.public_card.ScoreStrategy;

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


/**
 * Creates Public Objective, Private Objective and Tool Cards and loads Schema Cards
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

        int id = IDFIRSTPRIVATEOBJCARD;
        String name;
        String description;
        ColourEnum colour;
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\PrivateObjCard.xml";

        try {

            File file = new File(pathName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName("card");

            privateObjDeck = new CardDeck(CardTypeEnum.PRIVATEOBJCARD);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    description = eElement.getElementsByTagName("description").item(0).getTextContent();
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

        int id = IDFIRSTPUBLICOBJCARD;
        String name;
        String description;
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\PublicObjCards.xml";
        String strategy;

        try {

            File file = new File(pathName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName("card");

            publicObjDeck = new CardDeck(CardTypeEnum.PRIVATEOBJCARD);

            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    name = element.getElementsByTagName("name").item(0).getTextContent();
                    description = element.getElementsByTagName("description").item(0).getTextContent();
                    strategy = element.getElementsByTagName("strategy").item(0).getTextContent();

                    PublicObjCard card = new PublicObjCard(id, name, description, strategy);
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

        int id = IDFIRSTSCHEMACARD;
        String name;
        int difficulty;
        String restriction;

        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\SchemaCards.xml";

        Cell cell;
        List<Cell> cellList;

        try {

            File file = new File(pathName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName("card");
            schemaDeck = new CardDeck(CardTypeEnum.SCHEMACARD);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                cellList = new ArrayList<>();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    name = element.getElementsByTagName("name").item(0).getTextContent();
                    difficulty = Integer.valueOf(element.getElementsByTagName("difficulty").item(0).getTextContent());

                    for (int row = 0; row < NUMBEROFSCHEMAROW; row++) {
                        for (int col = 0; col < NUMBEROFSCHEMACOL; col++) {

                            restriction = element.getElementsByTagName("cell").item(row * NUMBEROFSCHEMACOL + col).getTextContent();
                            cell = parseCell(restriction);
                            cellList.add(cell);

                        }
                    }

                    SchemaCard frontCard = new SchemaCard(id, name, "", difficulty, cellList);
                    id ++;

                    cellList = new ArrayList<>();

                    for (int row = 0; row < NUMBEROFSCHEMAROW; row++) {
                        for (int col = 0; col < NUMBEROFSCHEMACOL; col++) {

                            restriction = element.getElementsByTagName("cell").item(NUMBEROFSCHEMACOL * NUMBEROFSCHEMAROW + row * NUMBEROFSCHEMACOL + col).getTextContent();
                            cell = parseCell(restriction);
                            cellList.add(cell);

                        }
                    }

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

        int id = IDFIRSTTOOLCARDS;
        String name;
        String description;
        ColourEnum colour;
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\ToolCards.xml";

        try {

            File file = new File(pathName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName("card");

            toolDeck = new CardDeck(CardTypeEnum.TOOLCARD);

            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    name = element.getElementsByTagName("name").item(0).getTextContent();
                    description = element.getElementsByTagName("description").item(0).getTextContent();
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
     * @return New Cell with restriction setted
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
}
