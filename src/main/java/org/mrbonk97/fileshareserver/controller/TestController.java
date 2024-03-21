package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class TestController {
    private final JwtUtils jwtUtils;

    @GetMapping()
    public String test() {
        return "Test";
    }

    @GetMapping("/acc")
    public String testAcc() {
        return jwtUtils.generateAccessToken("asd@naver.com");
    }

    @GetMapping("/ref")
    public String testRef() {
        return jwtUtils.generateRefreshToken("asd@naver.com");
    }

    @GetMapping("/verify")
    public String testJwt2(@RequestParam String jwt) {
        return "응애";

    }



}
