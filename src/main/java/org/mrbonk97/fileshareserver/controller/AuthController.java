package org.mrbonk97.fileshareserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login/oauth2/code")
@RestController
public class AuthController {

    @GetMapping("/google")
    public String test() {
        return "google";
    }

    @GetMapping
    public String success() {
        return "asd";
    }

}
