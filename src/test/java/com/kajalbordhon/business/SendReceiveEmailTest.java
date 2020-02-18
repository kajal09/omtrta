package com.kajalbordhon.business;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test send and receive objects
 *
 * @author kajal
 */
public class SendReceiveEmailTest {

    private final static Logger LOG = LoggerFactory.getLogger(SendReceiveEmailTest.class);

    public SendReceiveEmailTest() {
    }

    /**
     * Sends and receive an email with subject only.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailSubjectOnly() throws SQLException, IllegalArgumentException, InterruptedException {
        LOG.info("Testing testEmailSubjectOnly");

        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String subject = "subject only" + LocalDateTime.now();

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setSubject(subject);
        SendEmail send = new SendEmail();
        send.send(mymail);

        //delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailSubjectOnly", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(gotThemEmails[0].getSubject(), mymail.getSubject());
    }

    /**
     * Sends and receive an email with body only.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailBodyOnly() throws SQLException, IllegalArgumentException, InterruptedException {
        LOG.info("Testing testEmailBodyOnly");

        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setTextMessage("This is Body Only");
        SendEmail send = new SendEmail();
        send.send(mymail);

        //delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailBodyOnly", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(gotThemEmails[0].getTextMessage(), mymail.getTextMessage());
    }

    /**
     * Sends and receive an email with plain text and no html.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailWithPlainText() throws SQLException, IllegalArgumentException, InterruptedException {
        LOG.info("Testing testEmailWithPlainText");

        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String subject = "subject" + LocalDateTime.now();
        String textMessage = "This is a plain text message";

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setSubject(subject);
        mymail.setTextMessage(textMessage);
        SendEmail send = new SendEmail();
        send.send(mymail);

        //delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailWithPlainText", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(gotThemEmails[0].getTextMessage(), textMessage);
    }

    /**
     * Sends and receive an email with html and no plain text.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailWithHtml() throws SQLException, IllegalArgumentException, InterruptedException {
        LOG.info("Testing testEmailWithHtml");

        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String subject = "subject" + LocalDateTime.now();
        String htmlMessage = "<!DOCTYPE html><html><head><title>Page Title</title></head><body><h1>This is a HTML message</h1></body></html>";

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setSubject(subject);
        mymail.setHtmlMessage(htmlMessage);
        SendEmail send = new SendEmail();
        send.send(mymail);

        //delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailWithHtml", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(gotThemEmails[0].getHtmlMessage(), htmlMessage);
    }

    /**
     * Sends and receive an email with attachement.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailWithAttachment() throws SQLException, IllegalArgumentException, InterruptedException {
        LOG.info("Testing testEmailWithAttachment");
        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String subject = "subject" + LocalDateTime.now();
        String htmlMessage = "<!DOCTYPE html><html><head><title>Page Title</title></head><body><h1>This is a HTML message</h1></body></html>";

        AttachmentBean picAttach = null;
        try {

            //attachment
            File file = new File("coffee.jpg");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            picAttach = new AttachmentBean(fileContent, file.getName(), false);
        } catch (IOException ex) {
            // Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        AttachmentBean[] ab = new AttachmentBean[]{picAttach};

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setSubject(subject);
        mymail.setHtmlMessage(htmlMessage);
        mymail.setAttachBean(ab);
        SendEmail send = new SendEmail();
        send.send(mymail);

        //delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailWithAttachment", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(mymail, gotThemEmails[0]);
    }

    /**
     * Sends and receive an email with embedded attachement.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailWithEmbbeddedAttachment() throws SQLException, IllegalArgumentException, IOException, InterruptedException {
        LOG.info("Testing testEmailWithEmbbeddedAttachment");
        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String subject = "subject" + LocalDateTime.now();
        String htmlMessage = "<!DOCTYPE html><html><head><title>Page Title</title></head><body><h1>This is a HTML message</h1><img src='cid:fine.jpg'></body></html>";

        AttachmentBean picAttach = null;
        try {

            //attachment
            File file = new File("fine.jpg");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            picAttach = new AttachmentBean(fileContent, file.getName(), true);
        } catch (IOException ex) {
            LOG.debug("testEmailWithEmbbeddedAttachment", ex.getMessage());
            throw ex;
        }
        AttachmentBean[] ab = new AttachmentBean[]{picAttach};

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setSubject(subject);
        mymail.setHtmlMessage(htmlMessage);
        mymail.setAttachBean(ab);
        SendEmail send = new SendEmail();
        send.send(mymail);

        //delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailWithEmbbeddedAttachment", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(mymail, gotThemEmails[0]);
    }

    /**
     * Sends and receive an email with multiple attachement.
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testEmailWithMultipleAttachmentMixed() throws SQLException, IllegalArgumentException, IOException, InterruptedException {
        LOG.info("Testing testEmailWithMultipleAttachmentMixed");
        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String[] cc = new String[]{"other.1541986@gmail.com"};
        String[] bcc = null;
        String subject = "TEST EMAIL   " + LocalDateTime.now();
        String textMessage = "This is a plain text message";
        String htmlMessage = "<!DOCTYPE html><html><head><title>Page Title</title></head><body><h1>This is a HTML message</h1><img src='cid:why.jpg'></body></html>";
        AttachmentBean[] ab;

        AttachmentBean picAttach = null;
        AttachmentBean picAttach2 = null;
        AttachmentBean picAttach3 = null;
        AttachmentBean picEmb = null;
        try {

            //attachment
            File file = new File("coffee.jpg");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            picAttach = new AttachmentBean(fileContent, file.getName(), false);

            //attachment
            file = new File("cat.jpg");
            byte[] fileContent2 = Files.readAllBytes(file.toPath());
            picAttach2 = new AttachmentBean(fileContent2, file.getName(), false);

            //attachment
            file = new File("fine.jpg");
            byte[] fileContent4 = Files.readAllBytes(file.toPath());
            picAttach3 = new AttachmentBean(fileContent4, file.getName(), false);

            //embedded
            file = new File("why.jpg");
            byte[] fileContent3 = Files.readAllBytes(file.toPath());
            picEmb = new AttachmentBean(fileContent3, file.getName(), true);
        } catch (IOException ex) {
            LOG.debug("testEmailWithMultipleAttachmentMixed", ex.getMessage());
            throw ex;
        }
        ab = new AttachmentBean[]{picAttach, picEmb, picAttach3, picAttach2};

        EmailBean mymail = new EmailBean(from, to, cc, bcc, subject, textMessage, htmlMessage, ab, null, null);
        SendEmail send = new SendEmail();
        send.send(mymail);

        //Delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.debug("testEmailWithMultipleAttachmentMixed", ex.getMessage());
            throw ex;
        }

        //receive
        ReceiveEmail re = new ReceiveEmail();
        EmailBean[] gotThemEmails = re.getEmails();

        //assert
        assertEquals(mymail, gotThemEmails[0]);
    }

    /**
     * Test for exception
     *
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmailSendFailureInvalidEmail() throws SQLException, IllegalArgumentException {
        LOG.info("Testing testEmailSendFailureInvalidEmail");

        String from = "email";
        String[] to = new String[]{"to"};
        String subject = "subject";
        String textMessage = "This is a plain text message";

        //send
        EmailBean mymail = new EmailBean();
        mymail.setFrom(from);
        mymail.setTo(to);
        mymail.setSubject(subject);
        mymail.setTextMessage(textMessage);
        SendEmail send = new SendEmail();
        send.send(mymail);

        // If an exception was not thrown then the test failed
        fail("The EmailBean contain invalid email addresses and it did not throw the expected exception.");

    }

}
