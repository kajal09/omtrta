package com.kajalbordhon.persistemce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This DAO object takes care of all the interaction with the Address table.
 *
 * @author kajal
 */
public class AddressDAO {

    private final static Logger LOG = LoggerFactory.getLogger(AddressDAO.class);
    private final String url;
    private final String user;
    private final String password;

    public AddressDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Get address using id.
     *
     * @param id
     * @return String address
     * @throws SQLException
     * @throws NullPointerException
     */
    public String getAddress(int id) throws SQLException, NullPointerException {
        LOG.info("Getting Address");
        return getAddressPrivate(id);
    }

    /**
     * Insert address to the database.
     *
     * @param email
     * @return id
     * @throws SQLException
     */
    public int insert(String email) throws SQLException {
        LOG.info("Trying to insert address");
        int id = AddressExist(email);

        if (id == -1) {
            return insertAddress(email);
        }

        return id;
    }

    private int insertAddress(String email) throws SQLException {
        int id;
        String sql = "INSERT INTO emails (email) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, email);
            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                id = -1;
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("insertAddress", ex.getMessage());
            throw ex;
        }

        return id;
    }

    private int AddressExist(String email) throws SQLException {
        int id = -1;
        String sql = "SELECT eid FROM emails where email=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, email);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("AddressExist", ex.getMessage());
            throw ex;
        }
        return id;
    }

    private String getAddressPrivate(int id) throws SQLException, NullPointerException {
        String address = null;
        String sql = "SELECT email FROM emails where eid=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, id);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    address = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getAddressPrivate", ex.getMessage());
            throw ex;
        }
        return address;
    }

}
