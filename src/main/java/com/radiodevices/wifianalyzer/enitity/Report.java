package com.radiodevices.wifianalyzer.enitity;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

/*
* Отчет
* */
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(generator = "report_generator")
    @SequenceGenerator(
            name = "report_generator",
            sequenceName = "report_sequence",
            initialValue = 1000
    )
    private Long id;
    @Column(columnDefinition = "email")
    private String email;
    @Column(columnDefinition = "date")
    private LocalDateTime date;
    @Column(columnDefinition = "report_json")
    private String reportJson;

    /*
    * Пользователь
    * */
    public String getUser() {
        return email;
    }

    public void setUser(String user) {
        this.email = user;
    }


    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.date = localDateTime;
    }

    public String getReportJson() {
        return reportJson;
    }

    public void setReportJson(String reportJson) {
        this.reportJson = reportJson;
    }

    public Long getId() {
        return id;
    }

}
