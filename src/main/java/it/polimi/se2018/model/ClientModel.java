package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;

/**
 * Local Model containing Board status
 * @author Cristian Giannetti
 */
public class ClientModel extends Observable<ClientModel>{

    /**
     * status of ClientBoard
     */
    private ClientBoard clientBoard;

    private Player actualPlayer;

    private PrivateObjCard privateObjCard;

    private User actualUser;

    /**
     * Constructor
     */
    public ClientModel(){
        clientBoard = null;
    }

    private ClientModel(ClientModel clientModel){
        this.clientBoard = clientModel.getClientBoard().getClone();
        if(this.actualPlayer != null) this.actualPlayer = clientModel.actualPlayer.getClone();
        else actualPlayer = null;
        if(this.privateObjCard != null) this.privateObjCard = clientModel.privateObjCard.getClone();
        else privateObjCard = null;
    }

    /**
     * Setter method for ClientBoard notifies the change of the Board
     * @param clientBoard Board updated
     */
    public void setClientBoard(ClientBoard clientBoard) {
        this.clientBoard = clientBoard;
        try {
            notify(this.getClone());
        }catch (NullPointerException e){

        }
    }

    /**
     * Getter method for Client Board
     * @return Status of ClientBoard
     */
    public ClientBoard getClientBoard() {
        return clientBoard;
    }

    public void setPrivateObjCard(PrivateObjCard privateObjCard) {
        this.privateObjCard = privateObjCard;
    }

    public PrivateObjCard getPrivateObjCard() {
        return privateObjCard;
    }

    public Player getPlayer() {
        return actualPlayer;
    }

    public ClientModel getClone(){
        return new ClientModel(this);
    }

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public void setActualUser(User actualUser) {
        this.actualUser = actualUser;
    }

    public User getActualUser() {
        return actualUser;
    }

}
