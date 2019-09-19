package com.radiodevices.wifianalyzer.enitity;

/*
* Радиосеть
* */
public class DeviceNetwork extends Entity {

    private String ssid;

    private String bssid;

    private String rssi;

    private int countChannels;

    private String password;

    private String hash;

    private Device device;

    public DeviceNetwork(String ssid, String bssid, String rssi, int countChannels, String password, String hash, Device device) {
        super();
        this.ssid = ssid;
        this.bssid = bssid;
        this.rssi = rssi;
        this.countChannels = countChannels;
        this.password = password;
        this.hash = hash;
        this.device = device;
    }

    /*
    * Идентификатор беспроводной локальной сети
    * */
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    /*
    * Идентификатор базового набора услуг (MAC-адрес?)
    * */
    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    /*
    * Уровень принимаемого сигнала сети
    * */
    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    /*
    * Количество каналов
    * */
    public int getCountChannels() {
        return countChannels;
    }

    public void setCountChannels(int countChannels) {
        this.countChannels = countChannels;
    }

    /*
    * Пароль сети
    * */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    *
    * */
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /*
    * Радио устройство
    * */
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
