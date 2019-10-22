package com.radiodevices.wifianalyzer.controller;

import com.radiodevices.wifianalyzer.enitity.Device;
import com.radiodevices.wifianalyzer.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class DeviceController {

    private DeviceService deviceService;
    private Logger logger = Logger.getLogger(DeviceController.class.getName());

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices/{id}")
    public Device getDeviceById(@PathVariable("id") String id) {
        try {
            return deviceService.findAll().stream().filter(it -> it.getId().equals(id)).findFirst().get();
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

    @GetMapping("/devices/{mac}")
    public Device getDeviceByMac(@PathVariable("mac") String macAddress) {
        try {
            return deviceService.findDeviceByMac(macAddress);
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

    @PostMapping("/init")
    public ResponseEntity initData() {
        logger.log(Level.WARNING,"Выполняется инициализация таблицы тестовыми данными");
        deviceService.createDevice(addDevice("5C:4D:11:34:6A:95", "LinkSys", "1.2.1", "192.168.1.2"));
        deviceService.createDevice(addDevice("5C:4D:11:34:6A:97", "TpLink", "3.1b", "192.168.2.20"));
        deviceService.createDevice(addDevice("5C:4D:11:34:6A:99", "Dlink", "A701U", "192.168.3.112"));
        return ResponseEntity.ok(deviceService.findAll());
    }

    private Device addDevice(String mac, String model, String version, String ipAddress){
        Device device = new Device();
        device.setMac(mac);
        device.setModel(model);
        device.setVersion(version);
        device.setIpAddress(ipAddress);
        return device;
    }
}
