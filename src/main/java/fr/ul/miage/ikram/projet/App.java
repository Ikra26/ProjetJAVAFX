package fr.ul.miage.ikram.projet;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    // attribute
    private static Scene scene;

    // method
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("VuePrincipale"), 700, 550);
        stage.setScene(scene);
        stage.setTitle("Simulateur de Baignoire");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(App.class.getResource("/vue/" + fxml + ".fxml"));
        return fxmlloader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
