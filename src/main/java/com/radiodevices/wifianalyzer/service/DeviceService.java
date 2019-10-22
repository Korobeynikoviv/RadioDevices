package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.enitity.Device;

import java.util.List;
import java.util.UUID;

/*
*
* */
public interface DeviceService {

    /*
    *
    * */
    void getDeviceMacAddress(UUID uuid);

    /*
    *
    * */
    public void createDevice(Device device);

    public Device findDeviceByMac(String macAddress);

    /*
    *
    * */
    public List<Device> getDevicesByModel(String model);

    public List<Device> findAll();
}
