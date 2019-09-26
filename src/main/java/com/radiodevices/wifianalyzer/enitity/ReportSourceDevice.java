package com.radiodevices.wifianalyzer.enitity;

/*
* Устройство-источник отчета
* */
public class ReportSourceDevice {
    private String model;
    private String imei;

    /*
    * Модель устройства
    * */
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /*
    * Идентификатор устройства
    * */
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
