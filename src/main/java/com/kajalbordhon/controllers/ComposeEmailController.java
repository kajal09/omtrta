package com.kajalbordhon.controllers;

import com.kajalbordhon.business.ReceiveEmail;
import com.kajalbordhon.business.SendEmail;
import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import com.kajalbordhon.data.EmailFxBean;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComposeEmailController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField toId;

    @FXML
    private TextField ccId;

    @FXML
    private TextField bccId;

    @FXML
    private TextField subjectId;

    @FXML
    private HBox attachmentHBox;

    @FXML
    private HTMLEditor htmlEditorId;

    private final static Logger LOG = LoggerFactory.getLogger(ComposeEmailController.class);
    EmailFxBean efb;
    ArrayList<AttachmentBean> att;

    @FXML
    void initialize() {
        efb = new EmailFxBean();
        att = new ArrayList<>();
        Bindings.bindBidirectional(toId.textProperty(), efb.toProperty());
        Bindings.bindBidirectional(ccId.textProperty(), efb.ccProperty());
        Bindings.bindBidirectional(bccId.textProperty(), efb.bccProperty());
        Bindings.bindBidirectional(subjectId.textProperty(), efb.subjectProperty());
    }

    @FXML
    void addAttach(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        for (File f : selectedFiles) {
            try {
                AttachmentBean attachment;
                File file = new File(f.getAbsolutePath());
                byte[] fileContent = Files.readAllBytes(file.toPath());
                attachment = new AttachmentBean(fileContent, file.getName(), false);
                att.add(attachment);
            } catch (IOException ex) {
                LOG.debug("addAttach", ex.getMessage());
            }

        }
        showAttachment();
    }

    @FXML
    void send(ActionEvent event) {
        try {
            SendEmail emailSender = new SendEmail();
            ReceiveEmail emailReceiver = new ReceiveEmail();
            AttachmentBean[] temp = new AttachmentBean[att.size()];
            temp = att.toArray(temp);

            efb.setAttachBean(temp);
            efb.setHtmlMessage(htmlEditorId.getHtmlText());
            efb.setDate((new Timestamp(System.currentTimeMillis()).toString()));
            efb.setFolder("Sent");

            EmailBean eb = efb.getEb();

            emailSender.send(eb);
            emailReceiver.getEmails();
            Stage stage = (Stage) toId.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            LOG.debug("send", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOG.debug("send", ex.getMessage());
        }
    }

    @FXML
    void discard(ActionEvent event) {
        Stage stage = (Stage) htmlEditorId.getScene().getWindow();
        stage.close();
    }

    private void showAttachment() {
        attachmentHBox.getChildren().clear();
        for (AttachmentBean e : att) {
            Button attach = new Button(e.getName());
            attach.setOnAction((ActionEvent event) -> {
                String name = ((Button) event.getSource()).getText();
                removeAttachment(name);
            });
            attach.setTooltip(new Tooltip("Onclick it will remove the attachment."));
            Image image = new Image(getClass().getResourceAsStream("/images/attachment.png"));
            ImageView iv = new ImageView(image);
            iv.setFitHeight(20);
            iv.setFitWidth(20);
            attach.setGraphic(iv);
            this.attachmentHBox.getChildren().addAll(attach);
        }
    }

    private void removeAttachment(String name) {
        int temp = -1;

        for (int i = 0; i < att.size(); i++) {
            if (att.get(i).getName().equals(name)) {
                temp = i;
            }

        }

        if (temp != -1) {
            att.remove(temp);
            showAttachment();
        }

    }

    public void reply(EmailFxBean fxBean) {
        efb.setToProperty(fxBean.fromProperty());
        efb.setCcProperty(fxBean.ccProperty());
        efb.setBccProperty(fxBean.bccProperty());
        efb.setSubjectProperty(fxBean.subjectProperty());
        for (AttachmentBean a : fxBean.getAttachBean()) {
            att.add(a);
        }
        showAttachment();
        htmlEditorId.setHtmlText(fxBean.getTextMessage() + fxBean.getHtmlMessage());
        Bindings.bindBidirectional(toId.textProperty(), efb.toProperty());
        Bindings.bindBidirectional(ccId.textProperty(), efb.ccProperty());
        Bindings.bindBidirectional(bccId.textProperty(), efb.bccProperty());
        Bindings.bindBidirectional(subjectId.textProperty(), efb.subjectProperty());

    }

}
