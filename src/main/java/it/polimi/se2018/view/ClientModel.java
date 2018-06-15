package it.polimi.se2018.view;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.utils.Observable;

/**
 * Local Model containing Board status
 * @author Cristian Giannetti
 */
public class ClientModel extends Observable<ClientBoard>{

    /**
     * status of ClientBoard
     */
    private ClientBoard clientBoard;

    /**
     * Constructor
     */
    public ClientModel(){
        clientBoard = null;
    }

    /**
     * Setter method for ClientBoard notify the change of the Board
     * @param clientBoard Board updated
     */
    public void setClientBoard(ClientBoard clientBoard) {
        this.clientBoard = clientBoard;
        try {
            notify(clientBoard.getClone());
        }catch (CloneNotSupportedException e){

        }
    }

    /**
     * Getter method for Client Board
     * @return Status of ClientBoard
     */
    public ClientBoard getClientBoard() {
        return clientBoard;
    }
}
