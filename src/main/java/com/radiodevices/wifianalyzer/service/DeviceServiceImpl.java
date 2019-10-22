package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.dao.DeviceDao;
import com.radiodevices.wifianalyzer.enitity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;
    private Logger logger = Logger.getLogger(DeviceServiceImpl.class.getName());

    public DeviceServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public void getDeviceMacAddress(UUID uuid) {

        Device device = deviceDao
                .findById(uuid)
                .orElseThrow(()-> new EntityNotFoundException(uuid.toString()));

        logger.log(Level.INFO, device.getMac());
    }



    @Override
    public Device findDeviceByMac(String macAddress) {
        logger.log(Level.INFO,"Выполняется поиск устройства по MAC-адресу: "+ macAddress);
        return deviceDao.findAll().stream().filter(it->it.getMac().equals(macAddress)).findFirst().orElse(null);
    }

    @Override
    public List<Device> getDevicesByModel(String model) {
        return null;
    }

    @Override
    public List<Device> findAll() {
        return deviceDao.findAll();
    }

    @Override
    public void createDevice(Device device){
        Device existDevice = findDeviceByMac(device.getMac());
        if (existDevice == null) {
            deviceDao.save(device);
            logger.log(Level.WARNING, "Сохранили устройство с MAC-адресом " + device.getMac());
        } else {
            logger.log(Level.WARNING, "Устройство с MAC-адресом " + device.getMac() + " существует!");
        }
    }
}
