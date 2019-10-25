package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.dao.AuthorizeDao;
import com.radiodevices.wifianalyzer.dao.UserDao;
import com.radiodevices.wifianalyzer.enitity.Session;
import com.radiodevices.wifianalyzer.enitity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private AuthorizeDao authorizeDao;
    private UserDao userDao;
    private Logger logger = Logger.getLogger(AuthorizationServiceImpl.class.getName());

    private static Map<User, Session> autohorizedUsers = new ConcurrentHashMap<>();

    @Autowired
    public AuthorizationServiceImpl(AuthorizeDao authorizeDao, UserDao userDao) {
        this.authorizeDao = authorizeDao;
        this.userDao = userDao;
    }

    @Override
    public String login(String email, String hash) {
        if (email == null || hash == null) {
            logger.log(Level.INFO, ".login# ОШИБКА! email: " + email + "; hash: " + hash);
            return null;
        }

        // если сессии нет, создаем
        logger.log(Level.INFO, ".login# email: " + email + "; hash: " + hash);
        User user = userDao.findAll().stream()
                .filter(user1 -> user1.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (user == null) {
            logger.log(Level.INFO, ".login# пользователь "+ email + " не найден!");
            return null;
        }

        String userPassHash = user.getHash();

        if (userPassHash == null ) {
            logger.log(Level.INFO, "User not found or password is null");
            return null;
        }

        if (!userPassHash.equals(hash)) {
            logger.log(Level.INFO, "Wrong password");
            return null;
        }

        Session session = getSession(user);

        if (Objects.isNull(session)){
            // сессии нет, создаём
            session = createSession(email);
            autohorizedUsers.put(user, session);
            return session.getSessionId();
        }

        if (!isAlive(session)) {
            dropSession(email);
            session = createSession(email);
            autohorizedUsers.put(user, session);
        }
        return session.getSessionId();
    }

    @Override
    public void logOut(String sessionId) {
        // Удаляем запись по sessionId
        logger.log(Level.INFO, ".logout# sessionId: " + sessionId);

        if (Objects.isNull(sessionId)){
            dropSession(sessionId);
        }
    }

    @Override
    public boolean isAlive(String sessionId) {
        // если сессия протухла, то вернуть false
        logger.log(Level.INFO, ".isAlive# sessionId: " + sessionId);
        Session session = getSession(sessionId);
        return session != null && isAlive(session);
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

    @Override
    public User getUserBySessionId(String sessionId) {
        Collection<User> userKeys = autohorizedUsers.keySet();
        for (User key : userKeys) {
            Session session = autohorizedUsers.get(key);
            if (session.getSessionId().equals(sessionId)) {
                return key;
            }
        }
        return null;
    }


    private Session getSession(String sessionId) {
        Collection<User> userKeys = autohorizedUsers.keySet();

        for (User user : userKeys) {
            Session session = autohorizedUsers.get(user);
            if (session.getSessionId().equals(sessionId)) {
                return session;
            }
        }
        return null;
    }

    private Session getSession(User user) {
        Collection<User> userKeys = autohorizedUsers.keySet();
        for (User key : userKeys) {
            Session session = autohorizedUsers.get(user);
            if (key.getId().equals(user.getId())) {
                return autohorizedUsers.get(key);
            }
        }
        return null;
    }

    private Session createSession(String login) {
        // создаем запись sessionId
        String sessionId = getSha256HashHex(login + LocalDateTime.now().toString());
        Session session = new Session();
        session.setLogin(login);
        session.setDate(new Date());
        session.setSessionId(sessionId);
        authorizeDao.save(session);

        return session;
    }

    private void dropSession(String login) {
        // Удаление записи из таблицы
        if (!Objects.isNull(login)) {
            Collection<User> userKeys = autohorizedUsers.keySet();

            for (User user : userKeys) {
                Session session = autohorizedUsers.get(user);
                if (user.getEmail().equals(login)) {
                    autohorizedUsers.remove(user);
                }
            }

            logger.log(Level.INFO, ".dropSession# session for : " + login + " deleted!");
        }
    }

    /** Convert text to hash-number using SHA-256
     * @param text text to convert
     * @return sha-256 hash as BigInteger
     */
    private static BigInteger getSha256Hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA-256" );
            md.update( text.getBytes( StandardCharsets.UTF_8 ) );
            byte[] digest = md.digest();

            return new BigInteger( 1, digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Convert text to hex-representation (string) of hash using SHA-256
     * @param text text to convert
     * @return sha-256 hash as hex string
     */
    private String getSha256HashHex(String text) {
        String response = String.format("%064x", getSha256Hash(text));
        logger.log(Level.INFO, "Sha256: " + text + " -> " + response);
        return response;
    }
}
