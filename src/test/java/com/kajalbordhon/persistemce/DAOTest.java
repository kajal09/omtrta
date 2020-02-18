/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kajalbordhon.persistemce;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for all the server interactions.
 *
 * @author kajal
 */
public class DAOTest {

    private final static Logger LOG = LoggerFactory.getLogger(DAOTest.class);
    private final String url = "jdbc:mysql://localhost:3306/OMTRTA?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "kajal";
    private final String password = "dawson";

    public DAOTest() {
    }

    /**
     * This method will pull all the emails from the database. 25 is the number
     * of test mails in the database.
     *
     * @throws SQLException
     */
    @Test(timeout = 2000)
    public void testPullEmailDAO() throws SQLException {
        LOG.info("Testing testPullEmailDAO");

        EmailDAO instance = new EmailDAO();
        EmailBean[] result = instance.pullEmail();
        assertEquals(result.length, 25);
    }

    /**
     * This method will pull all the emails from the Inbox folder. 25 is the
     * number of test mails in the database.
     *
     * @throws SQLException
     */
    @Test(timeout = 2000)
    public void testpullEmailByFolderDAO() throws SQLException {
        LOG.info("Testing testpullEmailByFolderDAO");

        EmailDAO instance = new EmailDAO();
        EmailBean[] result = instance.pullEmailByFolder("Inbox");
        assertEquals(result.length, 10);
    }

    /**
     * This method will push a new email then pull all emails to see if it's
     * 25+1=26.
     *
     * @throws SQLException
     */
    @Test(timeout = 2000)
    public void testPushEmailDAO() throws SQLException {
        LOG.info("Testing testPushEmailDAO");
        EmailBean eb = new EmailBean("TonyC@armyspy.com",
                new String[]{"Rodneson@jourrapide.com"},
                null,
                null,
                "himenaeos id litora enim aenean",
                "Arcu vulputate class urna risus etiam est dictum sit mi ultrices, vulputate ultrices enim primis elementum phasellus praesent suscipit at, nisl quisque etiam nulla ornare aliquam lectus duis curabitur vehicula ac curae lobortis mattis iaculis elementum vitae per neque, vitae lorem elementum ultricies congue nostra pellentesque ornare, vivamus tristique curabitur lobortis libero pulvinar pretium id etiam ad in tortor vehicula interdum consequat nec egestas, dapibus urna vivamus urna fames rutrum venenatis, primis faucibus aenean platea ut felis velit interdum ipsum nam gravida non iaculis condimentum aenean lacinia maecenas.",
                "<p>Lorem ipsum dapibus lorem at praesent libero nisi porttitor hendrerit, ut neque gravida leo curabitur maecenas magna ornare, quis vitae amet sit ligula eu vestibulum nisl rhoncus suspendisse orci integer nam tincidunt donec vehicula.</p>",
                null,
                "Inbox",
                new Timestamp(System.currentTimeMillis()));

        EmailDAO instance = new EmailDAO();
        instance.pushEmail(eb);
        int size = instance.pullEmail().length;
        assertEquals(size, 26);
    }

    /**
     * Inserts a new folder and retrieve the folder.
     *
     * @throws SQLException
     */
    @Test(timeout = 2000)
    public void testInsertRetrieveFolderDAO() throws SQLException {
        LOG.info("Testing testInsertRetrieveFolderDAO");
        FolderDAO instance = new FolderDAO(url, user, password);
        String FolderName = "the cake is a lie";
        int id = instance.insert(FolderName);
        assertEquals(FolderName, instance.getFolder(id));
    }

    /**
     * This method will pull all the folders from the database. 25 is the number
     * folders in the database.
     *
     * @throws SQLException
     */
    @Test(timeout = 2000)
    public void testgetAllFoldersDAO() throws SQLException {
        LOG.info("Testing testgetAllFoldersDAO");

        EmailDAO instance = new EmailDAO();
        String[] result = instance.getAllFolders();
        assertEquals(result.length, 6);
    }

    /**
     * Inserts a new folder then delete the folder.
     *
     * @throws SQLException
     */
    @Test(timeout = 3000)
    public void testDeleteFolderDAO() throws SQLException {
        LOG.info("Testing testDeleteFolderDAO");
        FolderDAO instance = new FolderDAO(url, user, password);
        String FolderName = "cthulhu";
        int id = instance.insert(FolderName);
        instance.deleteFolder("cthulhu");
        assertEquals(null, instance.getFolder(id));
    }

    /**
     * Deletes the first email in the database. Then pull all emails to see if
     * it was deleted. Answer should be 25-1=24.
     *
     * @throws SQLException
     */
    @Test(timeout = 5000)
    public void testDeleteEmailDAO() throws SQLException {
        LOG.info("Testing testDeleteEmailDAO");
        EmailDAO instance = new EmailDAO();
        instance.deleteEmail(instance.pullEmail()[0].getId());
        int length = instance.pullEmail().length;
        assertEquals(length, 24);
    }

    /**
     * Push an attachment to the server and gets it back.
     *
     * @throws SQLException
     * @throws java.io.IOException
     */
    @Test(timeout = 5000)
    public void testAttachmentDAO() throws SQLException, IOException {
        LOG.info("Testing testAttachmentDAO");
        AttachmentDAO instance = new AttachmentDAO(url, user, password);

        AttachmentBean picAttach = null;
        try {

            //attachment
            File file = new File("coffee.jpg");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            picAttach = new AttachmentBean(fileContent, file.getName(), false);

        } catch (IOException ex) {
            LOG.debug("testAttachmentDAO", ex.getMessage());
            throw ex;
        }

        int id = instance.insert(picAttach);
        assertEquals(picAttach, instance.getAttachment(id));
    }

    /**
     * Creates a new EmailBean, push it to the server and gets it back. This
     * test will compare all the elements of the EmailBean.
     *
     * @throws SQLException
     * @throws java.io.IOException
     */
    @Test(timeout = 2000)
    public void testEmailDAO() throws SQLException, IOException {
        LOG.info("Testing testEmailDAO");

        String from = "send.1541986@gmail.com";
        String[] to = new String[]{"receive.1541986@gmail.com"};
        String[] cc = new String[]{"other.1541986@gmail.com"};
        String[] bcc = null;
        String subject = "TEST EMAIL   ";
        String textMessage = "The Director of a software company proudly announced that a flight software developed by the company was installed in an airplane and the airlines was offering free first flights to the members of the company. “Who are interested?” the Director asked. Nobody came forward. Finally, one person volunteered. The brave Software Tester stated, 'I will do it. I know that the airplane will not be able to take off.'";
        String htmlMessage = "<!DOCTYPE html><html><head><title>Page Title</title></head><body><h1>To tell somebody that he is wrong is called criticism. To do so officially is called testing.</h1><img src='cid:why.jpg'></body></html>";
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
            LOG.debug("testEmailDAO", ex.getMessage());
            throw ex;
        }
        ab = new AttachmentBean[]{picAttach, picEmb, picAttach3, picAttach2};

        EmailBean test = new EmailBean(from, to, cc, bcc, subject, textMessage, htmlMessage, ab, "MyFolder", new Timestamp(System.currentTimeMillis()));

        EmailDAO instance = new EmailDAO();
        instance.pushEmail(test);
        EmailBean[] result = instance.pullEmail();

        assertEquals(test, result[result.length - 1]);
    }

    /**
     * This will insert an empty record to the database. It should throw an
     * exception otherwise it fails.
     *
     * @throws SQLException
     */
    @Test(timeout = 2000, expected = SQLException.class)
    public void testPushEmailFailureDAO() throws SQLException {
        LOG.info("Testing testPushEmailFailureDAO");
        EmailDAO instance = new EmailDAO();
        instance.pushEmail(new EmailBean());

        // If an exception was not thrown then the test failed
        fail("The EmailBean is empty and it did not throw the expected exception.");

    }

    /**
     * I copied it from your demo, i hope i was allowed to. If not then my
     * sincere apology.
     *
     * This routine recreates the database before every test. This makes sure
     * that a destructive test will not interfere with any other test. Does not
     * support stored procedures.
     *
     * This routine is courtesy of Bartosz Majsak, the lead Arquillian developer
     * at JBoss
     *
     * @throws java.io.IOException
     */
    @Before
    public void seedDatabase() throws IOException {
        LOG.info("Seeding Database");
        final String seedDataScript = loadAsString("CreateTables.sql");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
            EmailBeanFactory factory = new EmailBeanFactory();
            factory.summon();
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    /**
     * The following methods support the seedDatabase method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream);) {
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }

}
