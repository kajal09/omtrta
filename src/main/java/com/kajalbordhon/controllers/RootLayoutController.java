package com.kajalbordhon.controllers;

import com.kajalbordhon.business.ReceiveEmail;
import com.kajalbordhon.data.EmailFxBean;
import com.kajalbordhon.persistemce.EmailDAO;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * This controller controls all the other controllers.
 *
 * @author kajal
 */
public class RootLayoutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar menuBarId;

    @FXML
    private ToolBar toolboxId;

    @FXML
    private AnchorPane folderAnchor;

    @FXML
    private AnchorPane emailAnchor;

    @FXML
    private AnchorPane webAnchor;

    @FXML
    private Button buttonCompose;

    @FXML
    private Button buttonReply;

    @FXML
    private Button buttonReplyAll;

    @FXML
    private Button buttonForward;

    @FXML
    private Button buttonDeleteFolder;

    @FXML
    private Button buttonAddFolder;

    @FXML
    private Button buttonRenameFolder;

    @FXML
    private Button buttonRefresh;

    @FXML
    private Button DeleteEmail;

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(RootLayoutController.class);
    private FolderController fController;
    private EmailTableController eController;
    private WebViewLayoutController wController;
    private ComposeEmailController cController;
    private AddFolderController addFController;
    private RenameFolderController renameFController;

    @FXML
    void initialize() throws IOException {
        LOG.info("RootLayoutController init");
        Platform.runLater(() -> {
            buttonCompose.setGraphic(getImageView("compose.png"));
            buttonReply.setGraphic(getImageView("reply.png"));
            buttonReplyAll.setGraphic(getImageView("replyAll.png"));
            buttonForward.setGraphic(getImageView("forward.png"));
            buttonDeleteFolder.setGraphic(getImageView("deleteFolder.png"));
            buttonAddFolder.setGraphic(getImageView("addFolder.png"));
            buttonRenameFolder.setGraphic(getImageView("renameFolder.png"));
            buttonRefresh.setGraphic(getImageView("refresh.png"));
            DeleteEmail.setGraphic(getImageView("deleteEmail.png"));
        });
        initWebLayout();
        initTableLayout();
        initFolderLayout();

    }

    /**
     * Setup the FolderLayout
     *
     */
    private void initFolderLayout() {
        Platform.runLater(() -> {
            LOG.info("Setting up FolderLayout");
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Folder.fxml"));

                AnchorPane treeView = (AnchorPane) loader.load();
                treeView.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
                fController = loader.getController();
                fController.seteController(eController);
                folderAnchor.getChildren().add(treeView);
            } catch (IOException ex) {
                LOG.debug("initFolderLayout", ex.getMessage());
            }
        });

    }

    /**
     * Setup the TableLayout
     *
     */
    private void initTableLayout() {
        Platform.runLater(() -> {
            LOG.info("Setting up TableLayout");
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/EmailTable.fxml"));
                loader.setResources(resources);

                AnchorPane treeView = (AnchorPane) loader.load();
                treeView.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
                eController = loader.getController();
                eController.setwController(wController);

                emailAnchor.getChildren().add(treeView);
            } catch (IOException ex) {
                LOG.debug("initTableLayout", ex.getMessage());
            }
        });

    }

    /**
     * Setup the WebLayout
     *
     */
    private void initWebLayout() {
        Platform.runLater(() -> {
            LOG.info("Setting up WebLayout");
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/WebViewLayout.fxml"));
                loader.setResources(resources);

                AnchorPane treeView = (AnchorPane) loader.load();
                treeView.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
                wController = loader.getController();

                webAnchor.getChildren().add(treeView);
            } catch (IOException ex) {
                LOG.debug("initWebLayout", ex.getMessage());
            }
        });

    }

    /**
     * Calls composeCreator() when the Compose button is pressed.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void compose(ActionEvent event) throws IOException {
        composeCreator();
    }

    /**
     * This is in a separate method so that other methods can call it much more
     * easily. Since, it is needed for reply/forward.
     *
     * @throws IOException
     */
    private void composeCreator() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ComposeEmail.fxml"));
            loader.setResources(resources);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
            cController = loader.getController();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
            stage.setScene(scene);
            stage.setTitle(resources.getString("Compose"));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException ex) {
            LOG.debug("compose", ex.getMessage());
        }
    }

    /**
     * Calls replyToEmail with a fx bean that does not contain CC and BCC. It
     * also adds 'RE' to subject.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void reply(ActionEvent event) throws IOException {

        EmailFxBean efb = eController.getEmailTableView().getSelectionModel().getSelectedItem();
        if (efb != null) {
            efb.setBcc("");
            efb.setCc("");
            efb.setSubject("RE " + efb.getSubject());
            replyToEmail(efb);
        }
    }

    /**
     * Calls replyToEmail with a fx bean that does not contain BCC. It also adds
     * 'RE' to subject.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void replyAll(ActionEvent event) throws IOException {
        EmailFxBean efb = eController.getEmailTableView().getSelectionModel().getSelectedItem();
        if (efb != null) {
            efb.setBcc("");
            efb.setSubject("RE " + efb.getSubject());
            replyToEmail(efb);
        }
    }

    /**
     * Calls replyToEmail with a fx bean that does not contain BCC. It also adds
     * 'FWD' to subject.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void forward(ActionEvent event) throws IOException {
        EmailFxBean efb = eController.getEmailTableView().getSelectionModel().getSelectedItem();
        if (efb != null) {
            efb.setBcc("");
            efb.setSubject("FWD " + efb.getSubject());
            replyToEmail(efb);
        }
    }

    /**
     * Reply to email using the fx bean.
     *
     * @param efb
     */
    private void replyToEmail(EmailFxBean efb) {
        Platform.runLater(() -> {
            try {
                composeCreator();
                cController.reply(efb);
            } catch (IOException ex) {
                LOG.debug("replyToEmail", ex.getMessage());
            }

        });

    }

    /**
     * Delete the currently selected folder.
     *
     * @param event
     */
    @FXML
    void deleteFolder(ActionEvent event) {
        try {
            TreeItem<String> item = fController.getFolderTreeView().getSelectionModel().getSelectedItem();
            String value = item.getValue();

            if (!value.equalsIgnoreCase("Folders") && !value.equals("") && !value.equalsIgnoreCase("Inbox") && !value.equalsIgnoreCase("Sent")) {
                EmailDAO dao = new EmailDAO();
                dao.deleteFolder(value);
                fController.reLoadFolders();
            }
        } catch (NullPointerException ex) {
            LOG.info("deleteFolder NullPointerException");
        } catch (SQLException ex) {
            LOG.info("deleteFolder SQLException");
        }

    }

    /**
     * Open a new scene that let's you add folders.
     *
     * @param event
     */
    @FXML
    void addFolder(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/AddFolder.fxml"));
                loader.setResources(resources);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
                addFController = loader.getController();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
                stage.setScene(scene);
                stage.setTitle(resources.getString("AddFolder"));
                addFController.setfController(fController);
                stage.show();
                stage.centerOnScreen();
            } catch (IOException ex) {
                LOG.debug("addFolder", ex);
            }
        });
    }

    /**
     * Let's you rename the selected folder.
     *
     * @param event
     */
    @FXML
    void renameFolder(ActionEvent event) {
        try {
            TreeItem<String> item = fController.getFolderTreeView().getSelectionModel().getSelectedItem();
            String value = item.getValue();

            if (!value.equalsIgnoreCase("Folders") && !value.equals("") && !value.equalsIgnoreCase("Inbox") && !value.equalsIgnoreCase("Sent")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/RenameFolder.fxml"));
                loader.setResources(resources);
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(this.getClass().getResource("/styles/root.css").toExternalForm());
                renameFController = loader.getController();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
                stage.setScene(scene);
                stage.setTitle(resources.getString("RenameFolder"));
                renameFController.setfController(fController);
                renameFController.setOldName(value);
                stage.show();
                stage.centerOnScreen();
            }
        } catch (NullPointerException ex) {
            LOG.info("deleteFolder NullPointerException");
        } catch (IOException ex) {
            LOG.info("deleteFolder IOException");
        }
    }

    /**
     * Open the projects gitLab page. Since, it's private. Only me and Ken can
     * see it.
     *
     * @param event
     * @throws URISyntaxException
     * @throws IOException
     */
    @FXML
    void about(ActionEvent event) throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("https://gitlab.com/kajal09/omtrta"));
    }

    /**
     * Load an image into ImageView
     *
     * @param name
     * @return
     */
    private ImageView getImageView(String name) {
        Image image = new Image(getClass().getResourceAsStream("/images/" + name));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(20);
        iv.setFitWidth(20);
        return iv;
    }

    @FXML
    void refresh(ActionEvent event) {
        try {
            reloadEmails();
            eController.populateEmails("Inbox");
        } catch (SQLException ex) {
            LOG.debug("refresh", ex);
        }
    }

    private void reloadEmails() {
        Platform.runLater(() -> {
            try {
                ReceiveEmail receive = new ReceiveEmail();
                receive.getEmails();
                eController.populateEmails("Inbox");
            } catch (SQLException ex) {
                LOG.debug("reloadEmails", ex);
            }
        });
    }

    /**
     * Move the email to delete folder.
     *
     * @param event
     */
    @FXML
    void deleteEmail(ActionEvent event) {
        EmailFxBean efb = eController.getEmailTableView().getSelectionModel().getSelectedItem();
        if (efb != null) {
            Platform.runLater(() -> {
                try {
                    int id = efb.getId();
                    EmailDAO dao = new EmailDAO();
                    dao.changeEmailFolder(id, "Deleted");
                    this.eController.populateEmails(fController.getFolderTreeView().getSelectionModel().getSelectedItem().getValue());
                    fController.reLoadFolders();
                } catch (SQLException ex) {
                    LOG.debug("deleteEmail", ex.getMessage());
                }
            });
        }
    }
}
