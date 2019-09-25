package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.dao.UserDao;
import com.radiodevices.wifianalyzer.enitity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(String email, String name, String hash) {
        User user = getUserByEmail(email);

        if (user == null) {
            logger.log(Level.INFO, "Пользователь " + name + " не найден!");
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setHash(hash);
            userDao.save(user);
            logger.log(Level.INFO, "Создали пользователя " + user.toString());
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findAll().stream().filter(user1 -> user1.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public User updateUser(User user) {
        User existUser = userDao.findById(user.getId()).get();
        existUser.setHash(user.getHash());
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        logger.log(Level.INFO, "Обновили пользователя " + user.toString());
        return userDao.save(existUser);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
        logger.log(Level.INFO, "Удалили пользователя " + user.toString());
    }

    @Override
    public void deleteUserByEMail(String email) {
        deleteUser(getUserByEmail(email));
    }
}
