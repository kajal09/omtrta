package com.kajalbordhon.controllers;

import com.kajalbordhon.util.PropertiesManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

public class ConfigurationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField smtpServer;

    @FXML
    private TextField imapServer;

    @FXML
    private TextField dataName;

    @FXML
    private TextField dataUrl;

    @FXML
    private TextField smtpPort;

    @FXML
    private TextField imapPort;

    @FXML
    private TextField dataPort;

    @FXML
    private TextField dataUser;

    @FXML
    private PasswordField emailPassword;

    @FXML
    private PasswordField dataPass;

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(WebViewLayoutController.class);

    @FXML
    void submit(ActionEvent event) {
        PropertiesManager property = new PropertiesManager();
        try {
            property.writeTextProperties("", "config", name.getText(), email.getText(), smtpServer.getText(), imapServer.getText(), dataName.getText(), dataUrl.getText(), smtpPort.getText(), imapPort.getText(), dataPort.getText(), dataUser.getText(), emailPassword.getText(), dataPass.getText());
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            LOG.debug("submit", ex.getMessage());
        }
    }

    @FXML
    void initialize() {

    }
}
