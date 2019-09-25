package com.radiodevices.wifianalyzer.dao;

import com.radiodevices.wifianalyzer.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
