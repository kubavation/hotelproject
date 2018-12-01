package com.duryskuba.hotelproject.controller;

import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.User;
import com.duryskuba.hotelproject.service.AuthenticationService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class AuthController {

    private AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @RequestMapping("/user")
    public Principal user(Principal user) {
        System.out.println(user == null ? "null" : user.getName());
        return user;
    }

}
