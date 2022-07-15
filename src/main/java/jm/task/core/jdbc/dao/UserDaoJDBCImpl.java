package jm.task.core.jdbc.dao;

import java.sql.Statement;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDaoJDBCImpl implements UserDao {
    private static final Connection CONNECTION = Util.getConnection();

    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users " +
                    "(ID BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(255), LastName VARCHAR(255), Age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = CONNECTION.prepareStatement("INSERT INTO Users (Name, LastName, Age) VALUES (?, ?, ?)")) {
            CONNECTION.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
           } catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = CONNECTION.prepareStatement("DELETE FROM Users WHERE ID = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet rs = CONNECTION.createStatement().executeQuery("SELECT * FROM Users")) {
            while(rs.next()) {
                User user = new User(rs.getString( "Name"),
                        rs.getString( "LastName"), rs.getByte("Age"));
                user.setId(rs.getLong("ID"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}