package com.radiodevices.wifianalyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radiodevices.wifianalyzer.enitity.Report;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ObjectMapper mapper = new ObjectMapper();
        File json = new File(Application.class.getClassLoader().getResource("report1.json").getFile());
        try {
            Report report = mapper.readValue(json, Report.class);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}