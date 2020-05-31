package com.scrapper.auth;

import com.scrapper.auth.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

import static com.scrapper.utilities.Hash.HashPassword;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user, HttpServletResponse response) {
        User nUser = userService.findByUsername(user.getUsername());
        if(nUser != null) {
            try {
                String passHash = Base64.getEncoder().encodeToString(HashPassword(user.getPassword(), Base64.getDecoder().decode(nUser.getSalt())));
                if (nUser.getPassword().equals(passHash)) {
                    Cookie cookie = new Cookie("user", nUser.rolesToString());
                    Cookie cookieUser = new Cookie("username", nUser.getUsername());
                    cookie.setMaxAge(3 * 60 * 60);
                    cookie.setMaxAge(3 * 60 * 60);
                    cookie.setPath("/");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    response.addCookie(cookieUser);
                    return ResponseEntity.ok()
                            .build();
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            catch (Exception ex){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(@CookieValue(value = "user", defaultValue = "") String userCredential) {
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(1);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping(value = "/hash_passes")
    public ResponseEntity logout() {
        userService.hash();
        return ResponseEntity.ok().build();
    }

}

