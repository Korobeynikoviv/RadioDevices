package com.radiodevices.wifianalyzer.enitity;

import java.util.Date;
import java.util.List;

/*
* Отчет
* */
public class Report {
    private User user;
    private Date createDate;
    private ReportSourceDevice sourceDevice;
    private List<RadioPoint> radioPoints;

    /*
    * Пользователь
    * */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
    * Дата создания
    * */
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /*
    * Источник отчета
    * */
    public ReportSourceDevice getSourceDevice() {
        return sourceDevice;
    }

    public void setSourceDevice(ReportSourceDevice sourceDevice) {
        this.sourceDevice = sourceDevice;
    }

    /*
    * Радиоточки
    * */
    public List<RadioPoint> getRadioPoints() {
        return radioPoints;
    }

    public void setRadioPoints(List<RadioPoint> radioPoints) {
        this.radioPoints = radioPoints;
    }
}
