package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.enitity.Report;
import com.radiodevices.wifianalyzer.enitity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    public Report saveReport(String login, String reportJson);

    public List<Report> getReports();

    public List<Report> getUserReports(User user);
}
