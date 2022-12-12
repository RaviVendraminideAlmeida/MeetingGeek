package br.com.meetinggeek.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess(){
        return "Public content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    public String userAccess() {
        return "User Content.";
    }

}
