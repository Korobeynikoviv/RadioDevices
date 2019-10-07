package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.dao.AuthorizeDao;
import com.radiodevices.wifianalyzer.enitity.Session;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private AuthorizeDao authorizeDao;
    private Logger logger = Logger.getLogger(AuthorizationServiceImpl.class.getName());

    @Autowired
    public AuthorizationServiceImpl(AuthorizeDao authorizeDao) {
        this.authorizeDao = authorizeDao;
    }

    @Override
    public String login(String email, String hash) {
        // если сессии нет, создаем
        logger.log(Level.INFO, ".login# email: " + email + "; hash: " + hash);

        String loginId = getMd5(email + hash);
        Session session = getSessionByLogin(loginId);

        if (Objects.isNull(session)){
            // сессии нет, создаём
            String sessionId = getMd5(email + LocalDate.now().toString() + hash);
            return createSession(loginId, sessionId);
        }

        if (isAlive(session)) {
            // сессия актуальная
            return session.getSessionId();
        } else {
            // сессия протухла, создаём новую
            dropSession(session);
            Date current = new Date();
            String sessionId = getMd5(email + current.getTime() + hash);
            return createSession(loginId, sessionId);
        }
    }

    @Override
    public void logOut(String sessionId) {
        // Удаляем запись по sessionId
        logger.log(Level.INFO, ".logout# sessionId: " + sessionId);
        Session session = getSession(sessionId);

        if (Objects.isNull(session)){
            dropSession(session);
        }
    }

    @Override
    public boolean isAlive(String sessionId) {
        // если сессия протухла, то вернуть false
        logger.log(Level.INFO, ".isAlive# sessionId: " + sessionId);
        Session session = getSession(sessionId);
        return isAlive(session);
    }

    @Override
    public boolean isAlive(Session session) {
        // если сессия протухла, то вернуть false
        logger.log(Level.INFO, ".isAlive# sessionId: " + session.getSessionId());
        Date currentDate = new Date();
        long seconds = (currentDate.getTime()-session.getDate().getTime())/1000;
        int ONE_MINUTE = 1;
        return (seconds / 60) < ONE_MINUTE;
    }

    private Session getSession(String sessionId) {
        return authorizeDao.findAll().stream().filter(it -> it.getSessionId().equals(sessionId)).findFirst().orElse(null);
    }

    private Session getSessionByLogin(String loginId) {
        return authorizeDao.findAll().stream().filter(it -> it.getLogin().equals(loginId)).findFirst().orElse(null);
    }

    private String createSession(String login, String sessionId) {
        // создаем запись sessionId
        Session session = new Session();
        session.setLogin(login);
        session.setDate(new Date());
        session.setSessionId(sessionId);
        authorizeDao.save(session);

        return sessionId;
    }

    private void dropSession(Session session) {
        // Удаление записи из таблицы
        if (!Objects.isNull(session)) {
            authorizeDao.delete(session);
            logger.log(Level.INFO, ".dropSession# sessionId: " + session.getSessionId() + " deleted!");
        }
    }

    private String getMd5(String st) {
        logger.log(Level.INFO, ".getMd5# st: " + st);
        return DigestUtils.md5Hex(st);
    }
}
