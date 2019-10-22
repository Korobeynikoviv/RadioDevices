package com.radiodevices.wifianalyzer.dao;

import com.radiodevices.wifianalyzer.enitity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceDao extends JpaRepository<Device, UUID> {

}
