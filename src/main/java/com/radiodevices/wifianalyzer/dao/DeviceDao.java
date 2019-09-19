package com.radiodevices.wifianalyzer.dao;

import com.radiodevices.wifianalyzer.enitity.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DeviceDao extends CrudRepository<Device, UUID> {
}
