package com.kajalbordhon.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.slf4j.LoggerFactory;

/**
 * This controller checks if the email and passwords are valid.
 *
 * @author kajal
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane anchorpane;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private TextField userEmail; // Value injected by FXMLLoader

    @FXML
    private PasswordField userPassword;

    @FXML
    private Label errorLabel;

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("LoginController init");
        this.resources = resources;
        this.location = location;
    }

    @FXML
    void english(ActionEvent event) {
        Locale locale = new Locale("en", "CA");
        resources = ResourceBundle.getBundle("MessagesBundle", locale);
        reload();
    }

    @FXML
    void french(ActionEvent event) {
        Locale locale = new Locale("fr", "CA");
        resources = ResourceBundle.getBundle("MessagesBundle", locale);
        reload();
    }

    private void reload() {
        Platform.runLater(() -> {
            LOG.info("Changing Language");
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
                loader.setResources(resources);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(this.getClass().getResource("/styles/login.css").toExternalForm());
                Stage stage = (Stage) anchorpane.getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
                stage.setScene(scene);
                stage.setTitle(resources.getString("Login"));
                stage.show();
                stage.centerOnScreen();
            } catch (IOException ex) {
                LOG.debug("initFolderLayout", ex.getMessage());
            }
        });
    }

    /**
     * Checking if the email and password are valid.
     *
     * @param event
     * @throws IOException
     */
    public void validLogin(ActionEvent event) throws IOException {
        LOG.info("Trying to login");
        boolean loggedIn = true;

        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host("smtp.gmail.com")
                .auth(userEmail.getText(), userPassword.getText())
                .debugMode(false)
                .buildSmtpMailServer();

        try (SendMailSession session = smtpServer.createSession()) {
            session.open();
        } catch (Exception ex) {
            LOG.debug("ValidLogin", ex.getMessage());
            loggedIn = false;
        }

        File f = new File("config.properties");
        if (f.exists()) {
            if (loggedIn) {
                LOG.info("Logged In");
                errorLabel.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
                errorLabel.setText("You've Logged In");
                switchScene();
            } else {
                LOG.info("LogIn failed");
                errorLabel.setStyle("-fx-background-color: linear-gradient(#ffffff, #ff0000);");
                errorLabel.setText("Login Failed");
            }
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Configuration.fxml"));
            loader.setResources(resources);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
            stage.setScene(scene);
            stage.setTitle("Config");
            stage.show();
            stage.centerOnScreen();
        }

    }

    /**
     * Switching scene.
     *
     * @throws IOException
     */
    public void switchScene() throws IOException {
        LOG.info("Switching scene");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RootLayout.fxml"));
            loader.setResources(resources);
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle(resources.getString("Email"));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException ex) {
            LOG.debug("switchScene", ex.getMessage());
            throw ex;
        }

    }

}
