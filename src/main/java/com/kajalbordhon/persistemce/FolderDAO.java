package com.kajalbordhon.persistemce;

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
 * This DAO object takes care of all the interaction with the Folder table. I am
 * using ON DELETE CASCADE on all my database table so that if a folder get
 * deleted, it will delete all the email in that folder.
 *
 * @author kajal
 */
public class FolderDAO {

    private final static Logger LOG = LoggerFactory.getLogger(FolderDAO.class);
    private final String url;
    private final String user;
    private final String password;

    public FolderDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Delete folder along with all the emails in it.
     *
     * @param id
     * @throws SQLException
     */
    public void deleteFolder(String name) throws SQLException {
        LOG.info("Trying to deleting Folder");
        int id = folderExist(name);
        if (id != -1) {
            deleteFolderPrivate(id);
        }
    }

    public void renameFolder(String oldName, String newName) throws SQLException {
        int id = folderExist(oldName);
        if (id != -1) {

            String sql = "UPDATE folder SET name = ? WHERE fid=?";
            try (Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setString(1, newName);
                pStatement.setInt(2, id);
                pStatement.executeUpdate();
            } catch (SQLException ex) {
                LOG.debug("renameFolder", ex.getMessage());
                throw ex;
            }

        }

    }

    /**
     * Get folder name
     *
     * @param id
     * @return
     * @throws SQLException
     * @throws NullPointerException
     */
    public String getFolder(int id) throws SQLException, NullPointerException {
        LOG.info("Trying to get folder name");
        return getFolderPrivate(id);
    }

    /**
     * Get folder name
     *
     * @return
     * @throws SQLException
     * @throws NullPointerException
     */
    public String[] getAllFolders() throws SQLException, NullPointerException {
        LOG.info("Trying to get all folder name");
        ArrayList<String> folder = new ArrayList<>();
        String sql = "SELECT name FROM folder";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    folder.add(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getAllFolders", ex.getMessage());
            throw ex;
        }

        String[] temp = new String[folder.size()];
        temp = folder.toArray(temp);
        if (!(temp == null || temp.length < 0)) {
            return temp;
        }
        return null;
    }

    /**
     * Insert a new folder
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public int insert(String name) throws SQLException {
        LOG.info("Trying to Insert folder or get folder id");
        int id = folderExist(name);
        if (id == -1) {
            return insertFolder(name);
        }
        return id;
    }

    private int insertFolder(String name) throws SQLException {
        int id;
        String sql = "INSERT INTO folder (name) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, name);
            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                id = -1;
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("insertFolder", ex.getMessage());
            throw ex;
        }
        return id;
    }

    private int folderExist(String name) throws SQLException {
        int id = -1;
        String sql = "SELECT fid FROM folder where name=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, name);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("folderExist", ex.getMessage());
            throw ex;
        }
        return id;
    }

    private String getFolderPrivate(int id) throws SQLException, NullPointerException {
        String folder = null;
        String sql = "SELECT name FROM folder where fid=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, id);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    folder = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getFolderPrivate", ex.getMessage());
            throw ex;
        }
        return folder;
    }

    /**
     * Mysql database will delete all the emails in that folder.
     *
     * @param id
     * @throws SQLException
     */
    private void deleteFolderPrivate(int id) throws SQLException {

        String sql = "DELETE FROM folder WHERE fid=?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, id);
            pStatement.execute();
            LOG.debug("FOLDER DELETED");
        } catch (SQLException ex) {
            LOG.debug("deleteFolderPrivate", ex.getMessage());
            throw ex;
        }

    }

}
