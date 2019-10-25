package com.radiodevices.wifianalyzer.dao;

import com.radiodevices.wifianalyzer.enitity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDao extends JpaRepository<Report, Long> {
}
