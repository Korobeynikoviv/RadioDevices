package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.enitity.User;

public interface UserService {
    /*
    * Добавить пользователя
    * */
    public User addUser(String email, String name, String hash, String role);

    /*
    * Вернуть пользователя по eMail
    * */
    public User getUserByEmail(String email);

    /*
    * Обновить данные пользователя
    * */
    public User updateUser(User user);

    /*
    *
    * */
    public void deleteUser(User user);

    /*
    *
    * */
    public void deleteUserByEMail(String email);
}
