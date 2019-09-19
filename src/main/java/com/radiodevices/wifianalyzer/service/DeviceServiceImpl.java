package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.dao.DeviceDao;
import com.radiodevices.wifianalyzer.enitity.Device;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceServiceImpl implements DeviceService {

    private DeviceDao deviceDao;
    private Logger logger = Logger.getLogger(DeviceServiceImpl.class.getName());

    @Autowired
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
}
