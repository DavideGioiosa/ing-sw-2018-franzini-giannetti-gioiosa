package it.polimi.se2018.view.graphic.cli;

import static it.polimi.se2018.controller.GameLoader.getNodeList;
import static org.fusesource.jansi.Ansi.*;

import it.polimi.se2018.model.ColourEnum;
import org.fusesource.jansi.AnsiConsole;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class CommandLinePrint {

    private static EnumMap<ColourEnum, Color> hashMapColours;

    private static List<HashMap<Integer, String>> dieRowStringList;

    public static HashMap<Integer, String> messageMap;
    public static HashMap<Integer, String> errorMap;
    static {

        String dieLineVoid       = "|       |";
        String dieLineTwo        = "| O   O |";
        String dieLineOneLeft    = "| O     |";
        String dieLineOneCenter  = "|   O   |";
        String dieListOneRight   = "|     O |";
        String dieListColourEven = "| # # # |";
        String dieListColourOdd  = "|# # # #|";

        HashMap<Integer, String> dieRowString1;
        HashMap<Integer, String> dieRowString2;
        HashMap<Integer, String> dieRowString3;

        dieRowString1 = new HashMap<>();
        dieRowString2 = new HashMap<>();
        dieRowString3 = new HashMap<>();

        dieRowString1.put(0, dieLineVoid);
        dieRowString2.put(0, dieLineVoid);
        dieRowString3.put(0, dieLineVoid);

        dieRowString1.put(1, dieLineVoid);
        dieRowString2.put(1, dieLineOneCenter);
        dieRowString3.put(1, dieLineVoid);

        dieRowString1.put(2, dieLineOneLeft);
        dieRowString2.put(2, dieLineVoid);
        dieRowString3.put(2, dieListOneRight);

        dieRowString1.put(3, dieLineOneLeft);
        dieRowString2.put(3, dieLineVoid);
        dieRowString3.put(3, dieListOneRight);

        dieRowString1.put(4, dieLineTwo);
        dieRowString2.put(4, dieLineVoid);
        dieRowString3.put(4, dieLineTwo);

        dieRowString1.put(5, dieLineTwo);
        dieRowString2.put(5, "|   O   |");
        dieRowString3.put(5, dieLineTwo);

        dieRowString1.put(6, dieLineTwo);
        dieRowString2.put(6, dieLineTwo);
        dieRowString3.put(6, dieLineTwo);

        dieRowString1.put(-1, dieListColourEven);
        dieRowString2.put(-1, dieListColourOdd);
        dieRowString3.put(-1, dieListColourEven);

        dieRowStringList = new ArrayList<>();
        dieRowStringList.add(dieRowString1);
        dieRowStringList.add(dieRowString2);
        dieRowStringList.add(dieRowString3);

        loadMessageMap();
        loadErrorMap();
    }

    public CommandLinePrint(EnumMap<ColourEnum, Color> hashMapColours){

        loadErrorMap();
        loadMessageMap();

        this.hashMapColours = hashMapColours;

    }

    private static void loadErrorMap() {
        errorMap = new HashMap<>();
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\ErrorCodes.xml";

        NodeList errorNodeList = getNodeList(pathName, "errors");
        if (errorNodeList == null) throw new NullPointerException("File non valido");

        for(int i = 0; i< errorNodeList.getLength(); i++) {
            Node errorNode = errorNodeList.item(i);

            if (errorNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) errorNode;

                NodeList errorList = element.getElementsByTagName("error");

                for (int index = 0; index < errorList.getLength(); index++) {
                    Node singleErrorNode = errorList.item(index);
                    if (singleErrorNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element singleError = (Element) singleErrorNode;

                        int id = Integer.parseInt(singleError.getElementsByTagName("id").item(0).getTextContent());
                        String errorString = singleError.getElementsByTagName("description").item(0).getTextContent();
                        errorMap.put(id, errorString);
                    }
                }
            }
        }

    }


    private static void loadMessageMap() {
        messageMap = new HashMap<>();
        String pathName = "src\\main\\java\\it\\polimi\\se2018\\configuration\\MessageCodes.xml";

        NodeList errorNodeList = getNodeList(pathName, "messages");
        if (errorNodeList == null) throw new NullPointerException("File non valido");

        for(int i = 0; i< errorNodeList.getLength(); i++) {
            Node errorNode = errorNodeList.item(i);

            if (errorNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) errorNode;

                NodeList errorList = element.getElementsByTagName("message");

                for (int j = 0; j < errorList.getLength(); j++) {
                    Node singleErrorNode = errorList.item(j);
                    if (singleErrorNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element singleMessage = (Element) singleErrorNode;

                        int id = Integer.parseInt(singleMessage.getElementsByTagName("id").item(0).getTextContent());
                        String messageString = singleMessage.getElementsByTagName("description").item(0).getTextContent();
                        messageMap.put(id, messageString);
                    }
                }
            }
        }

    }

    /**
     * Print String
     * @param string String to print
     */
    static void print(String string){
        System.out.print(string);
    }

    static void printMessage(int id){
        if(id != 0) System.out.println(messageMap.get(id));
    }

    static void printError(int id){
        if(id != 0) System.out.println(errorMap.get(id));
    }

    /**
     * Print char
     * @param character char to print
     */
    static void print(char character){
        System.out.print(character);
    }

    /**
     * Print entire line
     * @param string String to print
     */
    public static void println(String string){
        System.out.println(string);
    }

    /**
     * Print line with an Integer
//     * @param num Integer to print
     */
    static void println(int num){
        System.out.println(num);
    }

    public void colouredDiePrint(int row, ColourEnum colour, int value){
        if (colour != null && value == 0) value = -1;
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        if (colour == null){
            print(dieRowStringList.get(row).get(value));
        }
        else System.out.print(ansi().eraseScreen().fg(hashMapColours.get(colour)).a(dieRowStringList.get(row).get(value)).reset());
    }

    static void colouredPrint(char character, ColourEnum colour){
        System.out.print(ansi().eraseScreen().fg(hashMapColours.get(colour)).a(character).reset());
    }
}
