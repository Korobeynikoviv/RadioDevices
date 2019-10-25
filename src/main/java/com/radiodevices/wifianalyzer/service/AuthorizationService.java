package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.enitity.Session;
import com.radiodevices.wifianalyzer.enitity.User;

/*
*
* */
public interface AuthorizationService {
    /*
    *
    * */
    public String login(String email, String hash);

    /*
    *
    * */
    public void logOut(String sessionId);

    /*
    *
    * */
    public boolean isAlive(String sessionId);

    boolean isAlive(Session session);

    public User getUserBySessionId(String sessionId);
}
