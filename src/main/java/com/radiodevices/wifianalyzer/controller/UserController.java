package com.radiodevices.wifianalyzer.controller;

import com.radiodevices.wifianalyzer.enitity.User;
import com.radiodevices.wifianalyzer.service.AuthorizationService;
import com.radiodevices.wifianalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {
    private UserService userService;
    private AuthorizationService authorizationService;
    private Logger logger = Logger.getLogger(UserController.class.getName());

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
                       @RequestParam(required = true) String hash) {
        return userService.addUser(email, name, hash);
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

    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity login(@RequestParam(required = true) String email,
                       @RequestParam(required = true) String hash) {
        logger.log(Level.INFO, ".login# email: " + email + "; hash: " + hash);
        User user = userService.getUserByEmail(email);
        if (user.getHash().equals(hash)) {
            return ResponseEntity.ok(authorizationService.login(email, hash));
        } else {
            return ResponseEntity.ok("Не верный пароль!");
        }
    }

    @GetMapping("/getReports")
    public ResponseEntity getReports(@RequestParam(required = true) String sessionId) {
        logger.log(Level.INFO, ".getReports# sessionId: " + sessionId);
        if (authorizationService.isAlive(sessionId)) {
            return new ResponseEntity<>("Session Alive", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
