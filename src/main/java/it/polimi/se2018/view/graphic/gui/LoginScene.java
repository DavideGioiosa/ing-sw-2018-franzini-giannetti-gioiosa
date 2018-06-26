package it.polimi.se2018.view.graphic.gui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class LoginScene {

    public static Scene login() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(12);
        gridPane.setHgap(8);

        //username
        Label usernameLabel = new Label("Username ");
        GridPane.setConstraints(usernameLabel, 0, 0);

        //get username from client's input
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        //ip
        Label ipLabel = new Label("Ip ");
        GridPane.setConstraints(ipLabel, 0, 1);

        //get ip connection from client's input
        TextField ipInput = new TextField();
        ipInput.setPromptText("Ip match address");
        GridPane.setConstraints(ipInput, 1, 1);


        final ToggleGroup group = new ToggleGroup();
        Label typeOfConnectionLabel = new Label("Connection ");
        GridPane.setConstraints(typeOfConnectionLabel, 0, 2);

        RadioButton socketButton = new RadioButton("socket");
        GridPane.setConstraints(socketButton, 1, 2);
        socketButton.setToggleGroup(group);
        RadioButton rmiButton = new RadioButton("rmi");
        rmiButton.setToggleGroup(group);
        GridPane.setConstraints(rmiButton, 2, 2);

        Button loginButton = new Button("Join");
        loginButton.setOnAction(event -> {
            isInt (ipInput);
            radioHandler (socketButton, rmiButton);
        });

        GridPane.setConstraints(loginButton, 1, 5);

        gridPane.getChildren().addAll(usernameLabel, usernameInput, ipLabel, ipInput, typeOfConnectionLabel,
                socketButton, rmiButton, loginButton);

        return new Scene(gridPane, 300,180);
    }

    /**
     * Check if the ip is composed only by numbers
     * @param textField ip insert by the client
     * @return validity of the ip
     */
    private static boolean isInt (TextField textField){
        try{
            String message = textField.getText().replace(".","");
            int ipAddress = Integer.parseInt(message);
            System.out.println("ip address is: " + textField.getText());
            return true;
        }catch (NumberFormatException e){
            System.out.println(textField.getText() + "is not a valid ip address");
            return false;
        }
    }

    /**
     * Handle radio buttons of the connection
     * @param socket
     * @param rmi
     */
    private static void radioHandler (RadioButton socket, RadioButton rmi){
        if(socket.isSelected()){
            //typeOfConnection = true;
            System.out.println("Connection: socket");
        }
        else if (rmi.isSelected()){
            //typeOfConnection = true;
            System.out.println("Connection: rmi");
        }
    }

}

