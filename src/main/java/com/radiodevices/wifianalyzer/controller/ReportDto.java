package com.radiodevices.wifianalyzer.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ReportDto implements Serializable {
    private List<WifiDetailsDto> wiFiDetails;
    private LocalDateTime timestamp;

    public List<WifiDetailsDto> getWiFiDetails() {
        return wiFiDetails;
    }

    public void setWiFiDetails(List<WifiDetailsDto> wiFiDetails) {
        this.wiFiDetails = wiFiDetails;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    class WifiDetailsDto {
        private String ssid;
        private String bssid;
        private Integer level;
        private Integer primaryChannel;
        private Integer primaryFrequency;
        private String frequencyUnits;
        private Integer centerChannel;
        private Integer centerFrequency;
        private Integer frequencyWidth;
        private Integer frequencyStart;
        private Integer frequencyEnd;
        private Integer distance;
        private Boolean mc;
        private String capabilities;

        public WifiDetailsDto() {
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getBssid() {
            return bssid;
        }

        public void setBssid(String bssid) {
            this.bssid = bssid;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Integer getPrimaryChannel() {
            return primaryChannel;
        }

        public void setPrimaryChannel(Integer primaryChannel) {
            this.primaryChannel = primaryChannel;
        }

        public Integer getPrimaryFrequency() {
            return primaryFrequency;
        }

        public void setPrimaryFrequency(Integer primaryFrequency) {
            this.primaryFrequency = primaryFrequency;
        }

        public String getFrequencyUnits() {
            return frequencyUnits;
        }

        public void setFrequencyUnits(String frequencyUnits) {
            this.frequencyUnits = frequencyUnits;
        }

        public Integer getCenterChannel() {
            return centerChannel;
        }

        public void setCenterChannel(Integer centerChannel) {
            this.centerChannel = centerChannel;
        }

        public Integer getCenterFrequency() {
            return centerFrequency;
        }

        public void setCenterFrequency(Integer centerFrequency) {
            this.centerFrequency = centerFrequency;
        }

        public Integer getFrequencyWidth() {
            return frequencyWidth;
        }

        public void setFrequencyWidth(Integer frequencyWidth) {
            this.frequencyWidth = frequencyWidth;
        }

        public Integer getFrequencyStart() {
            return frequencyStart;
        }

        public void setFrequencyStart(Integer frequencyStart) {
            this.frequencyStart = frequencyStart;
        }

        public Integer getFrequencyEnd() {
            return frequencyEnd;
        }

        public void setFrequencyEnd(Integer frequencyEnd) {
            this.frequencyEnd = frequencyEnd;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Boolean getMc() {
            return mc;
        }

        public void setMc(Boolean mc) {
            this.mc = mc;
        }

        public String getCapabilities() {
            return capabilities;
        }

        public void setCapabilities(String capabilities) {
            this.capabilities = capabilities;
        }
    }
}
