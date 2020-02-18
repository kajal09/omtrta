package com.kajalbordhon.controllers;

import com.kajalbordhon.persistemce.EmailDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This controller shows the user all the folders in the database.
 *
 * @author kajal
 */
public class FolderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane folderAnchorPane;

    @FXML
    private TreeView<String> folderTreeView;

    private final static Logger LOG = LoggerFactory.getLogger(FolderController.class);
    private EmailDAO edao;
    Image icon;
    private EmailTableController eController;

    @FXML
    void initialize() throws SQLException {
        LOG.info("FolderController init");
        edao = new EmailDAO();
        loadFolders();
    }

    /**
     * Folder selected by the user to load the emails from.
     *
     * @param me
     * @throws SQLException
     */
    public void folderSelected(MouseEvent me) throws SQLException {
        LOG.info("Folder Selected");
        TreeItem<String> item = this.folderTreeView.getSelectionModel().getSelectedItem();
        String value = item.getValue();
        if (!value.equalsIgnoreCase("Folders") && !value.equals("")) {
            this.eController.populateEmails(value);
        }

    }

    public void reLoadFolders() throws SQLException {
        loadFolders();
    }

    /**
     * Load all folders
     *
     * @throws SQLException
     */
    private void loadFolders() throws SQLException {
        LOG.info("Loading Folders");
        icon = new Image(getClass().getResourceAsStream("/images/folder.png"));
        ImageView iv = new ImageView(this.icon);
        iv.setFitHeight(16);
        iv.setFitWidth(16);
        TreeItem<String> root = new TreeItem<>("Folders", iv);
        root.setExpanded(true);
        this.folderTreeView.setShowRoot(false);

        for (String f : edao.getAllFolders()) {
            LOG.info("Loading all Folder");
            iv = new ImageView(this.icon);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            TreeItem<String> name = new TreeItem<>(f, iv);
            root.getChildren().add(name);
        }
        this.folderTreeView.setRoot(root);

        this.folderTreeView.setCellFactory((TreeView<String> stringTreeView) -> {
            TreeCell<String> treeCell = new TreeCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                    }
                }
            };

            treeCell.setOnDragEntered((DragEvent event)
                    -> {
                treeCell.setStyle("-fx-background-color: aqua;");
            });

            treeCell.setOnDragExited((DragEvent event)
                    -> {
                treeCell.setStyle("");
            });

            treeCell.setOnDragOver((DragEvent event)
                    -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            treeCell.setOnDragDropped((DragEvent event)
                    -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    moveEmailToFolder(treeCell.getTreeItem().getValue(), db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            });

            return treeCell;
        });

    }

    private void moveEmailToFolder(String folder, String idString) {
        Platform.runLater(() -> {
            try {
                System.out.println("id: " + idString + "\nfolder: " + folder);
                int id = Integer.parseInt(idString);
                EmailDAO dao = new EmailDAO();
                dao.changeEmailFolder(id, folder);
                this.eController.populateEmails(this.folderTreeView.getSelectionModel().getSelectedItem().getValue());
            } catch (SQLException ex) {
                LOG.debug("moveEmailToFolder", ex.getMessage());
            }
        });

    }

    public EmailTableController geteController() {
        return eController;
    }

    public void seteController(EmailTableController eController) {
        this.eController = eController;
    }

    public TreeView<String> getFolderTreeView() {
        return folderTreeView;
    }

}
