package it.polimi.se2018.connection.server;


//GlobalLauncher
public class ServerLauncher {
    private ServerManager serverManager;
    private GameCreator gameCreator;

    public ServerLauncher (){
        initialize();
        addObsServerManager();
    }

    private void initialize (){
        serverManager = new ServerManager();
        gameCreator = new GameCreator ();
    }

    private void addObsServerManager (){
        serverManager.addObserver(gameCreator.getGameManager());
        //serverManager.addObserver(gameCreator.getGameManager().getGamestarter());
    }

    //per remote view
    public ServerManager getGlobalController() {
        return serverManager;
    }

}
