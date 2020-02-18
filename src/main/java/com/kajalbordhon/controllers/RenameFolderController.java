package com.kajalbordhon.controllers;

import com.kajalbordhon.persistemce.EmailDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * Rename the selected folder.
 *
 * @author kajal
 */
public class RenameFolderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField folderName;

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(RenameFolderController.class);
    private FolderController fController;
    private String oldName;

    @FXML
    void renameFolder(ActionEvent event) {
        String name = folderName.getText();
        if (!name.equals("")) {
            try {
                EmailDAO dao = new EmailDAO();
                dao.renameFolder(oldName, name);
                fController.reLoadFolders();
                Stage stage = (Stage) folderName.getScene().getWindow();
                stage.close();

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

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

}
