package it.polimi.se2018.controller.client;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;

public class ChoiceController {


    public ChoiceController (){
    }

    public int checkChoice(PlayerChoice message) {
        Boolean schema = false;
        Boolean col = false;

        if(message.getColourEnumList().isEmpty()) col = true;
        else
            for(ColourEnum colour: message.getColourEnumList())
                if(message.getChosenColour() == colour)
                    col = true;

        if(message.getSchemaCardList().isEmpty()) schema = true;
        else
            for(SchemaCard schemaCard: message.getSchemaCardList())
                if(schemaCard.getId() == message.getIdChosenSchema())
                    schema = true;

        if(schema && col) return 0;
        else return 2504;
    }

    public int checkNickname(User user){
        if (user.getNickname() == null || user.getNickname() == "" || user.getNickname().length() > 25) return 2502;
        return 0;
    }

    PlayerChoice getResetPlayerChoice(PlayerChoice playerChoice){
        if(playerChoice.getColourEnumList().isEmpty() && playerChoice.getSchemaCardList().isEmpty())
            return null;
        //playerChoice.setChosenColour(null);
        return playerChoice;
    }
}
