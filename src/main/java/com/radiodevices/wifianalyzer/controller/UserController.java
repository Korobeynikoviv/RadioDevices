package com.radiodevices.wifianalyzer.controller;

import com.radiodevices.wifianalyzer.enitity.User;
import com.radiodevices.wifianalyzer.service.AuthorizationService;
import com.radiodevices.wifianalyzer.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {
    private UserService userService;
    private AuthorizationService authorizationService;
    private Logger logger = Logger.getLogger(UserController.class.getName());
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public UserController(UserService userService,
                          AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestParam(required = true) String email,
                       @RequestParam(required = true) String name,
                       @RequestParam(required = true) String password) {
        return userService.addUser(email, name, password);
    }

    @PostMapping("/user/delete")
    public ResponseEntity delete(@RequestParam(required = true) String email,
                                 @RequestParam(required = true) String hash) {
        User user = userService.getUserByEmail(email);
        if (user.getHash().equals(hash)) {
            userService.deleteUser(user);
            return ResponseEntity.ok("Пользователь "+ email + " успешно удалён");
        } else {
            return ResponseEntity.ok("Не верный пароль!");
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestParam String login,
                                @RequestParam String passHash) {
        /*String content = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> requestMap = new Gson().fromJson(content, type);
        String login = requestMap.get("login");
        String passHash = requestMap.get("passHash");*/
        logger.log(Level.INFO, ".login# email: " + login + "; password: " + passHash);
        String sessionId = authorizationService.login(login, passHash);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        responseHeaders.add("Access-Control-Allow-Origin", "*");

        SessionDto sessionDto = new SessionDto(login, counter.incrementAndGet(), sessionId, null);
        if (!Strings.isBlank(sessionId)){
            return new ResponseEntity<SessionDto>(sessionDto, responseHeaders, HttpStatus.OK);
        } else {
            sessionDto.setMessage("Get out here! Не верный пароль");
            return new ResponseEntity<SessionDto>(sessionDto, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/sendreport")
    public ResponseEntity<Response> sendReport(
            @RequestParam(name = "session") String session,
            @RequestParam(name = "report") String report
    ) {
        JSONObject reportObject = new JSONObject(report);
        logger.log(Level.INFO, "sessionId: " + session + "; report: " + report);
        return new ResponseEntity<Response>(new Response(counter.incrementAndGet(), "Report saved"), HttpStatus.OK);
    }

    @PostMapping(value = "/sendreport2")
    public ResponseEntity<Response> sendReport2(HttpServletRequest request) {
        JSONObject reportObject = new JSONObject(request);
        return new ResponseEntity<Response>(new Response(counter.incrementAndGet(), "Report saved"), HttpStatus.OK);
    }

    @GetMapping("/getReports")
    public ResponseEntity getReports(@RequestParam(required = true) String sessionId) {
        logger.log(Level.INFO, ".getReports# sessionId: " + sessionId);

        if (Strings.isBlank(sessionId)) {
            return new ResponseEntity<String>("Bad request: sessionId is null", HttpStatus.BAD_REQUEST);
        }

        if (authorizationService.isAlive(sessionId)) {
            return new ResponseEntity<>("Session Alive", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    class SessionDto implements Serializable {
        private String username;
        private Long id;
        private String message;
        private String session;
        private static final long serialVersionUID = -1131160026914301582L;

        public SessionDto(String username, Long id, String session, String message) {
            this.username = username;
            this.id = id;
            this.session = session;
            this.message = message;
        }

        public void setUsername(String name) {
            this.username = name;
        }

        public String getUsername() {
            return username;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }


    }

    class Response implements Serializable {

        private static final long serialVersionUID = -6253082455394586219L;
        private final long id;
        private final String content;

        public Response(long id, String content) {
            this.id = id;
            this.content = content;
        }

        public long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }


    }

    class WifiDetailsDto implements Serializable {
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

    class ReportDto implements Serializable {
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
    }
}
