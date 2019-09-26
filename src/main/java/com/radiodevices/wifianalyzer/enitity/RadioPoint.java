package com.radiodevices.wifianalyzer.enitity;

/*
* Информация о радиоточке
* */
public class RadioPoint {
    private String model;

    private String version;

    private String mac;

    private String ipAddress;

    private String ssid;

    private String bssid;

    private String rssi;

    private String password;

    /*
    * Модель
    * */
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /*
    * Версия ПО
    * */
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /*
    * MAC-адрес
    * */
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    /*
    * IP-адрес
    * */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /*
    * SSID
    * */
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    /*
    * BSSID
    * */
    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }
    /*
    * Уровень сигнала dBm
    * */
    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    /*
    * PointPass
    * */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
