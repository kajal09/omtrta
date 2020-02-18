package com.kajalbordhon.data;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kajal
 */
public class EmailFxBean {

    private final static Logger LOG = LoggerFactory.getLogger(EmailFxBean.class);
    private IntegerProperty id;
    private StringProperty from;
    private StringProperty to; //arr
    private StringProperty cc; //arr
    private StringProperty bcc; //arr
    private StringProperty subject;
    private StringProperty textMessage;
    private StringProperty htmlMessage;
    private AttachmentBean[] attachBean;
    private StringProperty folder;
    private StringProperty date;
    private EmailBean eb;

    public EmailFxBean() {
        this.id = new SimpleIntegerProperty(-1);
        this.from = new SimpleStringProperty("send.1541986@gmail.com");
        this.to = new SimpleStringProperty("");
        this.cc = new SimpleStringProperty("");
        this.bcc = new SimpleStringProperty("");
        this.subject = new SimpleStringProperty("");
        this.textMessage = new SimpleStringProperty("");
        this.htmlMessage = new SimpleStringProperty("");
        this.folder = new SimpleStringProperty("Inbox");
    }

    public EmailFxBean(int id, String from, String[] to, String[] cc, String[] bcc, String subject, String textMessage, String htmlMessage, AttachmentBean[] att, String folder, Timestamp date) {
        this.id = new SimpleIntegerProperty(id);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(String.join(";", to));
        this.cc = new SimpleStringProperty(String.join(";", cc));
        this.bcc = new SimpleStringProperty(String.join(";", bcc));
        this.subject = new SimpleStringProperty(subject);
        this.textMessage = new SimpleStringProperty(textMessage);
        this.htmlMessage = new SimpleStringProperty(htmlMessage);
        this.folder = new SimpleStringProperty(folder);
        this.date = new SimpleStringProperty(date.toString());
        this.attachBean = att;
    }

    public EmailFxBean(EmailBean eb) {
        ebToFx(eb);
    }

    private void ebToFx(EmailBean eb) {
        this.eb = eb;
        this.id = new SimpleIntegerProperty(eb.getId());
        this.from = new SimpleStringProperty(eb.getFrom());
        this.to = new SimpleStringProperty(String.join(";", eb.getTo()));
        this.cc = new SimpleStringProperty(String.join(";", eb.getCc()));
        this.bcc = new SimpleStringProperty(String.join(";", eb.getBcc()));
        this.subject = new SimpleStringProperty(eb.getSubject());
        this.textMessage = new SimpleStringProperty(eb.getTextMessage());
        this.htmlMessage = new SimpleStringProperty(eb.getHtmlMessage());
        this.folder = new SimpleStringProperty(eb.getFolder());
        this.date = new SimpleStringProperty(eb.getDate().toString());
        this.attachBean = eb.getAttachBean();
    }

    private EmailBean fxToEb() {
        EmailBean bean = new EmailBean();
        bean.setId(this.id.getValue());
        bean.setFrom(this.from.get());
        bean.setTo(this.to.get().split(";"));
        bean.setCc(this.cc.get().split(";"));
        bean.setBcc(this.bcc.get().split(";"));
        bean.setSubject(this.subject.get());
        bean.setTextMessage(this.textMessage.get());
        bean.setHtmlMessage(this.htmlMessage.get());
        bean.setFolder(this.folder.get());
        bean.setDate(Timestamp.valueOf(this.date.get()));
        bean.setAttachBean(attachBean);

        if (this.cc.get().equals("")) {
            bean.setCc(null);
        }
        if (this.bcc.get().equals("")) {
            bean.setBcc(null);
        }

        return bean;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getFrom() {
        return from.get();
    }

    public void setFrom(String from) {
        this.from = new SimpleStringProperty(from);
    }

    public String getTo() {
        return to.get();
    }

    public void setTo(String to) {
        this.to = new SimpleStringProperty(to);
    }

    public String getCc() {
        return cc.get();
    }

    public void setCc(String cc) {
        this.cc = new SimpleStringProperty(cc);
    }

    public String getBcc() {
        return bcc.get();
    }

    public void setBcc(String bcc) {
        this.bcc = new SimpleStringProperty(bcc);
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject = new SimpleStringProperty(subject);
    }

    public String getTextMessage() {
        return textMessage.get();
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = new SimpleStringProperty(textMessage);
    }

    public String getHtmlMessage() {
        return htmlMessage.get();
    }

    public void setHtmlMessage(String htmlMessage) {
        this.htmlMessage = new SimpleStringProperty(htmlMessage);
    }

    public String getFolder() {
        return folder.get();
    }

    public void setFolder(String folder) {
        this.folder = new SimpleStringProperty(folder);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }

    public EmailBean getEb() {
        return fxToEb();
    }

    public void setEb(EmailBean eb) {
        ebToFx(eb);
    }

    //----------------------------------------
    public int idProperty() {
        return id.get();
    }

    public void setIdProperty(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty fromProperty() {
        return this.from;
    }

    public void setFromProperty(StringProperty from) {
        this.from = from;
    }

    public StringProperty toProperty() {
        return this.to;
    }

    public void setToProperty(StringProperty to) {
        this.to = to;
    }

    public StringProperty ccProperty() {
        return cc;
    }

    public void setCcProperty(StringProperty cc) {
        this.cc = cc;
    }

    public StringProperty bccProperty() {
        return bcc;
    }

    public void setBccProperty(StringProperty bcc) {
        this.bcc = bcc;
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public void setSubjectProperty(StringProperty subject) {
        this.subject = subject;
    }

    public StringProperty textMessageProperty() {
        return textMessage;
    }

    public void setTextMessageProperty(StringProperty textMessage) {
        this.textMessage = textMessage;
    }

    public StringProperty htmlMessageProperty() {
        return htmlMessage;
    }

    public void setHtmlMessageProperty(StringProperty htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    public AttachmentBean[] getAttachBean() {
        return attachBean;
    }

    public void setAttachBean(AttachmentBean[] attachBean) {
        this.attachBean = attachBean;
    }

    public StringProperty folderProperty() {
        return folder;
    }

    public void setFolderProperty(StringProperty folder) {
        this.folder = folder;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDateProperty(StringProperty date) {
        this.date = date;
    }

}
