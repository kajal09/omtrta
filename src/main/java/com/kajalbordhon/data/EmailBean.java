package com.kajalbordhon.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This bean contains all the information a email should have.
 *
 * @author kajal
 */
public class EmailBean {

    private int id = -1;
    private String from = null;
    private String[] to = null;
    private String[] cc = null;
    private String[] bcc = null;
    private String subject = null;
    private String textMessage = ""; // can never be null, it causes javax.mail.MessagingException: Empty multipart: multipart/mixed;
    private String htmlMessage = "";
    private AttachmentBean[] attachBean = null;
    private String folder = "Inbox";
    private Timestamp date;    // Timestamp is easier to work with than LocalDateTime

    public EmailBean() {
    }

    public EmailBean(String from, String[] to, String[] cc, String[] bcc, String subject, String textMessage, String htmlMessage, AttachmentBean[] ab, String folder, Timestamp date) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.textMessage = textMessage;
        this.htmlMessage = htmlMessage;
        this.attachBean = ab;
        this.folder = folder;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String[] getCc() {
        return cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getHtmlMessage() {
        return htmlMessage;
    }

    public AttachmentBean[] getAttachBean() {
        return attachBean;
    }

    public String getFolder() {
        return folder;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public void setHtmlMessage(String htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    public void setAttachBean(AttachmentBean[] attachBean) {
        this.attachBean = attachBean;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.from);
        hash = 97 * hash + Arrays.deepHashCode(this.to);
        hash = 97 * hash + Arrays.deepHashCode(this.cc);
        hash = 97 * hash + Objects.hashCode(this.subject);
        hash = 97 * hash + Objects.hashCode(this.textMessage);
        hash = 97 * hash + Objects.hashCode(this.htmlMessage);
        hash = 97 * hash + Arrays.deepHashCode(this.attachBean);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmailBean other = (EmailBean) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.textMessage, other.textMessage)) {
            return false;
        }
        if (!Objects.equals(this.htmlMessage, other.htmlMessage)) {
            return false;
        }
        if (!Arrays.deepEquals(this.to, other.to)) {
            return false;
        }
        if (!Arrays.deepEquals(this.cc, other.cc)) {
            return false;
        }

        /**
         * Sorting the 2 attachment arrays. For some reason sent and received
         * array of AttachmentBean aren't in the same order.
         */
        List<AttachmentBean> a = new ArrayList(Arrays.asList(this.attachBean));
        List<AttachmentBean> b = new ArrayList<>(Arrays.asList(other.attachBean));
        Collections.sort(a, (AttachmentBean a1, AttachmentBean a2) -> a1.getName().compareTo(a2.getName()));
        Collections.sort(b, (AttachmentBean a1, AttachmentBean a2) -> a1.getName().compareTo(a2.getName()));
        return a.equals(b);
    }

}
