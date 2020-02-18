package com.kajalbordhon.controllers;

import com.kajalbordhon.persistemce.EmailDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.LoggerFactory;

public class AddFolderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField folderName;

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(AddFolderController.class);
    private FolderController fController;

    @FXML
    void addFolder(ActionEvent event) {
        String name = folderName.getText();
        if (!name.equals("")) {
            try {
                EmailDAO dao = new EmailDAO();
                dao.insertFolder(name);
                fController.reLoadFolders();
            } catch (SQLException ex) {
                LOG.info("addFolder SQLException");
            }
        }
    }

    @FXML
    void initialize() {
    }

    public void setfController(FolderController fController) {
        this.fController = fController;
    }

}
