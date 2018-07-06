package it.polimi.se2018.view.graphic.gui;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.cards.Card;
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


    static public Image showCard(Card card, String stringa) {
        String string = "it/polimi/se2018/view/graphic/gui/img/" + stringa + "/" +
                card.getName().replaceAll(" ", "") + ".jpg" ;

        return new Image(string);

    }

    //TODO CHECK STATIC
    static public Image showDie(Die die) {
        String string = "it/polimi/se2018/view/graphic/gui/img/dice/" +
                die.getColour().toString().toLowerCase() + "/" + die.getValue() + ".jpg";

        return new Image(string);
    }
}