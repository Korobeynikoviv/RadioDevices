package com.radiodevices.wifianalyzer.service;

import com.radiodevices.wifianalyzer.dao.ReportDao;
import com.radiodevices.wifianalyzer.enitity.Report;
import com.radiodevices.wifianalyzer.enitity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportDao reportDao;
    private Logger logger = Logger.getLogger(ReportServiceImpl.class.getName());

    @Autowired
    public ReportServiceImpl(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public Report saveReport(String login, String reportJson) {
        Report report = new Report();
        report.setUser(login);
        report.setLocalDateTime(LocalDateTime.now());
        report.setReportJson(reportJson);
        return reportDao.save(report);
    }

    @Override
    public List<Report> getReports() {
        return reportDao.findAll();
    }

    @Override
    public List<Report> getUserReports(User user) {
        return reportDao.findAll().stream().filter(report -> report.getUser().equals(user.getEmail())).collect(Collectors.toList());
    }
}
