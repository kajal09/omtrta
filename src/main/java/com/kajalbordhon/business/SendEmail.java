package com.kajalbordhon.business;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import com.kajalbordhon.persistemce.EmailDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.MailServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jodd.mail.RFC2822AddressParser;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

/**
 * Sends a email using jodd
 *
 * @author kajal
 */
public class SendEmail {

    private final static Logger LOG = LoggerFactory.getLogger(SendEmail.class);
    private EmailBean eb;
    private final String smtpServerName = "smtp.gmail.com";
    private final String emailSend = "send.1541986@gmail.com";
    private final String emailSendPwd = "kajal1541986";

    public SendEmail() {
    }

    /**
     * Calls the perform method. Saves the email to the database.
     *
     * @param eb
     * @throws java.sql.SQLException
     */
    public void send(EmailBean eb) throws SQLException, IllegalArgumentException {
        LOG.info("Sending email");
        this.eb = eb;
        if (checkEmailBean()) {
            sendMail();

            this.eb.setFolder("Sent");
            new EmailDAO().pushEmail(this.eb);  // Push Email to the Database
        } else {
            LOG.debug("send");
            throw new IllegalArgumentException("Invalid Email Address");
        }
    }

    /**
     * Set up the SMTP server
     */
    private void sendMail() {

        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host(smtpServerName)
                .auth(emailSend, emailSendPwd)
                .debugMode(false)
                .buildSmtpMailServer();
        LOG.info("SMTP server created");

        Email email = emailBuilder(); //call an private method to build the Email object
        LOG.info("Email created");

        try (SendMailSession session = smtpServer.createSession()) {
            session.open();
            session.sendMail(email);
            LOG.info("Email sent");
        }

    }

    /**
     * Create the Email object from the Email bean
     *
     * @return Email object
     */
    private Email emailBuilder() {
        LOG.info("Building the Email");

        Email email = Email.create();
        email.from(this.eb.getFrom());
        email.to(this.eb.getTo());
        email.subject(this.eb.getSubject());
        this.eb.setDate(new Timestamp(System.currentTimeMillis()));

        if (this.eb.getTextMessage() != null) {
            email.textMessage(this.eb.getTextMessage());
        }

        if (this.eb.getHtmlMessage() != null && !this.eb.getHtmlMessage().equals("")) {
            email.htmlMessage(this.eb.getHtmlMessage());
        }

        if (this.eb.getCc() != null) {
            email.cc(this.eb.getCc());
        }

        if (this.eb.getBcc() != null) {
            email.bcc(this.eb.getBcc());
        }

        if (this.eb.getAttachBean() != null) {

            for (AttachmentBean ab : this.eb.getAttachBean()) {

                if (!ab.isEmbedded()) { //regular attachment
                    email.attachment(EmailAttachment.with().name(ab.getName()).content(ab.getAttachment()));
                } else if (ab.isEmbedded()) { // embedded attachment
                    email.embeddedAttachment(EmailAttachment.with().name(ab.getName()).content(ab.getAttachment()));
                }

            }

        }

        return email;
    }

    /**
     * Checks if all the email addresses are correct.
     *
     * @return boolean
     */
    private boolean checkEmailBean() {
        if (!(checkEmail(this.eb.getFrom()))) {
            return false;
        }

        if (this.eb.getTo() != null) {
            for (String e : this.eb.getTo()) {
                if (!(checkEmail(e))) {
                    return false;
                }
            }
        }

        if (this.eb.getCc() != null) {
            for (String e : this.eb.getCc()) {
                if (!(checkEmail(e))) {
                    return false;
                }
            }
        }

        if (this.eb.getBcc() != null) {
            for (String e : this.eb.getBcc()) {
                if (!(checkEmail(e))) {
                    return false;
                }
            }
        }

        if (this.eb.getAttachBean() != null) {
            for (AttachmentBean ab : this.eb.getAttachBean()) {
                if (ab == null) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkEmail(String address) {
        return RFC2822AddressParser.STRICT.parseToEmailAddress(address) != null;
    }

}
