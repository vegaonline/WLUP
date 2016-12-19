/*
 * JWLUP - Java Based WLUP
 * Developed by Abhijit Bhattacharyya,
 * Nuclear Data Physics Centre of India (NDPCI),
 *  Bhabha Atomic Research Centre, Mumbai, 400 085, INDIA
 */
package wlup;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author vega
 */
public class Wlup extends Application {

    private BorderPane mainLayout;
    public Scene myScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle ("WLUP :: WIMS library Analyser");
        FXMLLoader loader = new FXMLLoader (Wlup.class.getResource (
                "myRootFXML.fxml"));
        mainLayout = (BorderPane) loader.load ();
        myScene = new Scene (mainLayout);
        primaryStage.setScene (myScene);
        
        primaryStage.setMinWidth (1200);
        primaryStage.setMinHeight (500);
        MyRootFXMLController controller = loader.getController ();
        
        primaryStage.show ();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch (args);
    }

}
