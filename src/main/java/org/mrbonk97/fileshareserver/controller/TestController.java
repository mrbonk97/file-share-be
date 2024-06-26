package org.mrbonk97.fileshareserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {
    @GetMapping()
    public String auth() {
        return "테스트 성공";
    }

}
