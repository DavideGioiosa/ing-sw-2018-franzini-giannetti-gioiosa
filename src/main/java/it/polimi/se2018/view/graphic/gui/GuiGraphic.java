package it.polimi.se2018.view.graphic.gui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.application.Application;

import java.io.File;
import java.net.URL;

public class GuiGraphic extends Application {

    /**
     * runs the start method thought Application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * starts GUI
     * @param primaryStage first window of the GUI
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = new File("src\\main\\java\\it\\polimi\\se2018\\view\\graphic\\gui\\GameboardMatch.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root, 750, 400));
        primaryStage.show();
    }

}
