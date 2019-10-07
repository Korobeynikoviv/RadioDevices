package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.enitity.Session;

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
}
