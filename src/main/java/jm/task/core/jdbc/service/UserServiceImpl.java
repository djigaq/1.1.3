package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDao UD = new UserDaoJDBCImpl();

    public void createUsersTable() {
        UD.createUsersTable();
    }

    public void dropUsersTable() {
        UD.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        UD.saveUser(name, lastName, age);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        UD.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return UD.getAllUsers();
    }

    public void cleanUsersTable() {
        UD.cleanUsersTable();
    }
}
