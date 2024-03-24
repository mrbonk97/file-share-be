package org.mrbonk97.fileshareserver.controller;

import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {
    @GetMapping("/error")
    public String test() {
        throw new FileShareApplicationException(ErrorCode.EXPIRED_TOKEN);
    }

    @GetMapping("/auth")
    public String auth() {
        return "인증완료";
    }

}
