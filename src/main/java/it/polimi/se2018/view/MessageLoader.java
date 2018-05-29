package it.polimi.se2018.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.polimi.se2018.model.CardTypeEnum;
import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.CardDeck;
import it.polimi.se2018.model.cards.PrivateObjCard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MessageLoader {
    private Map<Integer, String> errors;


    public MessageLoader(){
        String filePath = "\"src\\main\\java\\it\\polimi\\se2018\\configuration\\Errors.xml";
        this.errors =  new HashMap<>();

        String id;
        String description;
        int key;

        try {
            File file = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName("error");


            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    id = eElement.getElementsByTagName("id").item(0).getTextContent();
                    key = Integer.valueOf(id);
                    description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    errors.put(key,description);
                }

            }

        } catch (Exception e) {
            //TODO: Gestione Exceptions
        }


    }

    public Map<Integer,String> getErrors(){
        return  this.errors;
    }
}
