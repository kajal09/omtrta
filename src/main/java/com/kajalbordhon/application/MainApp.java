package com.kajalbordhon.application;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main method.
 *
 * @author kajal
 */
public class MainApp extends Application {

    private final static Logger LOG = LoggerFactory.getLogger(MainApp.class);

    /**
     * Application start
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
            Locale locale = Locale.getDefault();
            LOG.info("Locale = " + locale);
            loader.setResources(ResourceBundle.getBundle("MessagesBundle", locale));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("/styles/login.css").toExternalForm());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
            stage.setScene(scene);
            stage.setTitle(ResourceBundle.getBundle("MessagesBundle", locale).getString("Login"));
            stage.show();
            stage.centerOnScreen();
            LOG.info("MainApp launched");
        } catch (Exception ex) {
            // TODO popup with an apology.
            LOG.error(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
