package com.kajalbordhon.business;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import com.kajalbordhon.persistemce.EmailDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import jodd.mail.ImapServer;
import jodd.mail.MailServer;
import jodd.mail.ReceiveMailSession;
import javax.mail.Flags;
import jodd.mail.EmailAddress;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ReceivedEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get an array of EmailBean from the ImapServer.
 *
 * @author kajal
 */
public class ReceiveEmail {

    private final static Logger LOG = LoggerFactory.getLogger(ReceiveEmail.class);
    private final ArrayList<EmailBean> mailList = new ArrayList<>();
    private final String imapServerName = "imap.gmail.com";
    private final String emailReceive = "receive.1541986@gmail.com";
    private final String emailReceivePwd = "kajal1541986";

    public ReceiveEmail() {
    }

    /**
     * Getting all the emails from the SMTP server.
     *
     * @return
     * @throws SQLException
     */
    public EmailBean[] getEmails() throws SQLException {
        LOG.info("Getting all emails from the ImapServer");
        return getThemEmails();
    }

    /**
     * Set up the ImapServer and get all the emails
     *
     * @return array of email Beans
     * @throws java.sql.SQLException
     */
    private EmailBean[] getThemEmails() throws SQLException {
        LOG.info("Setting up the ImapServer");

        ImapServer imapServer = MailServer.create()
                .host(imapServerName)
                .ssl(true)
                .auth(emailReceive, emailReceivePwd)
                .buildImapMailServer();

        try (ReceiveMailSession session = imapServer.createSession()) {
            session.open();
            ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter.filter().flag(Flags.Flag.SEEN, false));

            if (emails != null) {

                for (ReceivedEmail email : emails) {
                    ArrayList<AttachmentBean> attachArray = new ArrayList<>();

                    //get Attachments
                    List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                    if (attachments != null) {
                        for (EmailAttachment attachment : attachments) {
                            AttachmentBean attachBean = new AttachmentBean(attachment.toByteArray(), attachment.getName(), attachment.isEmbedded()); //byte[] attachment, String name, boolean embedded
                            attachArray.add(attachBean);
                        }
                    }

                    String from = email.from().getEmail();
                    String[] to = this.getTo(email.to());
                    String[] cc = this.getCc(email.cc());
                    Timestamp timestamp = new Timestamp(email.receivedDate().getTime());
                    if (email.cc().length < 1) {
                        cc = null;
                    }
                    String[] bcc = null;
                    String subject = email.subject();
                    String textMessage = "";
                    String htmlMessage = "";

                    AttachmentBean[] ab = new AttachmentBean[attachArray.size()];
                    ab = attachArray.toArray(ab);
                    if (ab.length < 1) {
                        ab = null;
                    }

                    //process messages
                    List<EmailMessage> messages = email.messages();

                    for (EmailMessage em : messages) {
                        if (em.getMimeType().equals("TEXT/PLAIN")) {
                            textMessage += em.getContent();
                        } else if (em.getMimeType().equals("TEXT/HTML")) {
                            htmlMessage += em.getContent();
                        } else {
                            textMessage += em.getContent();
                        }

                    }
                    EmailBean temp = new EmailBean(from, to, cc, bcc, subject, textMessage.trim(), htmlMessage, ab, "Inbox", timestamp);
                    this.mailList.add(temp);
                    new EmailDAO().pushEmail(temp);  //push email to the Database
                }

            }

        }

        EmailBean[] arrayBean = new EmailBean[this.mailList.size()];
        arrayBean = this.mailList.toArray(arrayBean);
        return arrayBean;
    }

    /**
     * Convert all the EmailAddress array to String array
     *
     * @param array
     * @return String array
     */
    private String[] getTo(EmailAddress[] array) {
        ArrayList<String> list = new ArrayList<>();

        for (EmailAddress ea : array) {
            list.add(ea.getEmail());
        }

        String[] temp = new String[list.size()];

        return list.toArray(temp);
    }

    /**
     * Convert all the EmailAddress array to String array
     *
     * @param array
     * @return String array
     */
    private String[] getCc(EmailAddress[] array) {
        return getTo(array);
    }

}
