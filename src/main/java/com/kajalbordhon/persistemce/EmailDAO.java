package com.kajalbordhon.persistemce;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * One DAO to role them all. This DAO class takes care of pushing and pulling
 * EmailBeans from the database.
 *
 * @author kajal
 */
public class EmailDAO {

    private final static Logger LOG = LoggerFactory.getLogger(EmailDAO.class);
    private final String url = "jdbc:mysql://localhost:3306/OMTRTA?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "kajal";
    private final String password = "dawson";
    private final FolderDAO folDao = new FolderDAO(url, user, password);
    private final AddressDAO addDao = new AddressDAO(url, user, password);
    private final AttachmentDAO attDao = new AttachmentDAO(url, user, password);

    public EmailDAO() {
    }

    /**
     * Get all the emails from the database and return an array of EmailBeans.
     *
     * @return array of EmailBeans
     * @throws SQLException
     */
    public EmailBean[] pullEmail() throws SQLException {
        LOG.info("Getting all the emails from the database");
        ArrayList<EmailBean> mailList = new ArrayList<>();

        String sql = "SELECT ebid,`from`,subject,textMessage,htmlMessage,fid,date FROM ebean";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    int ebid = rs.getInt(1);
                    EmailBean temp = new EmailBean(rs.getString(2), this.getTob(ebid), this.getCcb(ebid), this.getBccb(ebid), rs.getString(3), rs.getString(4), rs.getString(5), this.getAllAttachments(ebid), this.getFolder(rs.getInt(6)), rs.getTimestamp(7));
                    temp.setId(ebid);
                    mailList.add(temp);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("pullEmail()", ex.getMessage());
            throw ex;
        }

        EmailBean[] temp = new EmailBean[mailList.size()];
        temp = mailList.toArray(temp);
        if (!(temp == null || temp.length < 0)) {
            return temp;
        }
        return null;
    }

    /**
     * Get all the emails from the folder and return an array of EmailBeans.
     *
     * @param folder
     * @return array of EmailBeans
     * @throws SQLException
     */
    public EmailBean[] pullEmailByFolder(String folder) throws SQLException {
        LOG.info("Getting all the emails from the folder");
        ArrayList<EmailBean> mailList = new ArrayList<>();
        int fid = addFolder(folder);

        String sql = "SELECT ebid,`from`,subject,textMessage,htmlMessage,fid,date FROM ebean where fid=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, fid);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    int ebid = rs.getInt(1);
                    EmailBean temp = new EmailBean(rs.getString(2), this.getTob(ebid), this.getCcb(ebid), this.getBccb(ebid), rs.getString(3), rs.getString(4), rs.getString(5), this.getAllAttachments(ebid), this.getFolder(rs.getInt(6)), rs.getTimestamp(7));
                    temp.setId(ebid);
                    mailList.add(temp);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("pullEmailByFolder", ex.getMessage());
            throw ex;
        }

        EmailBean[] temp = new EmailBean[mailList.size()];
        temp = mailList.toArray(temp);
        if (!(temp == null || temp.length < 0)) {
            return temp;
        }
        return null;
    }

    /**
     * Add a EmailBean to the database.
     *
     * @param eb
     * @return id
     * @throws SQLException
     */
    public int pushEmail(EmailBean eb) throws SQLException {
        LOG.info("Inserting email to the database");
        int id;
        String sql = "INSERT INTO ebean (`from`, subject, textMessage, htmlMessage, fid, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, eb.getFrom());
            pStatement.setString(2, eb.getSubject());
            pStatement.setString(3, eb.getTextMessage());
            pStatement.setString(4, eb.getHtmlMessage());
            pStatement.setInt(5, this.addFolder(eb.getFolder()));
            pStatement.setTimestamp(6, eb.getDate());

            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                id = -1;
                if (rs.next()) {
                    id = rs.getInt(1);
                    eb.setId(id);
                }
            }

            //Add all the to,cc,bcc and attachment
            setTob(eb.getTo(), id);
            setCcb(eb.getCc(), id);
            setBccb(eb.getBcc(), id);
            bridgeAttachment(eb.getAttachBean(), id);

        } catch (SQLException ex) {
            LOG.debug("pushEmail", ex.getMessage());
            throw ex;
        }

        return id;
    }

    public void changeEmailFolder(int ebid, String folder) throws SQLException {
        LOG.info("Changing email folder");
        int fid = addFolder(folder);

        String sql = "UPDATE ebean SET fid = ? WHERE ebid=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, fid);
            pStatement.setInt(2, ebid);
            pStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.debug("changeEmailFolder", ex.getMessage());
            throw ex;
        }
    }

    /**
     * Delete a folder and all emails in that folder.
     *
     * @param id
     * @throws SQLException
     */
    public void deleteFolder(String name) throws SQLException {
        LOG.info("Deleting folder ***warning*** all emails in that folder will be deleted");
        folDao.deleteFolder(name);
    }

    public void insertFolder(String folname) throws SQLException {
        folDao.insert(folname);
    }

    public void renameFolder(String oldName, String newName) throws SQLException {
        folDao.renameFolder(oldName, newName);
    }

    /**
     * Delete an email using email id from the database.
     *
     * @param ebid
     * @throws SQLException
     */
    public void deleteEmail(int ebid) throws SQLException {
        LOG.info("Deleting email");
        String sql = "DELETE FROM ebean WHERE ebid=?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, ebid);
            pStatement.execute();
            LOG.debug("Email DELETED");
        } catch (SQLException ex) {
            LOG.debug("deleteEmail", ex.getMessage());
            throw ex;
        }

    }

    /**
     * Uses the FolderDAO.java to get all folders.
     *
     * @return An array of folders.
     * @throws SQLException
     * @throws NullPointerException
     */
    public String[] getAllFolders() throws SQLException, NullPointerException {
        return folDao.getAllFolders();
    }

    private String[] getTob(int ebid) throws SQLException {
        String sql = "SELECT eid FROM tob where ebid=?";

        return databaseToEmailArray(ebid, sql);
    }

    private String[] getCcb(int ebid) throws SQLException {
        String sql = "SELECT eid FROM ccb where ebid=?";

        return databaseToEmailArray(ebid, sql);
    }

    private String[] getBccb(int ebid) throws SQLException {
        String sql = "SELECT eid FROM bccb where ebid=?";

        return databaseToEmailArray(ebid, sql);
    }

    /**
     * A generic method to retrieve all the Email Address arrays (to,cc,bcc).
     *
     * @param ebid
     * @param sql
     * @return
     * @throws SQLException
     */
    private String[] databaseToEmailArray(int ebid, String sql) throws SQLException {
        ArrayList<String> emailArray = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, ebid);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    emailArray.add(this.getAddress(rs.getInt(1)));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("databaseToEmailArray", ex.getMessage());
            throw ex;
        }

        String[] temp = new String[emailArray.size()];
        temp = emailArray.toArray(temp);
        if (!(temp == null || temp.length < 0)) {
            return temp;
        }
        return null;
    }

    private AttachmentBean[] getAllAttachments(int ebid) throws SQLException {
        ArrayList<AttachmentBean> attbeans = new ArrayList<>();
        String sql = "SELECT aid FROM bridgeAttachment where ebid=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, ebid);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    attbeans.add(this.getAttachment(rs.getInt(1)));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("databaseToEmailArray", ex.getMessage());
            throw ex;
        }

        AttachmentBean[] temp = new AttachmentBean[attbeans.size()];
        temp = attbeans.toArray(temp);
        if (!(temp == null || temp.length < 0)) {
            return temp;
        }
        return null;
    }

    private void setTob(String[] to, int ebid) throws SQLException {
        String sql = "INSERT INTO tob (eid, ebid) VALUES (?, ?)";

        if (!(to == null || to.length < 0)) {
            emailArrayToDatabase(to, ebid, sql);
        }

    }

    private void setCcb(String[] cc, int ebid) throws SQLException {
        String sql = "INSERT INTO ccb (eid, ebid) VALUES (?, ?)";

        if (!(cc == null || cc.length < 0)) {
            emailArrayToDatabase(cc, ebid, sql);
        }

    }

    private void setBccb(String[] bcc, int ebid) throws SQLException {
        String sql = "INSERT INTO bccb (eid, ebid) VALUES (?, ?)";

        if (!(bcc == null || bcc.length < 0)) {
            emailArrayToDatabase(bcc, ebid, sql);
        }

    }

    /**
     * A generic method to push all the Email Address arrays (to,cc,bcc) to the
     * database.
     *
     * @param arr
     * @param ebid
     * @param sql
     * @throws SQLException
     */
    private void emailArrayToDatabase(String[] arr, int ebid, String sql) throws SQLException {

        for (String address : arr) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setInt(1, this.addAddress(address));
                pStatement.setInt(2, ebid);
                pStatement.execute();

            } catch (SQLException ex) {
                LOG.debug("emailArrayToDatabase", ex.getMessage());
                throw ex;
            }
        }

    }

    private void bridgeAttachment(AttachmentBean[] atb, int ebid) throws SQLException {
        String sql = "INSERT INTO bridgeAttachment (aid, ebid) VALUES (?, ?)";

        if (!(atb == null || atb.length < 0)) {
            for (AttachmentBean att : atb) {

                try (Connection connection = DriverManager.getConnection(url, user, password);
                        PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                    pStatement.setInt(1, addAttachment(att));
                    pStatement.setInt(2, ebid);
                    pStatement.execute();

                } catch (SQLException ex) {
                    LOG.debug("bridgeAttachment", ex.getMessage());
                    throw ex;
                }

            }
        }

    }

    private int addFolder(String folname) throws SQLException {
        return folDao.insert(folname);
    }

    private String getFolder(int id) throws SQLException {
        return folDao.getFolder(id);
    }

    private int addAddress(String email) throws SQLException {
        return addDao.insert(email);
    }

    private String getAddress(int id) throws SQLException {
        return addDao.getAddress(id);
    }

    private int addAttachment(AttachmentBean ab) throws SQLException {
        return attDao.insert(ab);
    }

    private AttachmentBean getAttachment(int id) throws SQLException {
        return attDao.getAttachment(id);
    }

}
