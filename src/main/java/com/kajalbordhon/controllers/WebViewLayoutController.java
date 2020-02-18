package com.kajalbordhon.controllers;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailFxBean;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This controller shows the user the content of the EmailFxBean.
 *
 * @author kajal
 */
public class WebViewLayoutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label fromId;

    @FXML
    private Label toId;

    @FXML
    private Label ccId;

    @FXML
    private Label subjectId;

    @FXML
    private HBox attachmentHBox;

    @FXML
    private WebView webViewId;

    private final static Logger LOG = LoggerFactory.getLogger(WebViewLayoutController.class);
    private WebEngine engine;

    @FXML
    void initialize() {
        LOG.info("WebViewLayout init");
        engine = webViewId.getEngine();

    }

    /**
     * Display the content of the EmailFxBean
     *
     * @param efb
     */
    public void display(EmailFxBean efb) {
        LOG.info("Displaying email");
        this.fromId.setText("  " + efb.getFrom());
        this.toId.setText("  " + efb.getTo());
        this.ccId.setText("  " + efb.getCc());
        this.subjectId.setText("  " + efb.getSubject());
        String message = "" + efb.getTextMessage() + efb.getHtmlMessage();
        this.engine.loadContent(message);
        displayAttachments(efb);
    }

    /**
     * Display all the attachments in EmailFxBean.
     *
     * @param efb
     */
    private void displayAttachments(EmailFxBean efb) {
        this.attachmentHBox.getChildren().clear();

        for (AttachmentBean e : efb.getAttachBean()) {
            Button attach = new Button(e.getName());
            attach.setOnAction((ActionEvent event) -> {
                String name = ((Button) event.getSource()).getText();
                writeAttachmentsToFile(name, efb);
            });
            attach.setTooltip(new Tooltip("Onclick the file will be saved to desktop(or home dir on linux)."));
            Image image = new Image(getClass().getResourceAsStream("/images/attachment.png"));
            ImageView iv = new ImageView(image);
            iv.setFitHeight(20);
            iv.setFitWidth(20);
            attach.setGraphic(iv);
            this.attachmentHBox.getChildren().addAll(attach);
        }

    }

    /**
     * Saves the clicked attachement to desktop for windows and home directory
     * for linux.
     *
     * @param name
     * @param efb
     */
    private void writeAttachmentsToFile(String name, EmailFxBean efb) {
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");
        String path;

        if (os.contains("Windows")) {
            path = home + "\\Desktop\\" + name;         // We could let the user choose the dir in the future.
        } else {
            path = home + "\\" + name;
        }

        for (AttachmentBean e : efb.getAttachBean()) {
            if (e.getName().equals(name)) {
                File f = new File(path);
                if (!f.exists() && !f.isDirectory()) {
                    Platform.runLater(() -> {
                        try {
                            FileUtils.writeByteArrayToFile(f, e.getAttachment());
                        } catch (IOException ex) {
                            LOG.debug("writeAttachmentsToFile", ex.getMessage());
                        }
                    });

                }
            }
        }

    }

}
