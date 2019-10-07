package com.radiodevices.wifianalyzer.dao;

import com.radiodevices.wifianalyzer.enitity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizeDao extends JpaRepository<Session, Long> {
}
