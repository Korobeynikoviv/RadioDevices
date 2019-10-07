package com.radiodevices.wifianalyzer.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(generator = "session_generator")
    @SequenceGenerator(
            name = "session_generator",
            sequenceName = "session_sequence",
            initialValue = 1
    )
    private Long id;

    @Column(columnDefinition = "login")
    private String login;

    @Column(columnDefinition = "session_id")
    private String sessionId;

    @Column(columnDefinition = "date")
    private Date date;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return this.getId() + "; "
                + this.getSessionId() + "; "
                + this.getDate();
    }
}
