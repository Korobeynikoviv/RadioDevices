package com.radiodevices.wifianalyzer.controller;

import com.radiodevices.wifianalyzer.enitity.User;
import com.radiodevices.wifianalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UserController {
    private UserService userService;
    private Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
