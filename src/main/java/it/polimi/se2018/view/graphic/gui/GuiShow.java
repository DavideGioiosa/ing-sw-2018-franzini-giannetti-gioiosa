package it.polimi.se2018.view.graphic.gui;
import it.polimi.se2018.model.Die;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;

import java.io.File;

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
        layout.setSpacing(10);
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
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\publicObjCard\\";

        switch (id) {
            case 1: image = new Image(string + "PubObjColorDiagonals.jpg"); break;
            case 2: image = new Image(string + "PubObjColorVariety.jpg"); break;
            case 3: image = new Image(string + "PubObjColumnColorVariety.jpg"); break;
            case 4: image = new Image(string + "PubObjColumnShadeVariety.jpg"); break;
            case 5: image = new Image(string + "PubObjDeepShades.jpg"); break;
            case 6: image = new Image(string + "PubObjLightShades.jpg"); break;
            case 7: image = new Image(string + "PubObjMediumShades.jpg"); break;
            case 8: image = new Image(string + "PubObjRowColorVariety.jpg"); break;
            case 9: image = new Image(string + "PubObjShadeVariety.jpg"); break;
            case 10: image = new Image(string + "PubObjRowShadeVariety.jpg"); break;
            default: image = null;
        }
        return image;
    }

    private static Image setPrivateCard (int id){
        if(id < 1 || id > 5){
            throw new IllegalArgumentException(outOfRange);
        }
        Image image;
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\privateObjCard\\";

        switch (id) {
            case 1: image = new Image(string + "PriObjShadesOfBlue.jpg");break;
            case 2: image = new Image(string + "PriObjShadesOfGreen.jpg"); break;
            case 3: image = new Image(string + "PriObjShadesOfPurple.jpg"); break;
            case 4: image = new Image(string + "PriObjShadesOfRed.jpg"); break;
            case 5: image = new Image(string + "PriObjShadesOfYellow.jpg"); break;
            default: image = null;
        }
        return image;
    }

    private static Image setToolCard (int id){
        if(id < 1 || id > 12){
            throw new IllegalArgumentException(outOfRange);
        }
        Image image;
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\toolCard\\";

        switch (id) {
            case 1: image = new Image(string + "Tool1.jpg"); break;
            case 2: image = new Image(string + "Tool2.jpg"); break;
            case 3: image = new Image(string + "Tool3.jpg"); break;
            case 4: image = new Image(string + "Tool4.jpg"); break;
            case 5: image = new Image(string + "Tool5.jpg"); break;
            case 6: image = new Image(string + "Tool6.jpg"); break;
            case 7: image = new Image(string + "Tool7.jpg"); break;
            case 8: image = new Image(string + "Tool8.jpg"); break;
            case 9: image = new Image(string + "Tool9.jpg"); break;
            case 10: image = new Image(string + "Tool10.jpg"); break;
            case 11: image = new Image(string + "Tool11.jpg"); break;
            case 12: image = new Image(string + "Tool12.jpg"); break;
            default: image = null;
        }
        return image;
    }

    private static Image setDie (Die die){
        if(die == null){
            throw new NullPointerException("Insert null die");
        }
        Image image;
        String string = "src\\main\\java\\it\\polimi\\se2018\\img\\dice\\";
        try {
            return new Image(String.valueOf(new File(string + die.getColour().toString().toLowerCase()
                    + "\\" + die.getValue() + ".jpg").toURI().toURL()));
        }catch (Exception e){
            //TODO
        }
        return null;
    }

}