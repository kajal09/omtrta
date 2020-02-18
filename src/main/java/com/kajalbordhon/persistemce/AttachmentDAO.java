package com.kajalbordhon.persistemce;

import com.kajalbordhon.data.AttachmentBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This DAO object takes care of all the interaction with the attachment table.
 *
 * @author kajal
 */
public class AttachmentDAO {

    private final static Logger LOG = LoggerFactory.getLogger(AttachmentDAO.class);
    private final String url;
    private final String user;
    private final String password;

    public AttachmentDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Insert an attachment to the database.
     *
     * @param ab
     * @return id
     * @throws SQLException
     */
    public int insert(AttachmentBean ab) throws SQLException {
        LOG.info("Inserting attachment");
        return insertPrivate(ab);
    }

    /**
     * Get an attachment using id.
     *
     * @param id
     * @return AttachmentBean
     * @throws SQLException
     * @throws NullPointerException
     */
    public AttachmentBean getAttachment(int id) throws SQLException, NullPointerException {
        LOG.info("Getting attachment");
        return getAttachmentPrivate(id);
    }

    private int insertPrivate(AttachmentBean ab) throws SQLException {
        int id = -1;
        String sql = "INSERT INTO attachment (file,name,embedded) VALUES (?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setBytes(1, ab.getAttachment());
            pStatement.setString(2, ab.getName());
            pStatement.setBoolean(3, ab.isEmbedded());
            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                ab.setId(id);
            }
        } catch (SQLException ex) {
            LOG.debug("insert error", ex.getMessage());
            throw ex;
        }

        return id;
    }

    private AttachmentBean getAttachmentPrivate(int id) throws SQLException, NullPointerException {
        AttachmentBean ab = null;
        int attId;
        byte[] attByte;
        String attName;
        Boolean attEm;

        String selectQuery = "SELECT aid,file,name,embedded FROM attachment WHERE aid = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(selectQuery)) {
            pStatement.setInt(1, id);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    attId = rs.getInt(1);
                    attByte = rs.getBytes(2);
                    attName = rs.getString(3);
                    attEm = rs.getBoolean(4);
                    ab = new AttachmentBean(attByte, attName, attEm);
                    ab.setId(attId);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("findAllGames error", ex.getMessage());
            throw ex;
        }

        return ab;
    }

}
