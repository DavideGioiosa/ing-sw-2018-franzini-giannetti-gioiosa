package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.PrivatePlayer;
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

    private PrivatePlayer privatePlayer;

    /**
     * Constructor
     */
    public ClientModel(){
        clientBoard = null;
    }

    private ClientModel(ClientModel clientModel){
        this.clientBoard = clientModel.getClientBoard().getClone();
        if(clientModel.getPrivatePlayer() != null) this.privatePlayer = clientModel.getPrivatePlayer().getClone();
        else privatePlayer = null;
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

    public void setPrivatePlayer(Player player, PrivateObjCard privateObjCard) {
        this.privatePlayer = new PrivatePlayer(player, privateObjCard);
    }

    public PrivatePlayer getPrivatePlayer() {
        return privatePlayer;
    }

    /**
     * Getter method for Client Board
     * @return Status of ClientBoard
     */
    public ClientBoard getClientBoard() {
        return clientBoard;
    }

    public ClientModel getClone(){
        return new ClientModel(this);
    }
}
