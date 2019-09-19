package com.radiodevices.wifianalyzer.enitity;

import java.util.UUID;

/*
* Радиоустройство
* */
public class Device extends Entity {

    private String model;

    private String version;

    private String mac;

    private String ipAddress;

    public Device(String model, String version, String mac, String ipAddress) {
        super();
        this.model = model;
        this.version = version;
        this.mac = mac;
        this.ipAddress = ipAddress;
    }

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
    * Версия программного обеспечения
    * */
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /*
    * MAC-адрес устройства
    * */
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    /*
    * IP-адрес устройства
    * */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
