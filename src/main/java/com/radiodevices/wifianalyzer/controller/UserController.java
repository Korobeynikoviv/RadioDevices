package com.radiodevices.wifianalyzer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.radiodevices.wifianalyzer.enitity.Report;
import com.radiodevices.wifianalyzer.enitity.User;
import com.radiodevices.wifianalyzer.service.AuthorizationService;
import com.radiodevices.wifianalyzer.service.ReportService;
import com.radiodevices.wifianalyzer.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {
    private UserService userService;
    private ReportService reportService;
    private AuthorizationService authorizationService;
    private Logger logger = Logger.getLogger(UserController.class.getName());
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public UserController(UserService userService,
                          AuthorizationService authorizationService,
                          ReportService reportService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.reportService = reportService;
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
        reportService.saveReport("user1", report);
        return new ResponseEntity<Response>(new Response(counter.incrementAndGet(), "Report saved"), HttpStatus.OK);
    }

    @PostMapping(value = "/sendreport2")
    public ResponseEntity<Response> sendReport2(HttpServletRequest request) {
        JSONObject reportObject = new JSONObject(request);
        return new ResponseEntity<Response>(new Response(counter.incrementAndGet(), "Report saved"), HttpStatus.OK);
    }

    @GetMapping("/getReports")
    public ResponseEntity getReports(@RequestParam String sessionId) {
        logger.log(Level.INFO, ".getReports# sessionId: " + sessionId);

        if (Strings.isBlank(sessionId)) {
            return new ResponseEntity<String>("Bad request: sessionId is null", HttpStatus.BAD_REQUEST);
        }
        User user = authorizationService.getUserBySessionId(sessionId);
        if (user == null) {
            return new ResponseEntity<String>("Bad request: User not found", HttpStatus.BAD_REQUEST);
        }
        List<Report> reports = reportService.getReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);

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
}
