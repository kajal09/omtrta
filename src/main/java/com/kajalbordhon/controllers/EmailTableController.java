package com.kajalbordhon.controllers;

import com.kajalbordhon.data.EmailBean;
import com.kajalbordhon.data.EmailFxBean;
import com.kajalbordhon.persistemce.EmailDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This controller shows the user all the emails in a specific folder.
 *
 * @author kajal
 */
public class EmailTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane emailTableAnchorPane;

    @FXML
    private TableView<EmailFxBean> emailTableView;

    @FXML
    private TableColumn<EmailFxBean, String> EmailTableSender;

    @FXML
    private TableColumn<EmailFxBean, String> emailTableSubject;

    @FXML
    private TableColumn<EmailFxBean, String> emailTableReceived;

    private final static Logger LOG = LoggerFactory.getLogger(EmailTableController.class);

    private ObservableList<EmailFxBean> list;

    private EmailDAO edao;

    private WebViewLayoutController wController;

    @FXML
    void initialize() throws SQLException {
        LOG.info("EmailTableController init");
        edao = new EmailDAO();

        emailTableView.setOnDragDetected((MouseEvent event) -> {
            LOG.debug("onDragDetected");
            Dragboard db = emailTableView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(this.emailTableView.getSelectionModel().getSelectedItem().getId() + "");
            db.setContent(content);
            event.consume();
        });
    }

    /**
     * Takes all the emails from the specified folder and populate the table
     *
     * @param folder
     * @throws SQLException
     */
    public void populateEmails(String folder) throws SQLException {
        Platform.runLater(() -> {
            try {
                LOG.info("Populating the table with emails from the folder " + folder);
                this.list = FXCollections.observableArrayList();

                for (EmailBean e : edao.pullEmailByFolder(folder)) {
                    this.list.add(new EmailFxBean(e));
                }

                EmailTableSender.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
                emailTableSubject.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
                emailTableReceived.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
                emailTableView.setItems(list);
            } catch (SQLException ex) {
                LOG.debug("populateEmails", ex.getMessage());
            }
        });

    }

    /**
     * Event that checks which email is selected for display.
     *
     * @param me
     */
    public void emailSelected(MouseEvent me) {
        LOG.info("emailSelected");
        EmailFxBean efb = this.emailTableView.getSelectionModel().getSelectedItem();

        if (efb != null) {
            this.wController.display(efb);
        }
    }

    @FXML
    private void dragDetected(MouseEvent event) {
        LOG.debug("DragDetected");
        Dragboard db = this.emailTableView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(this.emailTableView.getSelectionModel().getSelectedItem().getId() + "");
        db.setContent(content);
        event.consume();
    }

    public WebViewLayoutController getwController() {
        return wController;
    }

    public void setwController(WebViewLayoutController wController) {
        this.wController = wController;
    }

    public TableView<EmailFxBean> getEmailTableView() {
        return emailTableView;
    }
}
