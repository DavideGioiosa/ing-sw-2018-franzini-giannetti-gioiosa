package it.polimi.se2018.view.graphic;
import it.polimi.se2018.model.Die;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;

/**
 * View's Graphic Class: GuiShow
 *
 * @author Davide Gioiosa
 */

public class GuiShow {
    final static String outOfRange = "Id PublicObject card out of the range permitted";

    //param die
    public static Scene showDie (Die die){
        ImageView dieImage = setImage(setDie(die));

        HBox layout = new HBox();
        layout.getChildren().addAll(dieImage);

        return new Scene(layout, 300, 300);
    }

    //TODO: parametri da cambiare con nomi delle carte
    public static Scene showPublicObjCard (int id1, int id2, int id3) {

        ImageView pubObjCard1 = setImage(setPublicCard(id1));
        ImageView pubObjCard2 = setImage(setPublicCard(id2));
        ImageView pubObjCard3 = setImage(setPublicCard(id3));

        HBox layout = new HBox();
        layout.getChildren().addAll(pubObjCard1, pubObjCard2, pubObjCard3);

        return new Scene (layout, 859,400);
    }

    //TODO: parametri da cambiare con nome della carta
    public static Scene showPrivateObjCard (int id) {
        ImageView privateObjCard = setImage(setPrivateCard(id));

        HBox layout = new HBox();
        layout.getChildren().addAll(privateObjCard);

        return new Scene (layout, 286,400);
    }

    //TODO: parametri da cambiare con nome della carta
    public static Scene showToolCard (int id) {
        ImageView toolCard = setImage(setToolCard(id));

        HBox layout = new HBox();
        layout.getChildren().addAll(toolCard);

        return new Scene (layout, 286,400);
    }


    private static ImageView setImage (Image image){
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setFitHeight(400);

        return iv;
    }

    private static Image setPublicCard (int id){
        if(id < 1 || id > 10){
            throw new IllegalArgumentException(outOfRange);
        }
        Image image;

        switch (id) {
            case 1: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjColorDiagonals.jpg"); break;
            case 2: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjColorVariety.jpg"); break;
            case 3: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjColumnColorVariety.jpg"); break;
            case 4: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjColumnShadeVariety.jpg"); break;
            case 5: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjDeepShades.jpg"); break;
            case 6: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjLightShades.jpg"); break;
            case 7: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjMediumShades.jpg"); break;
            case 8: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjRowColorVariety.jpg"); break;
            case 9: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjShadeVariety.jpg"); break;
            case 10: image = new Image("it.polimi.se2018/img/publicObjCard/PubObjRowShadeVariety.jpg"); break;
            default: image = null;
        }
        return image;
    }

    private static Image setPrivateCard (int id){
        if(id < 1 || id > 5){
            throw new IllegalArgumentException(outOfRange);
        }
        Image image;

        switch (id) {
            case 1: image = new Image("it.polimi.se2018/img/privateObjCard/PriObjShadesOfBlue.jpg");break;
            case 2: image = new Image("it.polimi.se2018/img/privateObjCardPriObjShadesOfGreen.jpg"); break;
            case 3: image = new Image("it.polimi.se2018/img/privateObjCardPriObjShadesOfPurple.jpg"); break;
            case 4: image = new Image("it.polimi.se2018/img/privateObjCardPriObjShadesOfRed.jpg"); break;
            case 5: image = new Image("it.polimi.se2018/img/privateObjCardPriObjShadesOfYellow.jpg"); break;
            default: image = null;
        }
        return image;
    }

    private static Image setToolCard (int id){
        if(id < 1 || id > 12){
            throw new IllegalArgumentException(outOfRange);
        }
        Image image;

        switch (id) {
            case 1: image = new Image("it.polimi.se2018/img/toolCard/Tool1.jpg"); break;
            case 2: image = new Image("it.polimi.se2018/img/toolCard/Tool2.jpg"); break;
            case 3: image = new Image("it.polimi.se2018/img/toolCard/Tool3.jpg"); break;
            case 4: image = new Image("it.polimi.se2018/img/toolCard/Tool4.jpg"); break;
            case 5: image = new Image("it.polimi.se2018/img/toolCard/Tool5.jpg"); break;
            case 6: image = new Image("it.polimi.se2018/img/toolCard/Tool6.jpg"); break;
            case 7: image = new Image("it.polimi.se2018/img/toolCard/Tool7.jpg"); break;
            case 8: image = new Image("it.polimi.se2018/img/toolCard/Tool8.jpg"); break;
            case 9: image = new Image("it.polimi.se2018/img/toolCard/Tool9.jpg"); break;
            case 10: image = new Image("it.polimi.se2018/img/toolCard/Tool10.jpg"); break;
            case 11: image = new Image("it.polimi.se2018/img/toolCard/Tool11.jpg"); break;
            case 12: image = new Image("it.polimi.se2018/img/toolCard/Tool12.jpg"); break;
            default: image = null;
        }
        return image;
    }

    private static Image setDie (Die die){
        if(die == null){
            throw new NullPointerException("Insert null die");
        }
        Image image;

        switch (die.getColour()) {
            case BLUE:
                switch (die.getValue()) {
                    case 1: image = new Image("it.polimi.se2018/img/dice/blue/DieBlue1"); break;
                    case 2: image = new Image("it.polimi.se2018/img/dice/blue/DieBlue2"); break;
                    case 3: image = new Image("it.polimi.se2018/img/dice/blue/DieBlue3"); break;
                    case 4: image = new Image("it.polimi.se2018/img/dice/blue/DieBlue4"); break;
                    case 5: image = new Image("it.polimi.se2018/img/dice/blue/DieBlue5"); break;
                    case 6: image = new Image("it.polimi.se2018/img/dice/blue/DieBlue6"); break;
                    default: image = null; break;
                }
                break;

            case RED:
                switch (die.getValue()) {
                    case 1: image = new Image("it.polimi.se2018/img/dice/red/DieRed1"); break;
                    case 2: image = new Image("it.polimi.se2018/img/dice/red/DieRed2"); break;
                    case 3: image = new Image("it.polimi.se2018/img/dice/red/DieRed3"); break;
                    case 4: image = new Image("it.polimi.se2018/img/dice/red/DieRed4"); break;
                    case 5: image = new Image("it.polimi.se2018/img/dice/red/DieRed5"); break;
                    case 6: image = new Image("it.polimi.se2018/img/dice/red/DieRed6"); break;
                    default: image = null; break;
                }
                break;

            case GREEN:
                switch (die.getValue()) {
                    case 1: image = new Image("it.polimi.se2018/img/dice/green/DieGreen1"); break;
                    case 2: image = new Image("it.polimi.se2018/img/dice/green/DieGreen2"); break;
                    case 3: image = new Image("it.polimi.se2018/img/dice/green/DieGreen3"); break;
                    case 4: image = new Image("it.polimi.se2018/img/dice/green/DieGreen4"); break;
                    case 5: image = new Image("it.polimi.se2018/img/dice/green/DieGreen5"); break;
                    case 6: image = new Image("it.polimi.se2018/img/dice/green/DieGreen6"); break;
                    default: image = null; break;
                }
                break;

            case YELLOW:
                switch (die.getValue()) {
                    case 1: image = new Image("it.polimi.se2018/img/dice/yellow/DieYellow1"); break;
                    case 2: image = new Image("it.polimi.se2018/img/dice/yellow/DieYellow2"); break;
                    case 3: image = new Image("it.polimi.se2018/img/dice/yellow/DieYellow3"); break;
                    case 4: image = new Image("it.polimi.se2018/img/dice/yellow/DieYellow4"); break;
                    case 5: image = new Image("it.polimi.se2018/img/dice/yellow/DieYellow5"); break;
                    case 6: image = new Image("it.polimi.se2018/img/dice/yellow/DieYellow6"); break;
                    default: image = null; break;
                }
                break;

            case PURPLE:
                switch (die.getValue()) {
                    case 1: image = new Image("it.polimi.se2018/img/dice/purple/DiePurple1"); break;
                    case 2: image = new Image("it.polimi.se2018/img/dice/purple/DiePurple2"); break;
                    case 3: image = new Image("it.polimi.se2018/img/dice/purple/DiePurple3"); break;
                    case 4: image = new Image("it.polimi.se2018/img/dice/purple/DiePurple4"); break;
                    case 5: image = new Image("it.polimi.se2018/img/dice/purple/DiePurple5"); break;
                    case 6: image = new Image("it.polimi.se2018/img/dice/purple/DiePurple6"); break;
                    default: image = null; break;
                }
                break;

            default: image = null;
        }
        return image;
    }
}